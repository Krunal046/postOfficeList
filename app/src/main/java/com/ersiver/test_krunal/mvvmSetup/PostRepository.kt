package com.ersiver.test_krunal.mvvmSetup

import androidx.lifecycle.LiveData
import com.ersiver.test_krunal.model.PostOfficeModel
import com.ersiver.test_krunal.room.PostDao

class PostRepository(private val postDao: PostDao) {

    val postList: LiveData<List<PostOfficeModel>> = postDao.getPostList()

    suspend fun insertPost(postList: List<PostOfficeModel>) {
        postDao.postInsert(postList)
    }

    suspend fun deletePost(postList: PostOfficeModel) {
        postDao.postDelete(postList)
    }

    suspend fun updatePost(postList: PostOfficeModel) {
        postDao.postUpdate(postList)
    }

    fun getPostListFromRoom(): LiveData<List<PostOfficeModel>> {
        return postList
    }

    suspend fun deleteAllPost() = postDao.deleteAllPosts()

    fun searchPostOffices(searchQuery: String): LiveData<List<PostOfficeModel>> {
        return postDao.searchPostOffice(searchQuery)
    }

}
