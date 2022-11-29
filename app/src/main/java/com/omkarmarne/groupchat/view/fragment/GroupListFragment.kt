package com.omkarmarne.groupchat.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.omkarmarne.groupchat.GroupChatApplication
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.FragmentGroupListBinding
import com.omkarmarne.groupchat.models.Groups
import com.omkarmarne.groupchat.utility.LoginSessionHandler
import com.omkarmarne.groupchat.utility.Utils
import com.omkarmarne.groupchat.utility.listeners.OnGroupClickListener
import com.omkarmarne.groupchat.view.adapter.GroupAdapter
import com.omkarmarne.groupchat.viewModels.ChatViewModelFactory
import com.omkarmarne.groupchat.viewModels.GroupListViewModel


/**
 * A [Fragment] subclass for group list screen.
 * - Shows list of groups
 * - On group click, navigate to group messages
 *
 * Use the [GroupListFragment.newInstance] factory method to create an instance of this fragment.
 */
class GroupListFragment : Fragment(), OnGroupClickListener {

    private lateinit var binding: FragmentGroupListBinding
    private lateinit var adapter: GroupAdapter
    private var groupsList: MutableList<Groups> = ArrayList()

    private val groupListViewModel: GroupListViewModel by viewModels {
        ChatViewModelFactory((activity?.application as GroupChatApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGroupListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupInitialView()

        LoginSessionHandler(requireContext()).getUserId()?.let { userId ->
            groupListViewModel.getGroupsOfUser(userId).observe(viewLifecycleOwner) { groups ->
                if (groups.isEmpty()) {
                    binding.groupsEmpty.visibility = View.VISIBLE
                    binding.groups.visibility = View.GONE
                    return@observe
                }

                binding.groupsEmpty.visibility = View.GONE
                binding.groups.visibility = View.VISIBLE

                groupsList.clear()
                groupsList.addAll(groups)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        groupsList.clear()
    }

    override fun onGroupClick(groupId: String, groupName: String, profileImage: String) {
        Utils().navigateFragment(
            requireActivity().supportFragmentManager,
            GroupMessagesFragment.newInstance(groupId, groupName, profileImage)
        )
    }

    private fun setupInitialView() {
        binding.groups.layoutManager = LinearLayoutManager(requireContext())
        adapter = GroupAdapter(this@GroupListFragment, groupsList)
        binding.groups.adapter = adapter

        binding.groupListToolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.user_profile) {
                val loginSessionHandler = LoginSessionHandler(requireContext())
                Utils().navigateFragment(
                    requireActivity().supportFragmentManager,
                    UserProfileFragment.newInstance(
                        loginSessionHandler.getUserId(),
                        loginSessionHandler.getProfilePicture(),
                        loginSessionHandler.getName(),
                        loginSessionHandler.getMobile()
                    )
                )
            }

            true
        }

        binding.groupCreate.setOnClickListener {
            Toast.makeText(requireContext(), R.string.work_in_progress, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment.
         *
         * @return A new instance of fragment ChatListFragment.
         */
        @JvmStatic
        fun newInstance() = GroupListFragment()
    }
}