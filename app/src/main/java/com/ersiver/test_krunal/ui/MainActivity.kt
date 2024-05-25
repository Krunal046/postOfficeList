package com.ersiver.test_krunal.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ersiver.test_krunal.R
import com.ersiver.test_krunal.adapter.AddressAdapter
import com.ersiver.test_krunal.databinding.ActivityMainBinding
import com.ersiver.test_krunal.mvvmSetup.ApiServiceImp
import com.ersiver.test_krunal.mvvmSetup.PostRepository
import com.ersiver.test_krunal.mvvmSetup.ViewModelFactory
import com.ersiver.test_krunal.retrofitSetup.ApiClient
import com.ersiver.test_krunal.retrofitSetup.ApiInterface
import com.ersiver.test_krunal.room.PostListDatabase
import com.ersiver.test_krunal.utils.SharedPreferencesHelper
import com.ersiver.test_krunal.utils.Status
import com.ersiver.test_krunal.viewModel.PostVM
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postViewModel: PostVM
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var repository: PostRepository
    private lateinit var sharedPreferences: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        postViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiServiceImp(ApiClient.client.create(ApiInterface::class.java)))
        )[PostVM::class.java]

        val db = PostListDatabase.getDatabase(applicationContext)
        val postDao = db.getPostListDao()
        repository = PostRepository(postDao)
        sharedPreferences = SharedPreferencesHelper(this@MainActivity)

        setupRecyclerView()

        postViewModel.obrPostOfficeList.observe(this, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Log.e("TAG", "onCreate: Success")
                    binding.swipeRefreshLayout.isRefreshing = false
                    lifecycleScope.launch {
                        try {
                            val existingData = repository.postList.value
                            if (existingData.isNullOrEmpty()) {
                                repository.insertPost(resource.data!!)
                            }
                        } catch (e: Exception) {
                            Log.e("TAG", "onCreate: ${e.message}")
                        }
                    }

                    resource.data?.let { addresses ->
                        addressAdapter.submitList(addresses)
                    }
                }

                Status.LOADING -> {
                    Log.e("TAG", "onCreate: Loading")
                }

                Status.FAILED -> {
                    Log.e("TAG", "onCreate: Failed")
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                Status.ERROR -> {
                    Log.e("TAG", "onCreate: Error")
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        repository.postList.observe(this, Observer {
            addressAdapter.submitList(it)
            if (it.isNullOrEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
                binding.addressRecyclerView.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.addressRecyclerView.visibility = View.VISIBLE
            }
        })

        fetchData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isInternetAvailable(applicationContext)) {
                postViewModel.getList()
            } else {
                repository.getPostListFromRoom()
                binding.swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@MainActivity, "Internet is not Available.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()

                if (query.isEmpty()) {
                    val searchDrawable =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_search_24)
                    binding.searchEditText.setCompoundDrawablesWithIntrinsicBounds(
                        searchDrawable,
                        null,
                        null,
                        null
                    )
                } else {
                    val cancelDrawable =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_cancel_24)
                    val searchDrawable =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_search_24)
                    binding.searchEditText.setCompoundDrawablesWithIntrinsicBounds(
                        searchDrawable,
                        null,
                        cancelDrawable,
                        null
                    )
                }


                searchDatabase(query)
            }
        })

        binding.searchEditText.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // Right drawable
                if (binding.searchEditText.compoundDrawables[drawableEnd] == null) {
                    return@OnTouchListener false
                } else {
                    if (event.rawX >= (binding.searchEditText.right - binding.searchEditText.compoundDrawables[drawableEnd].bounds.width())) {
                        binding.searchEditText.text.clear()
                        binding.searchEditText.clearFocus()

                        // Optionally, hide the keyboard
                        val imm =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                sharedPreferences.clearLoginDetails()
                lifecycleScope.launch {
                    repository.deleteAllPost()
                }
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        addressAdapter = AddressAdapter(this@MainActivity)
        binding.addressRecyclerView.apply {
            adapter = addressAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

    private fun searchDatabase(query: String) {
        if (query.isNotEmpty()) {
            val searchQuery = "%$query%"
            repository.searchPostOffices(searchQuery).observe(this, Observer { addresses ->
                addressAdapter.submitList(addresses)
            })
        } else {
            fetchData()
        }
    }

    fun fetchData() {
        if (isInternetAvailable(applicationContext)) {
            postViewModel.getList()
        } else {
            repository.getPostListFromRoom()
            Toast.makeText(this@MainActivity, "Internet is not Available.", Toast.LENGTH_SHORT)
                .show()
        }
    }

}
