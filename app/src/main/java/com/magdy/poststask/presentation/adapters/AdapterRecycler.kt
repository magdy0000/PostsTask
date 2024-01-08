package com.magdy.poststask.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.magdy.poststask.R
import com.magdy.poststask.databinding.ItemPostsBinding
import com.magdy.poststask.presentation.models.ModelPostsPresentation

class AdapterRecycler(val init: (ModelPostsPresentation) -> Unit) : RecyclerView.Adapter<AdapterRecycler.Holder>() {

    var list: List<ModelPostsPresentation>? = null

    fun setData (postsList: List<ModelPostsPresentation>){
        val diffCallback = PostsDiffCallback(list ?: emptyList(), postsList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list = postsList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding = ItemPostsBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)
        holder.bind(data)
        Log.e("TAG", "onBindViewHolder: $position", )
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }


    inner class Holder(private val binding: ItemPostsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                init.invoke(list!![layoutPosition])
            }
        }

        fun bind(modelPosts: ModelPostsPresentation?) {
            binding.apply {
                textBody.text = modelPosts?.body
                textTitle.text = modelPosts?.title
            }
        }

    }
    private class PostsDiffCallback(
        private val oldList: List<ModelPostsPresentation>,
        private val newList: List<ModelPostsPresentation>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}