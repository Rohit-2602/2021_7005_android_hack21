package com.example.bonfire.data

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.bonfire.Constants
import com.example.bonfire.Constants.USER_PREFERENCES
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore(USER_PREFERENCES)

    suspend fun updateLoginState(loginStateKey: String, loginState: Boolean) {
        val dataStoreKey = preferencesKey<Boolean>(loginStateKey)
        dataStore.edit { pref ->
            pref[dataStoreKey] = loginState
        }
    }

    val loginStateFlow = dataStore.data
        .map { preferences ->
            val loginStateKey = preferencesKey<Boolean>(Constants.LOGIN_STATE_KEY)
            preferences[loginStateKey] ?: false
        }

}