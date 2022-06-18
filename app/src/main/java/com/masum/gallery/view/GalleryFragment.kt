package com.masum.gallery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.masum.gallery.R
import com.masum.gallery.common.BaseFragment
import com.masum.gallery.common.Constant
import com.masum.gallery.common.ResponseResult
import com.masum.gallery.databinding.FragmentGalleryBinding
import com.masum.gallery.utls.PaginationListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : BaseFragment(R.layout.fragment_gallery) {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var adapter: FilterHistoryAdapter
    private var currentPage: Int = PaginationListener.PAGE_START
    private var isLastPage = false
    private var totalPage = 10
    private var isLoading = false
    private lateinit var layoutManager: GridLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)
        viewModel.getGalleryPhoto(currentPage!!)
        adapter=FilterHistoryAdapter()
        layoutManager = GridLayoutManager(requireActivity(),2)
        binding.rv.layoutManager=layoutManager
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
                    //_galleryData.value = ResponseResult.Loading()
                    if (currentPage == 1) {
                        binding.refresh.isRefreshing = true
                    }
                }
                is ResponseResult.Success -> {
                    // _galleryData.value = ResponseResult.Success(result.data)
                   // hideLoader()
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
                        //showEmptyView()
                        adapter.submitList(emptyList())
                    } else {
                     //   hideLoader()
                        adapter.loadingEffect(false)
                    }
                    //showErrorDialog("Error!", dataResource.message)
                    //adapter.removeLoader()
                    //AppUtils.message(binding!!.root, result.message)
                    }
                else -> {

                }
            }

        }
    }


}