package com.omkarmarne.groupchat.view.fragment

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.omkarmarne.groupchat.GroupChatApplication
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.FragmentGroupDetailsBinding
import com.omkarmarne.groupchat.models.GroupUsers
import com.omkarmarne.groupchat.models.User
import com.omkarmarne.groupchat.utility.Utils
import com.omkarmarne.groupchat.utility.listeners.OnMemberAddListener
import com.omkarmarne.groupchat.utility.listeners.OnUserClickListener
import com.omkarmarne.groupchat.view.adapter.MemberAdapter
import com.omkarmarne.groupchat.viewModels.ChatViewModelFactory
import com.omkarmarne.groupchat.viewModels.GroupDetailsViewModel

/**
 * A simple [Fragment] subclass for group details.
 *  - Show group details
 *      - Group profile picture
 *      - Group name
 *      - Group member users
 *
 * Use the [GroupDetailsFragment.newInstance] factory method to create an instance of this fragment.
 */
class GroupDetailsFragment : Fragment(), OnUserClickListener, OnMemberAddListener {

    private var groupId: String? = null
    private var groupName: String? = null
    private var groupProfilePicture: String? = null

    private var members: MutableList<User> = ArrayList()

    private lateinit var binding: FragmentGroupDetailsBinding
    private lateinit var adapter: MemberAdapter

    private val groupDetailsViewModel: GroupDetailsViewModel by viewModels {
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
        binding = FragmentGroupDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupInitialView()

        groupId?.let {
            groupDetailsViewModel.getUsersInGroup(it).observe(viewLifecycleOwner) { tempMembers ->
                if (tempMembers.isEmpty()) {
                    return@observe
                }
                members.clear()
                members.addAll(tempMembers)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        members.clear()
    }

    override fun onUserClick(
        userId: String,
        userName: String,
        userNumber: String,
        profileImage: String
    ) {
        Utils().navigateFragment(
            requireActivity().supportFragmentManager,
            UserProfileFragment.newInstance(userId, profileImage, userName, userNumber)
        )
    }

    override fun onUserActionClick(user: User) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remove ${user.userName} from $groupName group?")
            .setPositiveButton("Yes") { _, _ ->
                groupId?.let {
                    groupDetailsViewModel.removeUserFromGroup(it, user.userId)
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onMemberAddClick(user: User) {
        groupId?.let {
            groupDetailsViewModel.insert(GroupUsers(user.userId, it, 0))
        }
    }

    private fun setupInitialView() {
        binding.groupMembers.layoutManager = LinearLayoutManager(requireContext())
        adapter = MemberAdapter(this@GroupDetailsFragment, members)
        binding.groupMembers.adapter = adapter

        Glide.with(this)
            .load(Uri.parse(groupProfilePicture))
            .error(R.drawable.ic_image_error)
            .placeholder(R.drawable.ic_image_downloading)
            .fitCenter()
            .into(binding.groupProfileImage)

        binding.groupNameTil.text = Editable.Factory.getInstance().newEditable(groupName)

        binding.groupProfileImageEdit.setOnClickListener {
            Toast.makeText(requireContext(), R.string.work_in_progress, Toast.LENGTH_SHORT).show()
        }

        binding.groupNameEdit.setOnClickListener {
            Toast.makeText(requireContext(), R.string.work_in_progress, Toast.LENGTH_SHORT).show()
        }

        binding.groupDetailsToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.groupProfileAddMember.setOnClickListener {
            Utils().navigateFragment(
                requireActivity().supportFragmentManager,
                GroupAddMemberFragment.newInstance(this, groupId)
            )
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
         * @return A new instance of fragment GroupDetailsFragment.
         */
        @JvmStatic
        fun newInstance(groupId: String?, groupName: String?, groupProfilePicture: String?) =
            GroupDetailsFragment().apply {
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