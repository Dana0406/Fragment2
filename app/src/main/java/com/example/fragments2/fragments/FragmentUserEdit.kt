package com.example.fragments2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.fragments2.R
import com.example.fragments2.UserActionListener
import com.example.fragments2.model.User

class FragmentUserEdit : Fragment(R.layout.fragment_edit_user) {
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var surnameEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var currentUser: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton = view.findViewById<Button>(R.id.saveEditions)
        cancelButton = view.findViewById<Button>(R.id.deleteEditions)
        surnameEditText = view.findViewById(R.id.surnameEditText)
        nameEditText = view.findViewById(R.id.nameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)

        currentUser = arguments?.getParcelable<User>(ARG_CURRENT_USER)!!

        nameEditText.setText(currentUser?.firstName)
        surnameEditText.setText(currentUser?.lastName)
        phoneNumberEditText.setText(currentUser?.phoneNumber)

        saveButton.setOnClickListener {
            if(dataChecking(currentUser)){
                val updatedUser = getUpdatedUserData(currentUser)
                val result = Bundle().apply {
                    putParcelable(ARG_UPDATED_USER, updatedUser)
                }
                parentFragmentManager.setFragmentResult(FRAGMENT_USER_EDIT_TAG, result)
                parentFragmentManager.popBackStack()
            }
        }

        cancelButton.setOnClickListener {
            (requireActivity() as onButtonsClicked).cancelButtonClicked()
        }
    }

    private fun dataChecking(user: User): Boolean {
        return if(surnameEditText.text.toString().isEmpty()) {
            surnameEditText.error = "Введите данные"
            false
        } else if (phoneNumberEditText.text.toString().isEmpty()) {
            phoneNumberEditText.error = "Введите данные"
            false
        } else {
            true
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun getUpdatedUserData(existingUser: User): User {
        val updatedFirstName = nameEditText.text.toString()
        val updatedLastName = surnameEditText.text.toString()
        val updatedPhoneNumber = phoneNumberEditText.text.toString()

        return User(
            id = existingUser.id,
            firstName = updatedFirstName,
            lastName = updatedLastName,
            phoneNumber = updatedPhoneNumber,
            photo = existingUser.photo
        )
    }

    interface onButtonsClicked {
        fun saveButtonClicked(updateUser: User)
        fun cancelButtonClicked()
    }

    companion object {
        const val FRAGMENT_USER_EDIT_TAG = "FRAGMENT_USER_EDIT_TAG"
        private const val ARG_CURRENT_USER = "current_user"
        const val ARG_UPDATED_USER = "updated_user"

        fun newInstance(currentUser: User): FragmentUserEdit {
            val fragment = FragmentUserEdit()
            val args = Bundle()
            args.putParcelable(ARG_CURRENT_USER, currentUser)
            fragment.arguments = args
            return fragment
        }
    }
}