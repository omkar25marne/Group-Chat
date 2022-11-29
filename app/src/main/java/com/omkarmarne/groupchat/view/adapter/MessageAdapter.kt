package com.omkarmarne.groupchat.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.RowMessageMeBinding
import com.omkarmarne.groupchat.databinding.RowMessageOtherBinding
import com.omkarmarne.groupchat.models.MessageWithUser
import com.omkarmarne.groupchat.utility.Utils
import com.omkarmarne.groupchat.utility.listeners.OnMessageLongClickListener

class MessageAdapter(
    private val onMessageLongClickListener: OnMessageLongClickListener,
    private val messageWithUserList: List<MessageWithUser>,
    private val loggedUserId: String?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == MESSAGE_TYPE_ME) {
            val binding = RowMessageMeBinding.inflate(inflater, parent, false)
            MessageMeViewHolder(binding)
        } else { //if (viewType == MESSAGE_TYPE_OTHER) {
            val binding = RowMessageOtherBinding.inflate(inflater, parent, false)
            MessageOtherViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageWithUser = messageWithUserList[position]
        val timestamp = Utils().getDateTime(messageWithUser.message.timestamp)

        if (holder.itemViewType == MESSAGE_TYPE_ME) {
            val messageMeViewHolder = holder as MessageMeViewHolder
            messageMeViewHolder.binding.messageMeContent.text = messageWithUser.message.content
            messageMeViewHolder.binding.messageMeTimestamp.text = timestamp

            if (messageWithUser.message.likes > 0) {
                messageMeViewHolder.binding.likesCount.root.visibility = View.VISIBLE
                messageMeViewHolder.binding.likesCount.messageLikesCount.text =
                    messageMeViewHolder.binding.root.resources.getString(
                        R.string.message_like_count,
                        messageWithUser.message.likes
                    )
                messageMeViewHolder.binding.likesCount.messageLikesCount.setOnClickListener {
                    Toast.makeText(it.context, R.string.work_in_progress, Toast.LENGTH_SHORT).show()
                }
            } else {
                messageMeViewHolder.binding.likesCount.root.visibility = View.GONE
            }

            /**
             * I prefer not to allow user to like their own messages.
             * It doesn't really make sense for me.
             * The "like" action is an approval/appreciation in a sense.
             * The user approves/appreciates a thought & hence sends that thought as a message
             * in the group. He doesn't really need to approve/appreciate his own messages.
             */
//            messageMeViewHolder.binding.root.setOnLongClickListener {
//                onMessageLongClickListener.onMessageLongClick(messageWithUser.message)
//                true
//            }

        } else { // if (holder.itemViewType == MESSAGE_TYPE_ME) {
            val messageOtherViewHolder = holder as MessageOtherViewHolder
            messageOtherViewHolder.binding.messageOtherNumber.text = messageWithUser.user.userName
            messageOtherViewHolder.binding.messageOtherContent.text =
                messageWithUser.message.content
            messageOtherViewHolder.binding.messageOtherTimestamp.text = timestamp

            if (messageWithUser.message.likes > 0) {
                messageOtherViewHolder.binding.likesCount.root.visibility = View.VISIBLE
                messageOtherViewHolder.binding.likesCount.messageLikesCount.text =
                    messageOtherViewHolder.binding.root.resources.getString(
                        R.string.message_like_count,
                        messageWithUser.message.likes
                    )
                messageOtherViewHolder.binding.likesCount.messageLikesCount.setOnClickListener {
                    Toast.makeText(it.context, R.string.work_in_progress, Toast.LENGTH_SHORT).show()
                }
            } else {
                messageOtherViewHolder.binding.likesCount.root.visibility = View.GONE
            }

            messageOtherViewHolder.binding.root.setOnLongClickListener {
                onMessageLongClickListener.onMessageLongClick(messageWithUser.message)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return messageWithUserList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageWithUserList[position].message.senderId == loggedUserId)
            MESSAGE_TYPE_ME
        else
            MESSAGE_TYPE_OTHER
    }

    inner class MessageOtherViewHolder(binding: RowMessageOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: RowMessageOtherBinding

        init {
            this.binding = binding
        }
    }

    inner class MessageMeViewHolder(binding: RowMessageMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: RowMessageMeBinding

        init {
            this.binding = binding
        }
    }

    companion object {
        private const val MESSAGE_TYPE_ME = 1
        private const val MESSAGE_TYPE_OTHER = 2
    }
}