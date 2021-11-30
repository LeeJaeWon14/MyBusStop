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

        var isLoop = true
        binding.apply {
            button.setOnClickListener {
                when(cbThread.isChecked) {
                    true -> {
                        Thread {
                            while(isLoop) {
                                if(progressbar.progress == 100) progressbar.progress = 0
                                progressbar.progress += 10
                                Thread.sleep(200)
                            }
                        }.start()
                        isLoop = true
                    }
                    false -> {
                        if(progressbar.progress == 100) {
                            progressbar.progress = 0
                            return@setOnClickListener
                        }
                        progressbar.progress += 10
                    }
                }
            }
            button.setOnLongClickListener {
                isLoop = !isLoop
//                Thread.currentThread().interrupt()
                false
            }
        }
    }
}