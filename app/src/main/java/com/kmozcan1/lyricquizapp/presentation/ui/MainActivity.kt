package com.kmozcan1.lyricquizapp.presentation.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.presentation.Constants
import com.kmozcan1.lyricquizapp.presentation.Constants.HOME_PAGE_INDEX
import com.kmozcan1.lyricquizapp.presentation.Constants.LEADER_BOARD_PAGE_INDEX
import com.kmozcan1.lyricquizapp.presentation.viewmodel.MainViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.MainViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.MainViewState.State.*
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    var isConnectedToInternet: Boolean = false
        private set

    var userName: String = ""

    var userScoresPendingUpdate = true
    var leaderBoardPendingUpdate = true

    private val navHostFragment : NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(setOf(R.id.viewPagerFragment, R.id.quizFragment))
    }

    val actionBar: MaterialToolbar by lazy {
        findViewById(R.id.topAppBar)
    }

    val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigationView)
    }

    private var logoutDialog: DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.viewState.observe(this, observeViewState())
        viewModel.observeInternetConnection()
        setViews()
    }

    private fun setViews() {
        setContentView(R.layout.activity_main)

        // Set app bar
        actionBar.setupWithNavController(navController, appBarConfiguration)
        actionBar.setOnMenuItemClickListener(actionBarMenuItemClickListener())

        // Set bottom nav bar
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView
                .setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener())
    }

    private fun actionBarMenuItemClickListener() = Toolbar.OnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.logout -> {
                displayLogoutAlert()
                true
            }
            else -> false
        }
    }

    // If the viewPager is active, navigate between pages
    // If the quiz fragment is active, navigate to viewPager with the page index safe arg
    private fun bottomNavigationItemSelectedListener() = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (val activeFragment = getActiveFragment()) {
            is ViewPagerFragment -> {
                when(menuItem.itemId) {
                    R.id.homeFragment -> {
                        menuItem.isCheckable = true;
                        activeFragment.setCurrentItem(HOME_PAGE_INDEX)
                    }
                    R.id.leaderboardFragment -> {
                        activeFragment.setCurrentItem(LEADER_BOARD_PAGE_INDEX)
                    }
                }
            }
            is QuizFragment -> {
                when(menuItem.itemId) {
                    R.id.homeFragment -> {
                        menuItem.isCheckable = true;
                        val navAction =  QuizFragmentDirections
                                .actionQuizFragmentToViewPagerFragment(HOME_PAGE_INDEX)
                        navController.navigate(navAction)
                    }
                    R.id.leaderboardFragment -> {
                        val navAction =  QuizFragmentDirections
                                .actionQuizFragmentToViewPagerFragment(LEADER_BOARD_PAGE_INDEX)
                        navController.navigate(navAction)
                    }
                }
            }
        }
        true
    }

    private fun displayLogoutAlert() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.logout))
            .setMessage(resources.getString(R.string.logout_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.logout)) { dialog, _ ->
                viewModel.logout()
                logoutDialog = dialog
            }
            .show()
    }

    private fun observeViewState() = Observer<MainViewState> { viewState ->
        when(viewState.state) {
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            CONNECTION_CHANGE -> {
                // Get the active fragment
                val baseFragment = supportFragmentManager.fragments
                    .first()?.childFragmentManager?.fragments?.get(0) as BaseFragment<*, *>

                isConnectedToInternet = viewState.isConnected
                if (viewState.isConnected) {
                    baseFragment.onInternetConnected()
                } else {
                    baseFragment.onInternetDisconnected()
                }
            }
            LOADING -> {

            }
            LOGOUT -> {
                logoutDialog?.dismiss()
                navController.navigate(R.id.loginFragment)
            }
        }
    }

    fun makeToast(toastMessage: String?) {
        Toast.makeText(
            this,
            toastMessage,
            Toast.LENGTH_LONG
        ).show()
    }

    fun showBottomNavigation(isVisible: Boolean) {
        if (isVisible) {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }
    }

    private fun getActiveFragment(): Fragment? {
        return supportFragmentManager.fragments
                .first()?.childFragmentManager?.fragments?.get(0)
    }
}