package com.austindorsey.d20

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.austindorsey.d20.databinding.ActivityMainBinding
import com.austindorsey.d20.model.Dice

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val numericDiceToCreate = listOf<Int>(2,4,6,8,10,12,16,20)
    private var dice: MutableList<Dice> = buildInitialDice()
    private var index: Int = 0
    companion object {
        const val CUSTOM_LIST_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.roll.setOnClickListener() { roll() }
        binding.nextDie.setOnClickListener() { nextDie() }
        binding.previousDie.setOnClickListener() { previousDie() }
        binding.moreOptions.setOnClickListener() { startCustomDiceActivity() }
    }

    private fun buildInitialDice(): MutableList<Dice> {
        val list: MutableList<Dice> = mutableListOf()
        for (d in numericDiceToCreate) {
            val die = Dice((1..d).toMutableList().map { it.toString() })
            list.add(die)
        }
        index = list.size - 1 //Sets index to last numeric die which is the d20
        list.add(Dice(listOf("Red", "Green", "Blue")))
        return list
    }

    private fun startCustomDiceActivity() {
        val intent = Intent(this, CustomDiceActivity::class.java)
        startActivityForResult(intent, CUSTOM_LIST_REQUEST_CODE)
    }

    private fun roll() {
        binding.rollResults.text = dice[index].roll()
    }

    private fun nextDie() {
        if (index < dice.size - 1) {
            index++
        } else {
            index = 0
        }
        updateDisplayedDie()
    }

    private fun previousDie() {
        if (index == 0) {
            index = dice.size - 1
        } else {
            index--
        }
        updateDisplayedDie()
    }

    private fun updateDisplayedDie() {
        binding.rollResults.text = if (index < numericDiceToCreate.size) numericDiceToCreate[index].toString() else "Custom"
        binding.diceSelected.text = if (index < numericDiceToCreate.size) "d" + numericDiceToCreate[index] else "Custom"
    }
}