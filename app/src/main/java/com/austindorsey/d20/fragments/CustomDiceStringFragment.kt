package com.austindorsey.d20.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.austindorsey.d20.R
import com.austindorsey.d20.databinding.FragmentCustomDiceStringBinding
import com.austindorsey.d20.model.CustomListCreator

class CustomDiceStringFragment : Fragment(),
    CustomListCreator {
    private lateinit var binding: FragmentCustomDiceStringBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_dice_string, container, false)
        val view = binding.root
        return view
    }

    override fun buildDieList(): List<String> {
        return listOf("a","b","c")
    }
}
