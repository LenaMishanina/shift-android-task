package com.task.shiftapp.data.model.user

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
) {
    override fun toString(): String {
        return "${street.number} ${street.name}, $city"
    }
}

data class Street(
    val number: Int,
    val name: String,
)
