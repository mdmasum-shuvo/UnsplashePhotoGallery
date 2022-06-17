package com.masum.gallery.common

import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutId: Int) :Fragment(layoutId) {

    abstract fun initListener()
    abstract fun observeData();
}