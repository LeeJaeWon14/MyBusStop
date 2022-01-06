package com.example.mybusstop

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.ConfirmationActivity
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import com.example.mybusstop.databinding.ActivityMainBinding
import java.lang.Exception

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

        binding.apply {
            var thread: Thread? = null
            btnStart.setOnClickListener {
                when(cbThread.isChecked) {
                    true -> {
                        thread = Thread {
                            while(true) {
                                if(progressbar.progress == 100) progressbar.progress = 0
                                progressbar.progress += 10
                                try {
                                    Thread.sleep(200)
                                } catch(e: InterruptedException) {
                                    break
                                }
                            }
                        }
                        thread?.run {
                            if (isAlive)
                                Toast.makeText(this@MainActivity, "already run!!", Toast.LENGTH_SHORT).show()
                            else
                                start()
                        }
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
            btnStop.setOnClickListener {
                thread?.run {
                    if(isAlive)
                        interrupt()
                    else
                        return@setOnClickListener
                }
            }
        }
    }
}