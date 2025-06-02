package com.example.lingro.ui.components

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "voice_prefs")

object VoicePreferences {
    private val VOICE_KEY = stringPreferencesKey("selected_voice")
    private val LANGUAGE_KEY = stringPreferencesKey("selected_language")

    suspend fun saveVoiceName(context: Context, voiceName: String) {
        context.dataStore.edit { prefs ->
            prefs[VOICE_KEY] = voiceName
        }
    }

    suspend fun loadVoiceName(context: Context): String? {
        return context.dataStore.data.map { prefs ->
            prefs[VOICE_KEY]
        }.first()
    }

    suspend fun saveLanguage(context: Context, language: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language
        }
    }

    suspend fun loadLanguage(context: Context): String? {
        return context.dataStore.data.map { prefs ->
            prefs[LANGUAGE_KEY]
        }.first()
    }
} 