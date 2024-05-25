package com.ersiver.test_krunal.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ersiver.test_krunal.model.CommanResponseModel
import com.ersiver.test_krunal.model.PostOfficeModel
import com.ersiver.test_krunal.mvvmSetup.ApiServiceRepo
import com.ersiver.test_krunal.utils.Resource
import com.ersiver.test_krunal.utils.Status
import com.ersiver.test_krunal.utils.onApiResponse

class PostVM(val repository: ApiServiceRepo?) : ViewModel() {

    var obrPostOfficeList = MutableLiveData<Resource<List<PostOfficeModel>>>() // Update type to List<PostOfficeModel>

    fun getList() {

        repository?.getList(object : onApiResponse<CommanResponseModel<PostOfficeModel>> {
            override fun onLoading(msg: String) {
                obrPostOfficeList.postValue(Resource<List<PostOfficeModel>>(Status.LOADING, null, msg)) // Update Resource type to List<PostOfficeModel>
            }

            override fun onFailed(error: String) {
                obrPostOfficeList.postValue(Resource<List<PostOfficeModel>>(Status.FAILED, null, error)) // Update Resource type to List<PostOfficeModel>
            }

            override fun onError(error: Throwable?) {
                obrPostOfficeList.postValue(
                    Resource<List<PostOfficeModel>>( // Update Resource type to List<PostOfficeModel>
                        Status.ERROR,
                        null,
                        error?.message
                    )
                )
            }

            override fun onSuccess(body: CommanResponseModel<PostOfficeModel>?) {
                obrPostOfficeList.postValue(
                    Resource<List<PostOfficeModel>>( // Update Resource type to List<PostOfficeModel>
                        Status.SUCCESS,
                        body?.data,
                        body?.message
                    )
                )
            }
        })
    }
}
