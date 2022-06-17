package com.masum.gallery.view

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.masum.gallery.R
import com.masum.gallery.common.BaseFragment
import com.masum.gallery.databinding.FragmentFullScreenPhotoBinding


class FullScreenPhotoFragment : BaseFragment(R.layout.fragment_full_screen_photo) {

    private lateinit var binding:FragmentFullScreenPhotoBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFullScreenPhotoBinding.bind(view)

    }

    override fun initListener() {

    }

    override fun observeData() {
    }

}