package com.masum.gallery.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.masum.gallery.R
import com.masum.gallery.databinding.FooterStateBinding
import com.masum.gallery.databinding.ItemGalleryBinding
import com.masum.gallery.model.GalleryResponseItem


class GalleryAdapter(private val interaction: Interaction? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isLoading = true
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GalleryResponseItem>() {

        override fun areItemsTheSame(
            oldItem: GalleryResponseItem,
            newItem: GalleryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GalleryResponseItem,
            newItem: GalleryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding: ItemGalleryBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_gallery,
                    parent,
                    false
                )
                return DataViewHolder(binding,interaction)
            }
            VIEW_TYPE_LOADING -> {
                val binding: FooterStateBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.footer_state,
                    parent,
                    false
                )
                return LoadingViewHolder(binding)
            }
            else -> return throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DataViewHolder -> {
                holder.bind(differ.currentList[position])
            }
            is LoadingViewHolder -> {
                holder.bindLoader(isLoading)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList.size - 1 == position) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
    }

    override fun getItemCount(): Int {
        return if (differ.currentList == null) 0 else differ.currentList.size
    }

    // mPostItems == null ? 0 : mPostItems.size();
    fun submitList(list: List<GalleryResponseItem>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<GalleryResponseItem>) {
        val difList = differ.currentList.toMutableList()
        difList.addAll(list)
        differ.submitList(difList)
        notifyDataSetChanged()

    }

    fun loadingEffect(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    class DataViewHolder constructor(private val binding: ItemGalleryBinding,private val interaction: Interaction?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GalleryResponseItem) {
            binding.imageUrl = item.urls!!.thumb

            binding.item.setOnClickListener {
                interaction!!.onItemClicked(item.urls!!.full!!)
            }
        }
    }

    class LoadingViewHolder constructor(
        private val binding: FooterStateBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindLoader(isLoading: Boolean) {
            if (isLoading) {
                binding.loadingView.visibility = View.VISIBLE
            } else {
                binding.loadingView.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

    interface Interaction {
        fun onItemClicked(url:String)
    }
}