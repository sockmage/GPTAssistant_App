package com.example.lingro.ui.components

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.Locale

@Suppress("DEPRECATION")
object TTSManager : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var onDone: (() -> Unit)? = null
    private var currentVoice: Voice? = null
    private var lastText: String? = null
    private var pendingOnInit: (() -> Unit)? = null

    fun init(context: Context, onInit: (() -> Unit)? = null) {
        if (tts == null) {
            pendingOnInit = onInit
            tts = TextToSpeech(context.applicationContext, this)
        } else {
            if (isInitialized) {
                onInit?.invoke()
            } else {
                pendingOnInit = onInit
            }
        }
    }

    override fun onInit(status: Int) {
        isInitialized = status == TextToSpeech.SUCCESS
        if (isInitialized) {
            tts?.setOnUtteranceProgressListener(object : android.speech.tts.UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    onDone?.invoke()
                }
                override fun onError(utteranceId: String?) {
                    onDone?.invoke()
                }
            })
            pendingOnInit?.invoke()
            pendingOnInit = null
        }
    }

    fun speak(text: String, voice: Voice? = null, onDone: (() -> Unit)? = null) {
        if (!isInitialized) return
        stop()
        this.onDone = onDone
        lastText = text
        if (voice != null) {
            tts?.voice = voice
            currentVoice = voice
        }
        val params = hashMapOf<String, String>()
        params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "LingroTTS"
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params as java.util.HashMap<String, String>?)
    }

    fun stop() {
        tts?.stop()
        onDone?.invoke()
        onDone = null
    }

    fun shutdown() {
        tts?.shutdown()
        tts = null
        isInitialized = false
    }

    fun getVoices(): List<Voice> {
        return tts?.voices?.filter { it.locale.language == Locale.getDefault().language && !it.isNetworkConnectionRequired }?.toList() ?: emptyList()
    }

    fun setVoice(voice: Voice) {
        currentVoice = voice
        tts?.voice = voice
    }

    fun getCurrentVoice(): Voice? = currentVoice
} 