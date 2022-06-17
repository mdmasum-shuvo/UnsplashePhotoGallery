package com.masum.gallery.di

import com.masum.gallery.network.NetworkCallbackApi
import com.masum.gallery.network.callback.GalleryRepository
import com.masum.gallery.network.repo_impl.GalleryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesAuthRepository(api: NetworkCallbackApi): GalleryRepository {
        return GalleryRepositoryImpl(api)
    }
}