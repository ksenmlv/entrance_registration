package com.example.a03_10

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent


class WelcomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //получаем имя юзера из intent
        val username = intent.getStringExtra("key")
        val textView = findViewById<TextView>(R.id.textView7)
        textView.text = "Welcome $username!"

        //обработчик клика на кнопку выхода
        val exitButton: Button = findViewById(R.id.button3)
        exitButton.setOnClickListener{
            //удаление данных из sharedPreferences
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() //удаление всех данных
            editor.apply()

            //используем объект Intent для запуска другой Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}