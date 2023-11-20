package com.example.fragments2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragments2.fragments.FragmentUserEdit
import com.example.fragments2.fragments.FragmentUsersList
import com.example.fragments2.model.User

class MainActivity : AppCompatActivity(), UserActionListener, FragmentUserEdit.onButtonsClicked {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(supportFragmentManager.findFragmentByTag(FragmentUsersList.FRAGMENT_USER_LIST_TAG)== null){
            with(supportFragmentManager.beginTransaction()){
                replace(R.id.fragmentContainer, FragmentUsersList.newInstance(),
                    FragmentUsersList.FRAGMENT_USER_LIST_TAG)
                addToBackStack(FragmentUsersList.FRAGMENT_USER_LIST_TAG)
                commit()
            }
        }
    }

    override fun onUserEdit(user: User) {
        val editFragment = FragmentUserEdit.newInstance(user)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, editFragment, FragmentUserEdit.FRAGMENT_USER_EDIT_TAG)
            addToBackStack(FragmentUserEdit.FRAGMENT_USER_EDIT_TAG)
            commit()
        }
    }

    override fun saveButtonClicked(updateUser: User) {
        supportFragmentManager.popBackStack()
    }


    override fun cancelButtonClicked() {
        supportFragmentManager.popBackStack()
    }

    override fun updateUser(updatedUser: User) {
        val userListFragment =
            supportFragmentManager.findFragmentByTag(FragmentUsersList.FRAGMENT_USER_LIST_TAG) as? FragmentUsersList

        userListFragment?.updateUser(updatedUser)
    }
}