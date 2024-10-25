package com.example.a03_10

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import android.util.Log

class MainActivity: AppCompatActivity() {
    private lateinit var imageView4: ImageView    //переменная будет инициализирована не в момент объявления
    private lateinit var inputPassword: TextInputEditText
    private var isPasswordVisible = false
    private lateinit var inputEmailOrPhone: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверка на наличие сохраненных данных
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val savedEmailOrPhone = sharedPreferences.getString("email_or_phone", null)
        val savedPassword = sharedPreferences.getString("password", null)

        if (savedEmailOrPhone != null && savedPassword != null) {
            // Проверка существования пользователя с сохраненными данными
            val user = isUserExists(savedEmailOrPhone, savedPassword)
            if (user != null) {
                // Перенаправление на WelcomeActivity
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("key", user.name)
                startActivity(intent)
                finish() // Закрыть MainActivity
                return // Прекратить выполнение onCreate
            }
        }

        setContentView(R.layout.activity_main)   //R - способ обращаться из кода к ресурсам приложения

        imageView4 = findViewById(R.id.imageView4)
        inputPassword = findViewById(R.id.textInputEditText)
        inputEmailOrPhone = findViewById(R.id.TextInputEditText)

        //обработчик клика на иконку отображения пароля
        imageView4.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                inputPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                imageView4.setImageResource(R.drawable.icon_close_eye)
                inputPassword.setSelection(inputPassword.text?.length ?: 0)   //устанавливаем курсор в конец текста
            } else {
                imageView4.setImageResource(R.drawable.icon_open_eye)
                inputPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                inputPassword.setSelection(inputPassword.text?.length ?: 0)
            }
        }



        //обработчик клика на кнопку login
        val loginButton: Button = findViewById(R.id.button)
        loginButton.setOnClickListener{
            //используем объект Intent для запуска другой Activity
            val intent = Intent(this, WelcomeActivity::class.java)

            val EmailOrPhone = inputEmailOrPhone.text.toString()
            val password = inputPassword.text.toString()

            val user = isUserExists(EmailOrPhone, password)
            if (user != null) {
                //сохранение данных юзера в SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("email_or_phone", EmailOrPhone)
                editor.putString("password", password)
                editor.apply()

                intent.putExtra("key", user.name)
                startActivity(intent)
                finish()  //закрытие MainActivity
            } else {
                Log.w("Login", "Не удалось войти: неверный логин или пароль")
                //сообщение об ошибке
                Snackbar.make(findViewById(android.R.id.content), "User not exists", Snackbar.LENGTH_LONG).show()
            }


        }
    }

    //проверка существования юзера
    private fun isUserExists (EmailOrPhone: String, password: String) : Users? {
        val users = getUsers()

        for (user in users) {
            val isEmailMatch = user.email == EmailOrPhone
            val isPhoneMatch = user.phoneNumber == EmailOrPhone
            if ((isEmailMatch || isPhoneMatch) && user.password == password) {
                return user
            }
        }
        return null
    }
}