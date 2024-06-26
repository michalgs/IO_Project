package com.example.io_project.ui.screens.socialscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.io_project.dataclasses.Group
import com.example.io_project.datamanagement.fetchFriends
import com.example.io_project.datamanagement.fetchUserGroups
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(

): ViewModel(){
    var groups = mutableListOf<Group>()
    var groupCount: Int = 0

    var friends = mutableListOf<String>()
    var friendCount: Int = 0
    init {
        getGroupsList()
        getFriendsList()
        Log.d("SocialViewModel", "Fetched groups: $groups")
        Log.d("SocialViewModel", "Fetched friends: $friends")
    }

    private fun getGroupsList() {
         runBlocking {
            Firebase.auth.currentUser?.let {
                groups = fetchUserGroups(it.uid)?.toMutableList() ?: groups
                groupCount = groups.size
            }
        }
    }

    private fun getFriendsList() {
        runBlocking {
            Firebase.auth.currentUser?.let {
                friends = fetchFriends(it.uid)?.toMutableList() ?: friends
                friendCount = friends.size
            }
        }
    }

    fun refreshData() {
        getFriendsList()
        getGroupsList()
    }
}