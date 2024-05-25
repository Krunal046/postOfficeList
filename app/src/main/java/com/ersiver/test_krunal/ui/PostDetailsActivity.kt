package com.ersiver.test_krunal.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersiver.test_krunal.R
import com.ersiver.test_krunal.databinding.ActivityPostDetailsBinding

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPostDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDetails)

        binding.btnBack.setOnClickListener{
            finish()
        }

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val pinCode = intent.getStringExtra("pinCode")
        val branchType = intent.getStringExtra("branchType")
        val deliveryStatus = intent.getStringExtra("deliveryStatus")
        val taluk = intent.getStringExtra("taluk")
        val circle = intent.getStringExtra("circle")
        val district = intent.getStringExtra("district")
        val division = intent.getStringExtra("division")
        val region = intent.getStringExtra("region")
        val state = intent.getStringExtra("state")
        val country = intent.getStringExtra("country")

        binding.tvName.text = "Name : $name"
        binding.tvDescription.text = "Description : $description"
        binding.tvPinCode.text = "PinCode : $pinCode"
        binding.tvBranchType.text = "BranchType : $branchType"
        binding.tvDeliveryStatus.text = "DeliveryStatus : $deliveryStatus"
        binding.tvTaluk.text = "Taluk : $taluk"
        binding.tvCircle.text = "Circle : $circle"
        binding.tvDistrict.text = "District : $district"
        binding.tvDivision.text = "Division : $division"
        binding.tvRegion.text = "Region : $region"
        binding.tvState.text = "State : $state"
        binding.tvCountry.text = "Country : $country"
    }
}
