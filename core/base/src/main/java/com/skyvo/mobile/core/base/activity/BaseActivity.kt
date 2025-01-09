package com.skyvo.mobile.core.base.activity

import androidx.appcompat.app.AppCompatActivity
import com.skyvo.mobile.core.base.viewmodel.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM

    abstract fun setBottomNavigationVisibility(visibility: Boolean?)
}