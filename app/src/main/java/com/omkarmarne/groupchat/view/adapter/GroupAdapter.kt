package com.omkarmarne.groupchat.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.RowGroupBinding
import com.omkarmarne.groupchat.models.Groups
import com.omkarmarne.groupchat.utility.listeners.OnGroupClickListener

class GroupAdapter(
    private val onGroupClickListener: OnGroupClickListener,
    private val groupsList: List<Groups>
) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowGroupBinding.inflate(inflater, parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groupsList[position]

        Glide.with(holder.binding.root.context)
            .load(Uri.parse(group.groupProfileImage))
            .error(R.drawable.ic_image_error)
            .placeholder(R.drawable.ic_image_downloading)
            .fitCenter()
            .into(holder.binding.groupProfilePicture)

        holder.binding.groupName.text = group.groupName

        holder.binding.root.setOnClickListener {
            onGroupClickListener.onGroupClick(
                group.groupId,
                group.groupName,
                group.groupProfileImage
            )
        }
    }

    override fun getItemCount(): Int {
        return groupsList.size
    }

    inner class GroupViewHolder(binding: RowGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: RowGroupBinding

        init {
            this.binding = binding
        }
    }
}