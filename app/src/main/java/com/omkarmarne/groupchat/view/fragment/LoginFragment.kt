package com.omkarmarne.groupchat.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.omkarmarne.groupchat.utility.LoginSessionHandler
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.utility.Utils

import com.omkarmarne.groupchat.databinding.FragmentLoginBinding

/**
 * A [Fragment] subclass for Login screen.
 * - User should be able to insert mobile number & OTP to login
 * - If login successful, move to next screen.
 * - Don't keep this fragment in stack (shouldn't be visible if navigated back from other screens)
 *
 * Use the [LoginFragment.newInstance] factory method to create an instance of this fragment.
 */
// TODO: Resend button for OTP
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    // Flag variable for showing UI
    // if true, show mobile number UI
    // if false, show OPT UI
    private var isMobileView = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Show Mobile UI
        showMobileUI()
        isMobileView = true

        binding.loginButton.setOnClickListener {
            if (isMobileView) {
                if (verifyPhoneNumber(binding.loginInputTiet.text.toString())) {
                    // Update UI for OTP
                    showOTPUI()
                    isMobileView = false
                } else {
                    Toast.makeText(context, R.string.login_mobile_error, Toast.LENGTH_SHORT).show()
                }
            } else {
                // TODO: Add correct logic to verify OTP
                // For now, navigating to next activity on entering any 6 digit number
                if (verifyOTP(binding.loginInputTiet.text.toString())) {
                    // TODO: Fetch user details from server & store it in shared preferences
                    // For now, using fake data
                    LoginSessionHandler(requireContext()).login(
                        "user1000",
                        getString(R.string.user_fake_profile_picture),
                        getString(R.string.user_fake_name),
                        getString(R.string.user_fake_mobile)
                    )

                    Utils().navigateFragment(
                        requireActivity().supportFragmentManager,
                        GroupListFragment.newInstance()
                    )
                } else {
                    Toast.makeText(context, R.string.login_otp_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Verify mobile number
    private fun verifyPhoneNumber(mobileNumber: String?): Boolean {
        mobileNumber?.let {
            return it.length == 10
        }

        return false
    }

    // Verify OTP number
    private fun verifyOTP(otp: String?): Boolean {
        otp?.let {
            return it.length == 6
        }

        return false
    }

    private fun showMobileUI() {
        binding.loginInputTil.hint = getString(R.string.login_mobile_number)
        binding.loginInputTiet.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.loginInputTiet.text?.clear()
        binding.loginButton.text = getString(R.string.login_otp_get)
        binding.loginInputTiet.requestFocus()
    }

    private fun showOTPUI() {
        binding.loginInputTil.hint = getString(R.string.login_otp)
        binding.loginInputTiet.imeOptions = EditorInfo.IME_ACTION_NEXT
        binding.loginInputTiet.text?.clear()
        binding.loginButton.text = getString(R.string.login_otp_verify)
        binding.loginInputTiet.requestFocus()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment.
         *
         * @return A new instance of fragment LoginFragment.
         */
        fun newInstance() = LoginFragment()
    }
}