package com.task.shiftapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.task.shiftapp.R
import com.task.shiftapp.data.model.user.User
import com.task.shiftapp.databinding.UserItemBinding

class UserAdapter(private val clickListener: UserClickListener): ListAdapter<User, UserAdapter.Holder>(Comparator()) {
    class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = UserItemBinding.bind(view)
        fun bind(user: User) = with(binding) {
            tvName.text = user.name.toString()
            tvAddress.text = user.location.toString()
            tvPhone.text = user.phone
            tvEmail.text = user.email
            Picasso.get()
                .load(user.picture.medium)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(ivProfile)
        }
    }

    class Comparator: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = getItem(position)
        holder.apply {
            bind(user)
            itemView.setOnClickListener {
                clickListener.onUserClickListener(user)
            }
        }
    }
}