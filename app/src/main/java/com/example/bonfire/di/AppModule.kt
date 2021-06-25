package com.example.bonfire.di

import com.example.bonfire.auth.AuthFirebaseSource
import com.example.bonfire.data.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthSource(preferencesManager: PreferencesManager) = AuthFirebaseSource(preferencesManager)

}