package com.example.fragments2

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.fragments2.databinding.ItemUserBinding
import com.example.fragments2.model.User
import com.squareup.picasso.Picasso

interface UserActionListener{
    fun onUserEdit(user: User)
    fun updateUser(updatedUser: User)
}

class UsersAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    var users: MutableList<User> = mutableListOf()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(view: View) {
        val user = view.tag as User
        actionListener.onUserEdit(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding){
            holder.itemView.tag = user

            nameTextView.text = user.firstName
            surnameTextView.text = user.lastName
            phoneNumberTextView.text = user.phoneNumber.toString()

            if(user.photo.isNotBlank()){
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .error(R.drawable.baseline_account_circle_24)
                    .into(photoImageView)
            }else{
                photoImageView.setImageResource(R.drawable.baseline_account_circle_24)
            }

        }
    }

    fun updateUser(updatedUser: User) {
        val position = users.indexOfFirst { it.id == updatedUser.id }
        if (position != -1) {
            users[position] = updatedUser
            notifyItemChanged(position)
        }
    }

    class UsersViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)
}