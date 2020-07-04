package com.austindorsey.d20.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.austindorsey.d20.R
import com.austindorsey.d20.databinding.FragmentCustomDiceNumaricBinding
import com.austindorsey.d20.model.CustomListCreator
import kotlin.math.abs

class CustomDiceNumaricFragment : Fragment(),
    CustomListCreator {
    private lateinit var binding: FragmentCustomDiceNumaricBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_dice_numaric, container, false)
        val view = binding.root
        return view
    }

    override fun buildDieList(): List<String> {
        val possibilities = mutableListOf<String>()

        val max = binding.max.text.toString().toInt()
        val min = binding.min.text.toString().toInt()
        val multiple = binding.multiple.text.toString().toInt()
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

        return possibilities.toList()
    }
}
