package com.ersiver.test_krunal.mvvmSetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ersiver.test_krunal.viewModel.PostVM

class ViewModelFactory (val apiServiceRepo: ApiServiceRepo?): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(PostVM::class.java)-> {
                return PostVM(apiServiceRepo) as T
            }
            else -> {
                return modelClass as T
            }
        }
    }
}
