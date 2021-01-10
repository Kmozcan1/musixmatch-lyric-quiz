package com.kmozcan1.lyricquizapp.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kmozcan1.lyricquizapp.presentation.Constants
import com.kmozcan1.lyricquizapp.presentation.Constants.HOME_PAGE_INDEX
import com.kmozcan1.lyricquizapp.presentation.Constants.LEADER_BOARD_PAGE_INDEX
import com.kmozcan1.lyricquizapp.presentation.ui.HomeFragment
import com.kmozcan1.lyricquizapp.presentation.ui.LeaderBoardFragment
import com.kmozcan1.lyricquizapp.presentation.ui.ViewPagerFragment
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 10-Jan-21.
 */
class ViewPagerAdapter @Inject constructor(
        userViewPagerFragment: ViewPagerFragment
) : FragmentStateAdapter(userViewPagerFragment) {

    // Number of tabs
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if (position == HOME_PAGE_INDEX) {
            return HomeFragment()
        } else if (position == LEADER_BOARD_PAGE_INDEX) {
            return LeaderBoardFragment()
        }
        return HomeFragment()
    }
}