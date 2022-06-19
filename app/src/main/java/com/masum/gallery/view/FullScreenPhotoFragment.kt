package com.masum.gallery.view

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.text.Html
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.Person.fromBundle
import com.masum.gallery.R
import com.masum.gallery.common.BaseFragment
import com.masum.gallery.databinding.FragmentFullScreenPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenPhotoFragment : BaseFragment(R.layout.fragment_full_screen_photo) {

    private lateinit var binding:FragmentFullScreenPhotoBinding
    private val mScaleFactor = 1.0f
    private var scaleGestureDetector: ScaleGestureDetector? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFullScreenPhotoBinding.bind(view)
        requireArguments().let {
            FullScreenPhotoFragmentArgs.fromBundle(it).also { args ->
                binding.imageUrl=args.url
            }
        }
    }


    override fun initListener() {

    }

    override fun observeData() {
    }

}