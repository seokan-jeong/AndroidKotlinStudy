package com.seokan.sharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.seokan.sharedpreferences.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!MySharedPreferences.getUserId(this).isNullOrBlank()
            || !MySharedPreferences.getUserPassword(this).isNullOrBlank()
        ) {  // SharedPreferences 안에 값이 저장돼있을 때 -> MainActivity로 이동
            Toast.makeText(
                this, "${MySharedPreferences.getUserId(this)}님" +
                        "자동로그인 되었습니다", Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 로그인 버튼 클릭했을 때
        binding.loginButton.setOnClickListener {
            Login()
        }
    }

    fun Login() {
        if (binding.idEditText.text.isNullOrBlank() || binding.passwordEditText.text.isNullOrBlank()) {
            Toast.makeText(this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
        } else {
            MySharedPreferences.setUserId(this, binding.idEditText.text.toString())
            MySharedPreferences.setUserPassword(this, binding.passwordEditText.text.toString())
            Toast.makeText(
                this, "${MySharedPreferences.getUserId(this)}님이 " +
                        "로그인 되었습니다.", Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}