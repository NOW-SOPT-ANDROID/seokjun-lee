package com.sopt.now.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding

class CommonListAdapter(
    private val userData: CommonItem,
    private var friendList: List<CommonItem>
): RecyclerView.Adapter<CommonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            CommonViewType.USER_VIEW.ordinal -> {
                CommonViewHolder.UserViewHolder(
                    ItemUserBinding.inflate(inflater, parent, false))
            }
            else -> { //CommonViewType.FRIEND_VIEW.ordinal
                CommonViewHolder.FriendViewHolder(
                    ItemFriendBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        if(holder is CommonViewHolder.FriendViewHolder && position > 0){
            holder.bind(friendList[position])
        } else if(holder is CommonViewHolder.UserViewHolder) {
            holder.bind(userData)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) CommonViewType.USER_VIEW.ordinal else CommonViewType.FRIEND_VIEW.ordinal
    }

    override fun getItemCount(): Int = friendList.size

    fun setFriendList(friends: List<CommonItem>) {
        this.friendList = friends.toList()
        notifyDataSetChanged()
    }
}