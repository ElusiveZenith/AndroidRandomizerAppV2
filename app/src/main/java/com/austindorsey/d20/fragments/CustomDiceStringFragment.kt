package com.austindorsey.d20.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.austindorsey.d20.R
import com.austindorsey.d20.databinding.FragmentCustomDiceStringBinding
import com.austindorsey.d20.model.CustomListCreator
import com.austindorsey.d20.util.ListAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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

        var pref = activity?.getSharedPreferences("CustomDiceStringFragmentPreferences", Context.MODE_PRIVATE)
        if (pref != null) {
            val gson = Gson()
            val dataString = pref.getString("CustomDiceStringFragment.data", "[]")
            val data: MutableList<String> = gson.fromJson(dataString, object:TypeToken<MutableList<String>>(){}.type)
            adapter.data = data
        }

        return binding.root
    }

    override fun buildDieList(): List<String> {
        val sides = adapter.data.toList()
        return if (sides.isNotEmpty()) {
            sides
        } else {
            listOf("No Sides")
        }
    }

    override fun onStop() {
        super.onStop()
        var pref = activity?.getSharedPreferences("CustomDiceStringFragmentPreferences", Context.MODE_PRIVATE)
        var editor = pref?.edit()
        if (editor != null) {
            val gson = Gson()
            val json = gson.toJson(adapter.data)
            editor.putString("CustomDiceStringFragment.data", json)
            editor.apply()
        }
    }
}
