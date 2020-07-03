package com.austindorsey.d20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.austindorsey.d20.databinding.ActivityCustomDiceBinding
import com.austindorsey.d20.fragments.CustomDiceNumaricFragment
import com.austindorsey.d20.fragments.CustomDiceStringFragment
import com.austindorsey.d20.model.Tab
import com.austindorsey.d20.util.TabAdapter
import kotlinx.android.synthetic.main.activity_custom_dice.*

class CustomDiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomDiceBinding
    private val tabs = listOf<Tab>(
        Tab("Numeric", CustomDiceNumaricFragment()),
        Tab("List", CustomDiceStringFragment())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_dice)

        val tabLayout = binding.tabLayout
        val veiwPager = binding.viewPager
        tabLayout.setupWithViewPager(viewPager)
        veiwPager.adapter = TabAdapter(supportFragmentManager, tabs)
    }
}
