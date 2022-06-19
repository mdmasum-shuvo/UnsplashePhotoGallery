package com.masum.gallery.network.repo_impl

import com.masum.gallery.model.GalleryResponseItem
import com.masum.gallery.network.NetworkCallbackApi
import com.masum.gallery.network.callback.GalleryRepository
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(private val networkCallbackApi: NetworkCallbackApi) :GalleryRepository{

    override suspend fun loadGallery(page: String): List<GalleryResponseItem> {
        return networkCallbackApi.requestGalleryPhoto(page)
    }
}