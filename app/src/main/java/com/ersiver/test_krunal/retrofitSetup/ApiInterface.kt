package com.ersiver.test_krunal.retrofitSetup

import com.ersiver.test_krunal.model.CommanResponseModel
import com.ersiver.test_krunal.model.PostOfficeModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("postoffice/Vadodara")
    fun getPosts(): Deferred<Response<CommanResponseModel<PostOfficeModel>>>
}
