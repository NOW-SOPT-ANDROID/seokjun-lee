package com.sopt.now.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding

class CommonListAdapter(
    private var memberList: List<CommonItem>
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
        if(holder is CommonViewHolder.UserViewHolder && position == 0){
            holder.bind(memberList[position])
        } else if(holder is CommonViewHolder.FriendViewHolder) {
            holder.bind(memberList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return CommonViewType.valueOf(memberList[position].viewType).ordinal
    }

    override fun getItemCount(): Int = memberList.size

    fun setFriendList(friends: List<CommonItem>) {
        this.memberList = friends.toList()
        notifyDataSetChanged()
    }
}