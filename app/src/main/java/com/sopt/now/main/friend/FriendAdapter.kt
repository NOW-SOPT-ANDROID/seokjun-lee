package com.sopt.now.main.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding
import com.sopt.now.models.User

class FriendAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    // 임시의 빈 리스트
    private var friendList: List<Friend> = emptyList()
    private var userData: User = User()

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> UserViewHolder(ItemUserBinding.inflate(inflater, parent, false))
            else -> FriendViewHolder(ItemFriendBinding.inflate(inflater, parent, false))
        }

        /*val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendBinding.inflate(inflater, parent, false)
        return FriendViewHolder(binding)*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //FriendViewHolder의 onBind() 메서드
        //holder.onBind(friendList[position])

        if (holder is FriendViewHolder && position > 0) {
            holder.onBind(friendList[position])
        } else if(holder is UserViewHolder) {
            holder.onBind(userData)
        }
    }

    override fun getItemCount() = friendList.size

    fun setFriendList(friendList: List<Friend>) {
        this.friendList = friendList.toList()
        notifyDataSetChanged()
    }

    fun setUser(user: User){
        userData = user
    }
}