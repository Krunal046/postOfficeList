package com.ersiver.test_krunal.mvvmSetup

import com.ersiver.test_krunal.model.CommanResponseModel
import com.ersiver.test_krunal.model.PostOfficeModel
import com.ersiver.test_krunal.retrofitSetup.ApiInterface
import com.ersiver.test_krunal.utils.onApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class ApiServiceImp(val apiInterface: ApiInterface):ApiServiceRepo {

    override fun getList(callback: onApiResponse<CommanResponseModel<PostOfficeModel>>) {
        commonResponse(callback, apiInterface.getPosts())
    }

    private fun commonResponse(
        callback: onApiResponse<CommanResponseModel<PostOfficeModel>>,
        response: Deferred<Response<CommanResponseModel<PostOfficeModel>>>
    ){
        callback.onLoading("loading")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.IO){
                    val finalResponse = response.await()
                    if (finalResponse.isSuccessful){
                        withContext(Dispatchers.Main){
                            if (finalResponse.body()?.status == "Success"){
                                callback.onSuccess(finalResponse.body())
                            }
                        }
                    }else{
                        failure(finalResponse, callback)
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callback.onError(e.cause)
                }
            }

        }
    }

    private fun <T> failure(
        response: Response<CommanResponseModel<T>>,
        callback: onApiResponse<CommanResponseModel<T>>
    ){
        callback.onFailed(response.body()?.message ?: "Failed")
    }

}
