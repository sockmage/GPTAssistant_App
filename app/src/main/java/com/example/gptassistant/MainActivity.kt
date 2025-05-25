package com.example.gptassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.gptassistant.api.AskRequest
import com.example.gptassistant.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)
        val output = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            val message = input.text.toString()
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = RetrofitClient.instance.ask(AskRequest(message))
                    if (response.isSuccessful) {
                        output.text = response.body()?.response ?: "Нет ответа"
                    } else {
                        output.text = "Ошибка: ${response.code()}"
                    }
                } catch (e: Exception) {
                    output.text = "Ошибка сети: ${e.message}"
                }
            }
        }
    }
}
