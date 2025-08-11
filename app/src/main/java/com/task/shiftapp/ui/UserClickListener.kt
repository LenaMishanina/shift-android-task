package com.task.shiftapp.ui

import com.task.shiftapp.data.model.user.User

interface UserClickListener {
    fun onUserClickListener(user: User)
}