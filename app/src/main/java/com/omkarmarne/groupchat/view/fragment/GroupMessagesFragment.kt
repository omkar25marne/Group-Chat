package com.omkarmarne.groupchat.view.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.omkarmarne.groupchat.GroupChatApplication
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.FragmentGroupMessagesBinding
import com.omkarmarne.groupchat.models.Message
import com.omkarmarne.groupchat.models.MessageWithUser
import com.omkarmarne.groupchat.utility.LoginSessionHandler
import com.omkarmarne.groupchat.utility.Utils
import com.omkarmarne.groupchat.utility.listeners.OnMessageLongClickListener
import com.omkarmarne.groupchat.view.adapter.MessageAdapter
import com.omkarmarne.groupchat.viewModels.ChatViewModelFactory
import com.omkarmarne.groupchat.viewModels.MessageViewModel

/**
 * A [Fragment] subclass for group messages screen.
 * - Shows list of messages in the group with sender & timestamp
 * - Navigates to group details screen
 *
 * Use the [GroupMessagesFragment.newInstance] factory method to create an instance of this fragment.
 */
class GroupMessagesFragment : Fragment(), OnMessageLongClickListener {

    private var groupId: String? = null
    private var groupName: String? = null
    private var groupProfilePicture: String? = null

    private var messageList: MutableList<MessageWithUser> = ArrayList()

    private lateinit var binding: FragmentGroupMessagesBinding
    private lateinit var adapter: MessageAdapter

    private val messageViewModel: MessageViewModel by viewModels {
        ChatViewModelFactory((activity?.application as GroupChatApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupId = it.getString(ARG_GROUP_ID)
            groupName = it.getString(ARG_GROUP_NAME)
            groupProfilePicture = it.getString(ARG_GROUP_PROFILE_PICTURE)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGroupMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupInitialView()

        groupId?.let { tempGroupId ->
            messageViewModel.getGroupMessages(tempGroupId).observe(this) { tempMessageList ->
                if (tempMessageList.isEmpty()) {
                    binding.groupMessagesEmpty.visibility = View.VISIBLE
                    binding.groupMessages.visibility = View.GONE
                    return@observe
                }

                binding.groupMessagesEmpty.visibility = View.GONE
                binding.groupMessages.visibility = View.VISIBLE

                // Check if a new message is added in the list
                val isNewMessageAdded = messageList.isNotEmpty() &&
                        tempMessageList[tempMessageList.size - 1] != messageList[messageList.size - 1]

                messageList.clear()
                messageList.addAll(tempMessageList)

                adapter.notifyDataSetChanged()

                // Scroll to last message, only if a new message is added
                if (isNewMessageAdded)
                    binding.groupMessages.smoothScrollToPosition(messageList.size)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        messageList.clear()
    }

    override fun onMessageLongClick(message: Message) {
        val loggedInUserId = LoginSessionHandler(requireContext()).getUserId()
        loggedInUserId?.let {
            val likesBy: MutableList<String> = ArrayList()
            likesBy.addAll(message.likesBy)
            var likesCount = message.likes

            if (likesBy.contains(loggedInUserId)) {
                likesBy.remove(loggedInUserId)
                likesCount--
            } else {
                likesBy.add(loggedInUserId)
                likesCount++
            }

            messageViewModel.updateLikesForMessage(message.id, likesCount, likesBy)
        }
    }

    private fun setupInitialView() {
        Glide.with(this)
            .load(Uri.parse(groupProfilePicture))
            .error(R.drawable.ic_image_error)
            .placeholder(R.drawable.ic_image_downloading)
            .fitCenter()
            .into(binding.groupMessagesProfilePicture)

        binding.groupMessagesTitle.text = groupName
        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        binding.groupMessages.layoutManager = layoutManager
        adapter =
            MessageAdapter(this, messageList, LoginSessionHandler(requireContext()).getUserId())
        binding.groupMessages.adapter = adapter

        binding.groupMessagesToolbarContent.setOnClickListener {
            Utils().navigateFragment(
                requireActivity().supportFragmentManager,
                GroupDetailsFragment.newInstance(groupId, groupName, groupProfilePicture)
            )
        }

        binding.groupMessageSend.setOnClickListener {
            val text: String = binding.groupNewMessage.text.toString()
            if (text.isNotEmpty()) {
                val loginSessionHandler = LoginSessionHandler(requireContext())
                val groupId = groupId ?: ""
                val userId = loginSessionHandler.getUserId() ?: ""
                val userNumber = loginSessionHandler.getMobile() ?: ""

                if (groupId.isEmpty() || userId.isEmpty() || userNumber.isEmpty()) {
                    return@setOnClickListener
                }
                val message = Message(
                    groupId,
                    userId,
                    userNumber,
                    text,
                    System.currentTimeMillis(),
                    0,
                    arrayListOf()
                )

                binding.groupNewMessage.text.clear()
                messageViewModel.insert(message)
            }
        }

        binding.groupMessageToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @param groupId id of the group.
         * @param groupName name of the group.
         * @param groupProfilePicture profile picture of the group.
         *
         * @return A new instance of fragment MessageListFragment.
         */
        @JvmStatic
        fun newInstance(groupId: String, groupName: String, groupProfilePicture: String) =
            GroupMessagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_GROUP_ID, groupId)
                    putString(ARG_GROUP_NAME, groupName)
                    putString(ARG_GROUP_PROFILE_PICTURE, groupProfilePicture)
                }
            }

        private const val ARG_GROUP_ID = "group_id"
        private const val ARG_GROUP_NAME = "group_name"
        private const val ARG_GROUP_PROFILE_PICTURE = "group_profile_picture"
    }
}