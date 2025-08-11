package com.task.shiftapp.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.task.shiftapp.utils.readParcelableWithVersionChecking

data class User (
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: DateOfBirth,
    val phone: String,
    val picture: Picture,
): Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeString(gender)
            writeParcelable(name, flags)
            writeParcelable(location, flags)
            writeString(email)
            writeParcelable(dob, flags)
            writeString(phone)
            writeParcelable(picture, flags)
        }
    }

    constructor(dest: Parcel) : this(
        gender = dest.readString().toString(),
        name = dest.readParcelableWithVersionChecking<Name>()!!,
        location = dest.readParcelableWithVersionChecking<Location>()!!,
        email = dest.readString().toString(),
        dob = dest.readParcelableWithVersionChecking<DateOfBirth>()!!,
        phone = dest.readString().toString(),
        picture = dest.readParcelableWithVersionChecking<Picture>()!!,
    )

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(dest: Parcel): User {
            return User(dest)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}
