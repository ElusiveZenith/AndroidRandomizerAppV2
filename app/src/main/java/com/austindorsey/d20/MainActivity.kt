package com.austindorsey.d20

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.austindorsey.d20.databinding.ActivityMainBinding
import com.austindorsey.d20.model.Dice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val numericDiceToCreate = listOf<Int>(2,4,6,8,10,12,16,20)
    private lateinit var dice: MutableList<Dice>
    private var index: Int = 0
    companion object {
        const val CUSTOM_LIST_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dice = buildInitialDice()

        //Bindings
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.roll.setOnClickListener() { roll() }
        binding.nextDie.setOnClickListener() { nextDie() }
        binding.previousDie.setOnClickListener() { previousDie() }
        binding.moreOptions.setOnClickListener() { startCustomDiceActivity() }

        updateDisplayedDie()
    }

    override fun onStop() {
        super.onStop()
        var pref = getSharedPreferences("MainActivityPreferences", Context.MODE_PRIVATE)
        var editor = pref?.edit()
        if (editor != null) {
            val gson = Gson()
            val json = gson.toJson(dice[dice.size-1].values)
            editor.putString("MainActivityPreferences.custom", json)
            editor.putInt("MainActivityPreferences.lastDieSelected", index)
            editor.apply()
        }
    }

    /**
     *  Creates the default dice and returns them
     *  @return MutableList of the default Dice and saved or default custom die.
     */
    private fun buildInitialDice(): MutableList<Dice> {
        val list: MutableList<Dice> = mutableListOf()
        for (d in numericDiceToCreate) {
            val die = Dice((1..d).toMutableList().map { it.toString() })
            list.add(die)
        }
        index = list.size - 1 //Sets index to last numeric die which is the d20

        var pref = getSharedPreferences("MainActivityPreferences", Context.MODE_PRIVATE)
        if (pref != null) {
            //Gets any custom die created in the last session and adds it to the initial list.
            val gson = Gson()
            val customDieString = pref.getString("MainActivityPreferences.custom", "[\"Red\",\"Green\",\"Blue\"]")
            val customDieList: List<String> = gson.fromJson(customDieString, object:TypeToken<List<String>>(){}.type)
            list.add(Dice(customDieList))

            //Gets last selected die. If that index is out of range, then defaults to d20.
            index = pref.getInt("MainActivityPreferences.lastDieSelected", numericDiceToCreate.size - 1)
            if (index >= list.size) { index = numericDiceToCreate.size - 1 }
        } else {
            list.add(Dice(listOf("Red", "Green", "Blue")))
            index = numericDiceToCreate.size - 1
        }

        return list
    }

    /**
     * Starts the CustomDiceActivity for results
     */
    private fun startCustomDiceActivity() {
        val intent = Intent(this, CustomDiceActivity::class.java)
        startActivityForResult(intent, CUSTOM_LIST_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CUSTOM_LIST_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            //Get custom die from result
            val customDice = Dice(data.getStringArrayExtra("customDice").asList())
            index = dice.size - 1
            dice[index] = customDice
            updateDisplayedDie()
        }
    }

    /**
     * Rolls the selected die and shows the results of the roll
     */
    private fun roll() {
        binding.rollResults.text = dice[index].roll()
    }

    /**
     * Changes the selected die to the next die in the list. Cycles back to the first die if on the last.
     */
    private fun nextDie() {
        if (index < dice.size - 1) {
            index++
        } else {
            index = 0
        }
        updateDisplayedDie()
    }

    /**
     * Changes the selected die to the previous die in the list. Cycles back to the last die if on the last.
     */
    private fun previousDie() {
        if (index == 0) {
            index = dice.size - 1
        } else {
            index--
        }
        updateDisplayedDie()
    }

    /**
     * Updates rollResults with the highest number for default dice, or custom.
     * Updates diceSelected with d2 - d20 for default dice, or custom.
     */
    private fun updateDisplayedDie() {
        binding.rollResults.text = if (index < numericDiceToCreate.size) numericDiceToCreate[index].toString() else "Custom"
        binding.diceSelected.text = if (index < numericDiceToCreate.size) "d" + numericDiceToCreate[index] else "Custom"
    }
}
