package com.example.mybusstop

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.ConfirmationActivity
import android.support.wearable.activity.WearableActivity
import com.example.mybusstop.databinding.ActivityMainBinding

class MainActivity : WearableActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enables Always-on
        setAmbientEnabled()

        binding.btnYes.setOnClickListener {
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION)
                putExtra(ConfirmationActivity.EXTRA_MESSAGE, "확인되었습니다.")
            }
            startActivity(intent)
        }
        binding.btnNo.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }
    }
}