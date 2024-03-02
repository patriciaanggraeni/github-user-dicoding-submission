package com.example.githubuser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.repository.UserRepositoryImpl
import com.example.githubuser.data.remote.response.SearchResult
import com.example.githubuser.data.remote.response.UserDetail
import com.example.githubuser.domain.model.User

class UserViewModel(private val userRepository: UserRepositoryImpl) : ViewModel() {

    private val _users = MutableLiveData<Result<List<User>>>()
    val users: LiveData<Result<List<User>>> = _users

    private val _userDetail = MutableLiveData<Result<UserDetail>>()
    val userDetail: LiveData<Result<UserDetail>> = _userDetail

    private val _searchResults = MutableLiveData<Result<SearchResult>>()
    val searchResults: LiveData<Result<SearchResult>> = _searchResults

    private val _userFollowers = MutableLiveData<Result<List<User>>>()
    val userFollowers: LiveData<Result<List<User>>> = _userFollowers

    private val _userFollowing = MutableLiveData<Result<List<User>>>()
    val userFollowing: LiveData<Result<List<User>>> = _userFollowing

    fun getAllUsers() {
        userRepository.getAllUsers { result ->
            _users.postValue(result)
        }
    }

    fun getUserDetail(username: String) {
        userRepository.getUserDetail(username) { result ->
            _userDetail.postValue(result)
        }
    }

    fun searchUser(username: String) {
        userRepository.searchUser(username) { result ->
            _searchResults.postValue(result)
        }
    }

    fun getUserFollowers(username: String) {
        userRepository.getUserFollowers(username) { result ->
            _userFollowers.postValue(result)
        }
    }

    fun getUserFollowing(username: String) {
        userRepository.getUserFollowing(username) { result ->
            _userFollowing.postValue(result)
        }
    }
}
