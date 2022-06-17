package com.masum.gallery.network

import com.masum.gallery.model.GalleryResponse
import com.masum.gallery.model.GalleryResponseItem
import com.masum.gallery.network.HttpParam.PAGE
import com.masum.gallery.network.HttpParam.PHOTO
import retrofit2.http.POST
import retrofit2.http.Query


interface NetworkCallbackApi {


    @POST(PHOTO)
    suspend fun requestGalleryPhoto(@Query(PAGE) page:String) :GalleryResponse



}