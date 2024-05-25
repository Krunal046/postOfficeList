package com.ersiver.test_krunal.mvvmSetup

import com.ersiver.test_krunal.model.CommanResponseModel
import com.ersiver.test_krunal.model.PostOfficeModel
import com.ersiver.test_krunal.utils.onApiResponse

interface ApiServiceRepo {
    fun getList(callback: onApiResponse<CommanResponseModel<PostOfficeModel>>)
}
