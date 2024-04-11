package com.sopt.week02.friend

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding

class FriendViewHolder(private val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(friendData: Friend) {
        binding.run {
            ivProfile.setImageResource(friendData.profileImage)
            tvName.text = friendData.name
            tvSelfDescription.text = friendData.selfDescription
        }
    }
}