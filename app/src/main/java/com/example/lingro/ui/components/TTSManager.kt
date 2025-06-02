package com.example.lingro.ui.components

import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log

object TTSManager {
    private var mediaPlayer: MediaPlayer? = null
    private var onDone: (() -> Unit)? = null
    private var currentVoice: String = "sora"
    private var isSpeaking: Boolean = false
    private var lastFile: File? = null

    val availableVoices = listOf(
        "nova", "shimmer", "echo", "onyx", "fable", "alloy", "ash", "sage", "coral"
    )

    fun setVoice(voice: String) {
        if (voice in availableVoices) {
            currentVoice = voice
        }
    }

    fun getCurrentVoice(): String = currentVoice

    fun speak(
        context: Context,
        text: String,
        voice: String? = null,
        onDone: (() -> Unit)? = null,
        onLoadingStart: (() -> Unit)? = null,
        onLoadingEnd: (() -> Unit)? = null
    ) {
        stop()
        this.onDone = onDone
        isSpeaking = true
        val useVoice = voice ?: currentVoice
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) { onLoadingStart?.invoke() }
                val url = URL("https://lingroproxy-production.up.railway.app/tts")
                val postData = "{\"input\":\"${text.replace("\"", "\\\"") }\",\"voice\":\"$useVoice\"}"
                Log.d("TTSManager", "Запрос к $url с голосом $useVoice и текстом: $text")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                conn.outputStream.use { it.write(postData.toByteArray()) }
                if (conn.responseCode == 200) {
                    val tempFile = File.createTempFile("tts", ".mp3", context.cacheDir)
                    conn.inputStream.use { input ->
                        FileOutputStream(tempFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        onLoadingEnd?.invoke()
                        playAudio(context, tempFile)
                    }
                    lastFile = tempFile
                    Log.d("TTSManager", "Успешно получили и воспроизводим mp3")
                } else {
                    Log.e("TTSManager", "Ошибка HTTP: ${conn.responseCode}")
                    withContext(Dispatchers.Main) {
                        onLoadingEnd?.invoke()
                        onDone?.invoke()
                    }
                }
            } catch (e: Exception) {
                Log.e("TTSManager", "Ошибка TTS: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    onLoadingEnd?.invoke()
                    onDone?.invoke()
                }
            }
        }
    }

    private fun playAudio(context: Context, file: File) {
        stop()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            setOnCompletionListener {
                isSpeaking = false
                onDone?.invoke()
            }
            setOnErrorListener { _, _, _ ->
                isSpeaking = false
                onDone?.invoke()
                true
            }
            prepare()
            start()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isSpeaking = false
        onDone?.invoke()
        onDone = null
        lastFile?.delete()
        lastFile = null
    }

    fun isSpeaking(): Boolean = isSpeaking
} 