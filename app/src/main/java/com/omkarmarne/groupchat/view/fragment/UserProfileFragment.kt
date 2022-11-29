package com.omkarmarne.groupchat.view.fragment

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.omkarmarne.groupchat.GroupChatApplication
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.databinding.FragmentUserProfileBinding
import com.omkarmarne.groupchat.models.Groups
import com.omkarmarne.groupchat.utility.LoginSessionHandler
import com.omkarmarne.groupchat.utility.Utils
import com.omkarmarne.groupchat.utility.listeners.OnGroupClickListener
import com.omkarmarne.groupchat.view.adapter.GroupAdapter
import com.omkarmarne.groupchat.viewModels.ChatViewModelFactory
import com.omkarmarne.groupchat.viewModels.UserViewModel

/**
 * A simple [Fragment] subclass for User profile.
 * - Show User details:
 *      - Profile Image
 *      - Name
 *      - Mobile Number
 * - Allow user to edit the profile
 *
 * Use the [UserProfileFragment.newInstance] factory method to create an instance of this fragment.
 */
class UserProfileFragment : Fragment(), OnGroupClickListener {

    private var userId: String? = null
    private var profilePicture: String? = null
    private var name: String? = null
    private var mobile: String? = null

    private var groups: MutableList<Groups> = ArrayList()

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var adapter: GroupAdapter

    private val userViewModel: UserViewModel by viewModels {
        ChatViewModelFactory((activity?.application as GroupChatApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(USER_ID)
            profilePicture = it.getString(USER_PROFILE_PICTURE)
            name = it.getString(USER_NAME)
            mobile = it.getString(USER_MOBILE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setupInitialView()

        userId?.let {
            userViewModel.getGroupsOfUser(it).observe(viewLifecycleOwner) { groups ->
                if (groups.isEmpty()) {
                    Toast.makeText(requireContext(), "No Groups to show", Toast.LENGTH_SHORT)
                        .show()
                    return@observe
                }

                this.groups.addAll(groups)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        groups.clear()
    }

    override fun onGroupClick(groupId: String, groupName: String, profileImage: String) {
        Utils().navigateFragment(
            requireActivity().supportFragmentManager,
            GroupMessagesFragment.newInstance(groupId, groupName, profileImage)
        )
    }

    private fun setupInitialView() {
        binding.userGroups.layoutManager = LinearLayoutManager(requireContext())
        adapter = GroupAdapter(this@UserProfileFragment, groups)
        binding.userGroups.adapter = adapter

        Glide.with(this)
            .load(Uri.parse(profilePicture))
            .error(R.drawable.ic_image_error)
            .placeholder(R.drawable.ic_image_downloading)
            .fitCenter()
            .into(binding.userProfileImage)

        binding.userNameTil.text = Editable.Factory.getInstance().newEditable(name)
        binding.userMobileTil.text = Editable.Factory.getInstance().newEditable(mobile)

        if (LoginSessionHandler(requireContext()).getUserId().equals(userId)) {
            binding.userProfileImageEdit.visibility = View.VISIBLE
            binding.userNameEdit.visibility = View.VISIBLE
            binding.userMobileEdit.visibility = View.VISIBLE

            binding.userProfileImageEdit.setOnClickListener {
                Toast.makeText(requireContext(), R.string.work_in_progress, Toast.LENGTH_SHORT)
                    .show()
            }

            binding.userNameEdit.setOnClickListener {
                Toast.makeText(requireContext(), R.string.work_in_progress, Toast.LENGTH_SHORT)
                    .show()
            }

            binding.userMobileEdit.setOnClickListener {
                Toast.makeText(requireContext(), R.string.work_in_progress, Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            binding.userProfileImageEdit.visibility = View.GONE
            binding.userNameEdit.visibility = View.GONE
            binding.userMobileEdit.visibility = View.GONE
        }

        binding.userProfileToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment using the provided
         * parameters.
         *
         * @param profilePicture profile picture url.
         * @param name name of the user.
         * @param mobile mobile number of user.
         *
         * @return A new instance of fragment UserProfileFragment.
         */
        @JvmStatic
        fun newInstance(userId: String?, profilePicture: String?, name: String?, mobile: String?) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userId)
                    putString(USER_PROFILE_PICTURE, profilePicture)
                    putString(USER_NAME, name)
                    putString(USER_MOBILE, mobile)
                }
            }

        private const val USER_ID = "user_id"
        private const val USER_PROFILE_PICTURE = "profile_picture"
        private const val USER_NAME = "name"
        private const val USER_MOBILE = "mobile"
    }
}