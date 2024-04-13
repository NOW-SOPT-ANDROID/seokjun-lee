package com.sopt.now.main.adapter

import android.view.View
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding

sealed class CommonViewHolder(
    binding: ViewBinding
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: CommonItem)

    class UserViewHolder(
        private val binding: ItemUserBinding
    ): CommonViewHolder(binding) {
        override fun bind(item: CommonItem) {
            val viewObject = item.viewObject as ViewObject.UserViewObject
            binding.itemUserIvProfile.setImageResource(viewObject.image)
            binding.itemUserTvName.text = viewObject.name
            binding.itemUserTvSelfDescription.text = viewObject.description
        }
    }

    class FriendViewHolder(
        private val binding: ItemFriendBinding
    ): CommonViewHolder(binding) {
        override fun bind(item: CommonItem) {
            val viewObject = item.viewObject as ViewObject.FriendViewObject
            binding.ivProfile.setImageResource(viewObject.profileImage)
            binding.tvName.text = viewObject.name
            binding.tvSelfDescription.text = viewObject.selfDescription
        }

    }
}