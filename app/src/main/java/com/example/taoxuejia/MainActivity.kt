package com.example.taoxuejia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var timeLeft = 5 // 初始剩余时间
    private var autoJump: Job? = null
    private lateinit var adButton: Button
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adButton = findViewById(R.id.ad_pass)
        intent = Intent(this@MainActivity, HomeActivity::class.java)

        startAutoJump() // 启动计时
        adButton.setOnClickListener {
            autoJump?.cancel()
            startActivity(intent)
            finish()
        }
    }

    private fun startAutoJump() {
        autoJump?.cancel()
        autoJump = lifecycleScope.launch {
            while (timeLeft > 0) {
                adButton.text = "跳过 $timeLeft"
                delay(1000)
                timeLeft--
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        startAutoJump()
    }

    override fun onPause() {
        autoJump?.cancel()
        super.onPause()
    }
}
