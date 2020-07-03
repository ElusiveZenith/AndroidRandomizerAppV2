package com.austindorsey.d20.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.austindorsey.d20.fragments.CustomDiceNumaricFragment
import com.austindorsey.d20.fragments.CustomDiceStringFragment
import com.austindorsey.d20.model.Tab

class TabAdapter(fm: FragmentManager, private var tabs: List<Tab>) : FragmentStatePagerAdapter(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        if (position < tabs.size) {
            return tabs[position].fragment
        } else {
            return Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabs[position].name
    }

    override fun getCount(): Int {
        return tabs.size
    }

}