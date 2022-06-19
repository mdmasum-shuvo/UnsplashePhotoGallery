package com.masum.gallery.network.netowork_use_case

import com.masum.gallery.common.ResponseResult
import com.masum.gallery.model.GalleryResponse
import com.masum.gallery.model.GalleryResponseItem
import com.masum.gallery.network.callback.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GalleryApiUseCase @Inject constructor(private val repository: GalleryRepository) {

    operator fun invoke(page: String): Flow<ResponseResult<List<GalleryResponseItem>>> = flow {
        try {
            emit(ResponseResult.Loading())
            val appResponse = repository.loadGallery(page)
            emit(ResponseResult.Success(appResponse))
        } catch (e: HttpException) {
            val error = ResponseResult.Error<List<GalleryResponseItem>>(e.localizedMessage ?: "An unexpected error")
            emit(error)
        } catch (e: IOException) {
            val error =
                ResponseResult.Error<List<GalleryResponseItem>>("Couldn't reach server. Check your internet connection.")
            emit(error)
        }
    }

}