package com.omkarmarne.groupchat.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.RowMemberAddBinding
import com.omkarmarne.groupchat.models.User
import com.omkarmarne.groupchat.utility.listeners.OnUserClickListener

/**
 * TODO:
 * KNOWN ISSUE:
 *      persist selected row UI when recyclerview is scrolled
 */
class MemberAddAdapter(
    private val onUserClickListener: OnUserClickListener,
    private val usersList: List<User>
) :
    RecyclerView.Adapter<MemberAddAdapter.MemberAddViewHolder>() {

    private lateinit var checkedStatus: MutableList<Boolean>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAddViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MemberAddViewHolder(RowMemberAddBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MemberAddViewHolder, position: Int) {
        val user: User = usersList[position]

        Glide.with(holder.binding.root.context)
            .load(Uri.parse(user.userProfilePicture))
            .error(R.drawable.ic_image_error)
            .placeholder(R.drawable.ic_image_downloading)
            .fitCenter()
            .into(holder.binding.memberProfilePicture)

        holder.binding.memberName.text = user.userName
        holder.binding.memberNumber.text = user.userNumber
        holder.binding.memberAdd.isChecked = checkedStatus[position]

        holder.binding.memberAdd.setOnCheckedChangeListener { _, isChecked ->
            checkedStatus[position] = isChecked
            onUserClickListener.onUserActionClick(user)
        }
    }

    override fun getItemCount(): Int {
        checkedStatus = emptyMutableList(usersList.size)
        return usersList.size
    }

    private fun emptyMutableList(size: Int): MutableList<Boolean> {
        return MutableList(size) {
            false
        }
    }

    inner class MemberAddViewHolder(binding: RowMemberAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: RowMemberAddBinding

        init {
            this.binding = binding
        }
    }
}