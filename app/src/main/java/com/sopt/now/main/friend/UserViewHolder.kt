package com.sopt.now.main.friend

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding
import com.sopt.now.models.User

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(user: User) {
        binding.run {
            //itemUserIvProfile.setImageResource(friendData.profileImage)
            itemUserTvName.text = user.nickName
            itemUserTvSelfDescription.text = user.nickName
        }
    }
}