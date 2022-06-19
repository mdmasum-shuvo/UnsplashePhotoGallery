package com.masum.gallery.view

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.masum.gallery.R
import com.masum.gallery.common.BaseFragment
import com.masum.gallery.common.ResponseResult
import com.masum.gallery.databinding.FragmentGalleryBinding
import com.masum.gallery.utls.PaginationListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryFragment : BaseFragment(R.layout.fragment_gallery), GalleryAdapter.Interaction {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var adapter: GalleryAdapter
    private var currentPage: Int = PaginationListener.PAGE_START
    private var isLastPage = false
    private var totalPage = 10
    private var isLoading = false

    private lateinit var layoutManager: GridLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)
        viewModel.getGalleryPhoto(currentPage!!)
        adapter = GalleryAdapter(this)
        layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rv.layoutManager = layoutManager
        binding.adapter = adapter
        initListener()
        observeData()
    }



    override fun initListener() {
        binding.rv.addOnScrollListener(object : PaginationListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                adapter.loadingEffect(true)
                viewModel.getGalleryPhoto(currentPage)
            }

            override fun isLastPage(): Boolean {
                return isLastPage;
            }

            override fun isLoading(): Boolean {
                return isLoading;
            }


        })

        binding.refresh.setOnRefreshListener {
            viewModel.getGalleryPhoto(1)
        }
    }

    override fun observeData() {
        viewModel.galleryData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    if (currentPage == 1) {
                        binding.refresh.isRefreshing = true
                    }
                }
                is ResponseResult.Success -> {
                    isLoading = false
                    if (currentPage == 1) {
                        isLastPage = false
                        binding.refresh.isRefreshing = false
                        adapter.submitList(result.data!!)
                    } else
                        adapter.addData(result.data!!)
                }
                is ResponseResult.Error -> {
                    binding.refresh.isRefreshing = false
                    isLoading = false
                    isLastPage = true
                    if (currentPage == 1) {
                        adapter.submitList(emptyList())
                    } else {
                        adapter.loadingEffect(false)
                    }

                }
            }

        }
    }

    override fun onItemClicked(url: String) {
        findNavController().navigate(
            GalleryFragmentDirections.actionGalleryFragmentToFullScreenPhotoFragment(
                url
            )
        )
    }


}