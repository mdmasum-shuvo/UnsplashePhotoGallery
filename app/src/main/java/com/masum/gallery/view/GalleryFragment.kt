package com.masum.gallery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.masum.gallery.R
import com.masum.gallery.common.BaseFragment
import com.masum.gallery.databinding.FragmentGalleryBinding


class GalleryFragment : BaseFragment(R.layout.fragment_gallery) {

    private lateinit var binding: FragmentGalleryBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)
    }

    override fun initListener() {
    }

    override fun observeData() {
    }


}