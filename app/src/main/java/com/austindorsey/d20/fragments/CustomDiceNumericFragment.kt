package com.austindorsey.d20.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.austindorsey.d20.R
import com.austindorsey.d20.databinding.FragmentCustomDiceNumericBinding
import com.austindorsey.d20.model.CustomListCreator
import kotlin.math.abs

class CustomDiceNumericFragment : Fragment(),
    CustomListCreator {
    private lateinit var binding: FragmentCustomDiceNumericBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_dice_numeric, container, false)
        val view = binding.root

        var pref = activity?.getSharedPreferences("CustomDiceNumericFragmentPreferences", Context.MODE_PRIVATE)
        if (pref != null) {
            binding.min.setText(pref.getInt("min", 1).toString())
            binding.max.setText(pref.getInt("max", 20).toString())
            binding.multiple.setText(pref.getInt("multiple", 1).toString())
            binding.evens.isChecked = pref.getBoolean("evens", true)
            binding.odds.isChecked = pref.getBoolean("odds", true)
        }

        return view
    }

    override fun buildDieList(): List<String> {
        val possibilities = mutableListOf<String>()

        val max = try {binding.max.text.toString().toInt()} catch (e: NumberFormatException) {20}
        val min = try {binding.min.text.toString().toInt()} catch (e: NumberFormatException) {1}
        val multiple = try {binding.multiple.text.toString().toInt()} catch (e: NumberFormatException) {1}
        for (i in min..max) {
            if (i % multiple != 0) {
                continue
            }
            if (!binding.evens.isChecked) {
                if (abs(i) % 2 == 0) {
                    continue
                }
            }
            if (!binding.odds.isChecked) {
                if (abs(i) % 2 == 1) {
                    continue
                }
            }
            possibilities.add(i.toString())
        }
        return if (possibilities.isNotEmpty()) {
            possibilities.toList()
        } else {
            listOf("No Sides")
        }
    }

    override fun onStop() {
        super.onStop()
        var pref = activity?.getSharedPreferences("CustomDiceNumericFragmentPreferences", Context.MODE_PRIVATE)
        var editor = pref?.edit()
        if (editor != null) {
            editor.putInt("min", try {binding.min.text.toString().toInt()} catch (e: NumberFormatException) {1})
            editor.putInt("max", try {binding.max.text.toString().toInt()} catch (e: NumberFormatException) {20})
            editor.putInt("multiple", try {binding.multiple.text.toString().toInt()} catch (e: NumberFormatException) {1})
            editor.putBoolean("evens", binding.evens.isChecked)
            editor.putBoolean("odds", binding.odds.isChecked)
            editor.apply()
        }
    }
}
