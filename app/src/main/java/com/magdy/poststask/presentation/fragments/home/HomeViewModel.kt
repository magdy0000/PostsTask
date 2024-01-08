package com.magdy.poststask.presentation.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magdy.poststask.domain.usecase.GetPostsUseCase
import com.magdy.poststask.presentation.mappers.toPresentation
import com.magdy.poststask.presentation.models.ModelPostsPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getPostsUseCase: GetPostsUseCase) :
    ViewModel() {

    private val _postsStateFlow = MutableStateFlow<List<ModelPostsPresentation>>(emptyList())
    val postsStateFlow get() = _postsStateFlow

    private val _loadingStateFlow = Channel<Boolean>()
    val loadingStateFlow = _loadingStateFlow.receiveAsFlow()

    private val _errorChannel = Channel<String?>()
    val errorChannel = _errorChannel.receiveAsFlow()

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch(IO) {
            launch {
                _loadingStateFlow.send(true)
            }
            getPostsUseCase.getPosts().collect {
                if (it.isSuccess()) {
                    postsStateFlow.emit(it.getSuccessData()
                        .map { it.toPresentation() })
                } else {
                    _errorChannel.send(it.getErrorMessage())
                }
                launch {
                    _loadingStateFlow.send(false)
                }
            }
        }
    }

}