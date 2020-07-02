package com.austindorsey.d20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.austindorsey.d20.databinding.ActivityCustomDiceBinding

class CustomDiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomDiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_dice)
    }
}
