package com.austindorsey.d20

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import com.austindorsey.d20.databinding.ActivityCustomDiceBinding
import com.austindorsey.d20.fragments.CustomDiceNumaricFragment
import com.austindorsey.d20.fragments.CustomDiceStringFragment
import com.austindorsey.d20.model.Tab
import com.austindorsey.d20.model.CustomListCreator
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

    override fun onBackPressed() {
        sendResults()
        super.onBackPressed()
    }

    /**
     * Gets the custom die and sends it to the main activity to be rolled.
     */
    private fun sendResults() {
        val intent = Intent()
        val currentIndex = binding.viewPager.currentItem
        val adapter = binding.viewPager.adapter as FragmentStatePagerAdapter
        val item = adapter.getItem(currentIndex) as CustomListCreator
        val dieList = item.buildDieList().toTypedArray()
        intent.putExtra("customDice", dieList)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
