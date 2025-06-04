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
    private val ONBOARDING_DONE_KEY = stringPreferencesKey("onboarding_done")
    private val THEME_KEY = stringPreferencesKey("selected_theme")

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

    suspend fun saveOnboardingDone(context: Context, done: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_DONE_KEY] = done.toString()
        }
    }

    suspend fun loadOnboardingDone(context: Context): Boolean {
        return context.dataStore.data.map { prefs ->
            prefs[ONBOARDING_DONE_KEY]?.toBoolean() ?: false
        }.first()
    }

    suspend fun saveTheme(context: Context, theme: String) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = theme
        }
    }

    suspend fun loadTheme(context: Context): String? {
        return context.dataStore.data.map { prefs ->
            prefs[THEME_KEY]
        }.first()
    }
} 