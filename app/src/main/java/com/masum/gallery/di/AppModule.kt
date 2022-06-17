package com.masum.gallery.di

import android.content.Context
import com.masum.gallery.common.CustomApplication
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): CustomApplication {
        return app as CustomApplication
    }
}