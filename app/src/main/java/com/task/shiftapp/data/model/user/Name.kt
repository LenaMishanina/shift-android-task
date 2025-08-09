package com.task.shiftapp.data.model.user

data class Name(
    val title: String,
    val first: String,
    val last: String,
) {
    override fun toString(): String {
        return "$title $first $last"
    }
}
