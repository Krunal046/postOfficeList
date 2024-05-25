package com.ersiver.test_krunal.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ersiver.test_krunal.room.RoomDataBaseHelper

@Entity(tableName = RoomDataBaseHelper.Post_Office_List)
data class PostOfficeModel(
    val Name: String,
    val Description: String,
    val PINCode: String,
    val BranchType: String,
    val DeliveryStatus: String,
    val Taluk: String,
    val Circle: String,
    val District: String,
    val Division: String,
    val Region: String,
    val State: String,
    val Country:String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
