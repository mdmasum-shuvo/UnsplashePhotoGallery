package com.masum.gallery.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masum.gallery.common.Constant.UNEXPECTED_ERROR
import com.masum.gallery.common.ResponseResult
import com.masum.gallery.model.GalleryResponse
import com.masum.gallery.model.GalleryResponseItem
import com.masum.gallery.network.netowork_use_case.GalleryApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel  @Inject constructor(var useCase: GalleryApiUseCase) : ViewModel() {

    private val _galleryData = MutableLiveData<ResponseResult<List<GalleryResponseItem>>>()
    val galleryData: LiveData<ResponseResult<List<GalleryResponseItem>>>
        get() = _galleryData



    fun getGalleryPhoto(page:Int) {
        useCase.invoke(page.toString()).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    _galleryData.value = ResponseResult.Loading()
                }
                is ResponseResult.Success -> {
                    _galleryData.value = ResponseResult.Success(result.data)
                }
                is ResponseResult.Error -> {
                    _galleryData.value = ResponseResult.Error(result.message ?: UNEXPECTED_ERROR)
                }
            }

        }.launchIn(viewModelScope)
    }


}