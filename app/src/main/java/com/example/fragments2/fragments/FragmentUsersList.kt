package com.example.fragments2.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragments2.App
import com.example.fragments2.R
import com.example.fragments2.UserActionListener
import com.example.fragments2.UsersAdapter
import com.example.fragments2.model.User
import com.example.fragments2.model.UsersListener
import com.example.fragments2.model.UsersService

class FragmentUsersList: Fragment(R.layout.fragment_list) {
    private lateinit var adapter: UsersAdapter
    private lateinit var recyclerView: RecyclerView

    private val usersService: UsersService
        get() = (requireActivity().application as App).usersService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.usersRecyclerView)

        adapter = UsersAdapter(object: UserActionListener {
            override fun onUserEdit(user: User) {
                val editFragment = FragmentUserEdit.newInstance(user)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, editFragment, FragmentUserEdit.FRAGMENT_USER_EDIT_TAG)
                    addToBackStack(FragmentUserEdit.FRAGMENT_USER_EDIT_TAG)
                    commit()
                }
            }

            override fun updateUser(updatedUser: User) {
                adapter.updateUser(updatedUser)
            }
        })

        val layoutManager = LinearLayoutManager(view.context)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        parentFragmentManager.setFragmentResultListener(
            FragmentUserEdit.FRAGMENT_USER_EDIT_TAG,
            viewLifecycleOwner
        ) { _, result ->
            val updatedUser = result.getParcelable<User>(FragmentUserEdit.ARG_UPDATED_USER)
            if (updatedUser != null) {
                updateUser(updatedUser)
            }
        }

        usersService.addListener(usersListener)
    }

    private val usersListener: UsersListener = {
        adapter.users = it as MutableList<User>
    }

    fun updateUser(updatedUser: User) {
        adapter.updateUser(updatedUser)
    }

    companion object{
        const val FRAGMENT_USER_LIST_TAG = "FRAGMENT_USER_LIST_TAG"

        private const val ARG_UPDATED_USER = "updated_user"

        fun newInstance() = FragmentUsersList()
    }
}