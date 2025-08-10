package com.task.shiftapp.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.task.shiftapp.utils.readParcelableWithVersionChecking

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
): Parcelable {
    override fun toString(): String {
        return "${street.number} ${street.name}, $city"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeParcelable(street, flags)
            writeString(city)
            writeString(state)
            writeString(country)
            writeString(postcode)
        }
    }

    constructor(dest: Parcel): this(
        street = dest.readParcelableWithVersionChecking<Street>()!!,
        city = dest.readString().toString(),
        state = dest.readString().toString(),
        country = dest.readString().toString(),
        postcode = dest.readString().toString(),
    )

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(dest: Parcel): Location {
            return Location(dest)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }

}

data class Street(
    val number: Int,
    val name: String,
): Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeInt(number)
            writeString(name)
        }
    }

    constructor(dest: Parcel): this(
        number = dest.readInt(),
        name = dest.readString().toString(),
    )

    companion object CREATOR : Parcelable.Creator<Street> {
        override fun createFromParcel(dest: Parcel): Street {
            return Street(dest)
        }

        override fun newArray(size: Int): Array<Street?> {
            return arrayOfNulls(size)
        }
    }

}
