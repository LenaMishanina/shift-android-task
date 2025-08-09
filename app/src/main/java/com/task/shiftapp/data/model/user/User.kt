package com.task.shiftapp.data.model.user

data class User (
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val picture: Picture,
)