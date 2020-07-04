package com.austindorsey.d20.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.LinearLayoutBindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager

import com.austindorsey.d20.R
import com.austindorsey.d20.databinding.FragmentCustomDiceStringBinding
import com.austindorsey.d20.model.CustomListCreator
import com.austindorsey.d20.util.ListAdapter

class CustomDiceStringFragment : Fragment(),
    CustomListCreator {
    private lateinit var binding: FragmentCustomDiceStringBinding
    private val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_dice_string, container, false)
        binding.addListItem.setOnClickListener() { adapter.addItem() }
        binding.list.adapter = adapter
        return binding.root
    }

    override fun buildDieList(): List<String> {
        return adapter.data.toList()
    }
}
