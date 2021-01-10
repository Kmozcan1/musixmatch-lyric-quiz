package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.ViewPagerFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.Constants.HOME_PAGE_INDEX
import com.kmozcan1.lyricquizapp.presentation.Constants.LEADER_BOARD_PAGE_INDEX
import com.kmozcan1.lyricquizapp.presentation.adapter.ViewPagerAdapter

/**
 * Created by Kadir Mert Özcan on 10-Jan-21.
 */
class ViewPagerFragment: Fragment() {

    companion object {
        fun newInstance() = ViewPagerFragment()
    }

    lateinit var viewPagerAdapter: ViewPagerAdapter

    lateinit var binding: ViewPagerFragmentBinding

    private val args: ViewPagerFragmentArgs by navArgs()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).actionBar.visibility = View.VISIBLE
        (activity as MainActivity).bottomNavigationView.visibility = View.VISIBLE

        binding = ViewPagerFragmentBinding.inflate(
                inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.run {
            adapter = viewPagerAdapter
            visibility = View.GONE
            registerOnPageChangeCallback(viewPagerOnPageChangeCallback())
        }
    }

    private fun viewPagerOnPageChangeCallback(): OnPageChangeCallback {
        return object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Set the bottom navigation selected item and action bar title
                val mainActivity = (activity as MainActivity)
                when(position) {
                    HOME_PAGE_INDEX -> {
                        mainActivity.actionBar.title =
                                resources.getString(R.string.home)
                        mainActivity
                                .bottomNavigationView.menu[HOME_PAGE_INDEX].isChecked = true
                    }
                    LEADER_BOARD_PAGE_INDEX -> {
                        mainActivity.actionBar.title =
                                resources.getString(R.string.leader_board)
                        mainActivity
                                .bottomNavigationView.menu[LEADER_BOARD_PAGE_INDEX].isChecked = true
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewPager.run {
            doOnPreDraw {
                setCurrentItem(args.pageIndex, false)
                visibility = View.VISIBLE
            }
        }
    }

    fun setCurrentItem(pageIndex: Int) {
        binding.viewPager.currentItem = pageIndex
    }
}