package com.omkarmarne.groupchat.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.RowGroupMemberBinding
import com.omkarmarne.groupchat.models.User
import com.omkarmarne.groupchat.utility.listeners.OnUserClickListener

class MemberAdapter(
    private val onUserClickListener: OnUserClickListener,
    private val membersList: List<User>
) :
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MemberViewHolder(RowGroupMemberBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val user: User = membersList[position]

        Glide.with(holder.binding.root.context)
            .load(Uri.parse(user.userProfilePicture))
            .error(R.drawable.ic_image_error)
            .placeholder(R.drawable.ic_image_downloading)
            .fitCenter()
            .into(holder.binding.memberProfilePicture)

        holder.binding.memberName.text = user.userName
        holder.binding.memberNumber.text = user.userNumber

        holder.binding.root.setOnClickListener {
            onUserClickListener.onUserClick(
                user.userId,
                user.userName,
                user.userNumber,
                user.userProfilePicture
            )
        }

        holder.binding.memberRemove.setOnClickListener {
            onUserClickListener.onUserActionClick(user)
        }
    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    inner class MemberViewHolder(binding: RowGroupMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: RowGroupMemberBinding

        init {
            this.binding = binding
        }
    }
}