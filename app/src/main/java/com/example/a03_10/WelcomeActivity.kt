package com.example.a03_10

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //получаем имя юзера из intent
        val username = intent.getStringExtra("key")
        val textView = findViewById<TextView>(R.id.textView7)
        textView.text = "Welcome $username!"
    }
}