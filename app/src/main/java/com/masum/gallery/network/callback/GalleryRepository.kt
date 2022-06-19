package com.masum.gallery.network.callback

import com.masum.gallery.model.GalleryResponseItem

interface GalleryRepository {
    suspend fun loadGallery(page:String):List<GalleryResponseItem>
}