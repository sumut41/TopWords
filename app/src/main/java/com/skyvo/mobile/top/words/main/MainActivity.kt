package com.skyvo.mobile.top.words.main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skyvo.mobile.core.base.activity.BaseActivity
import com.skyvo.mobile.core.base.deeplink.DeeplinkManager
import com.skyvo.mobile.core.base.navigation.deeplinkNavigate
import com.skyvo.mobile.top.words.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var borderView: View

    @Inject
    lateinit var deeplinkManager: DeeplinkManager

    override fun setBottomNavigationVisibility(visibility: Boolean?) {
        if (::bottomNavigationView.isInitialized) {
            bottomNavigationView.isVisible = visibility ?: false
            borderView.isVisible = visibility ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        borderView = findViewById(R.id.line)
        navigateSetup()
        onBackListener()
    }

    private fun navigateSetup() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        deeplinkManager.setNavController(navController)
    }

    private fun onBackListener() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == com.skyvo.mobile.core.base.R.id.wordsDashboardFragment ||
                    navController.currentDestination?.id == com.skyvo.mobile.core.base.R.id.booksDashboardFragment ||
                    navController.currentDestination?.id == com.skyvo.mobile.core.base.R.id.menuFragment
                ) {
                   /* navController.deeplinkNavigate(
                        navDeeplinkDestination =
                    ) */
                    Toast.makeText(applicationContext, "ÇIKIŞ", Toast.LENGTH_LONG).show()
                } else if (navController.currentDestination?.id == R.id.splashFragment) {
                    finish()
                } else {
                    navController.popBackStack()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}