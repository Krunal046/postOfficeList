package com.ersiver.test_krunal.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ersiver.test_krunal.model.PostOfficeModel


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postInsert(postList: List<PostOfficeModel>)

    @Delete
    suspend fun postDelete(postList: PostOfficeModel)

    @Update
    suspend fun postUpdate(postList: PostOfficeModel)

    @Query("SELECT * FROM ${RoomDataBaseHelper.Post_Office_List}")
    fun getPostList(): LiveData<List<PostOfficeModel>>

    @Query("DELETE FROM ${RoomDataBaseHelper.Post_Office_List}")
    suspend fun deleteAllPosts()

    @Query("SELECT * FROM ${RoomDataBaseHelper.Post_Office_List} WHERE name LIKE '%' || :searchQuery || '%' OR pincode LIKE '%' || :searchQuery || '%'")
    fun searchPostOffice(searchQuery: String): LiveData<List<PostOfficeModel>>

}
