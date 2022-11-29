package com.omkarmarne.groupchat.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.omkarmarne.groupchat.GroupChatApplication
import com.omkarmarne.groupchat.databinding.FragmentGroupAddMemberBinding
import com.omkarmarne.groupchat.models.User
import com.omkarmarne.groupchat.utility.listeners.OnMemberAddListener
import com.omkarmarne.groupchat.utility.listeners.OnUserClickListener
import com.omkarmarne.groupchat.view.adapter.MemberAddAdapter
import com.omkarmarne.groupchat.viewModels.ChatViewModelFactory
import com.omkarmarne.groupchat.viewModels.GroupAddMemberViewModel

class GroupAddMemberFragment(private val onMemberAddListener: OnMemberAddListener) :
    Fragment(), OnUserClickListener {

    private var groupId: String? = null

    private lateinit var binding: FragmentGroupAddMemberBinding
    private lateinit var adapter: MemberAddAdapter

    private var users: MutableList<User> = ArrayList()
    private var selectedUsers: MutableList<User> = ArrayList()

    private val groupAddMemberViewModel: GroupAddMemberViewModel by viewModels {
        ChatViewModelFactory((activity?.application as GroupChatApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupId = it.getString(ARG_GROUP_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupAddMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupInitialView()

        groupId?.let {
            groupAddMemberViewModel.getUsersNotInGroup(it)
                .observe(viewLifecycleOwner) { tempMembers ->
                    if (tempMembers.isEmpty()) {
                        return@observe
                    }
                    users.clear()
                    users.addAll(tempMembers)
                    adapter.notifyDataSetChanged()
                }
        }
    }

    override fun onUserActionClick(user: User) {
        if (selectedUsers.contains(user)) {
            selectedUsers.remove(user)
        } else {
            selectedUsers.add(user)
        }
    }

    private fun setupInitialView() {
        binding.addMemberList.layoutManager = LinearLayoutManager(requireContext())
        adapter = MemberAddAdapter(this, users)
        binding.addMemberList.adapter = adapter

        binding.addMember.setOnClickListener {
            selectedUsers.forEach {
                onMemberAddListener.onMemberAddClick(it)
            }
            requireActivity().onBackPressed()
        }

        binding.groupAddMembersToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of this bottom sheet fragment
         * using the provided parameters.
         *
         * @param groupId id of the group.
         *
         * @return A new instance of fragment GroupAddMemberBottomSheetFragment.
         */
        fun newInstance(onMemberAddListener: OnMemberAddListener, groupId: String?) =
            GroupAddMemberFragment(onMemberAddListener).apply {
                arguments = Bundle().apply {
                    putString(ARG_GROUP_ID, groupId)
                }
            }

        private const val ARG_GROUP_ID = "group_id"
    }

    override fun onUserClick(
        userId: String,
        userName: String,
        userNumber: String,
        profileImage: String
    ) {
        /**
         * Not needed
         */
    }
}