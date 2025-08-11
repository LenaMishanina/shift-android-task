package com.task.shiftapp.data.model.user

import android.os.Parcel
import android.os.Parcelable

data class DateOfBirth(
    val date: String,
    val age: Int,
) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeString(date)
            writeInt(age)
        }
    }

    constructor(dest: Parcel) : this(
        date = dest.readString().toString(),
        age = dest.readInt(),
    )

    companion object CREATOR : Parcelable.Creator<DateOfBirth> {
        override fun createFromParcel(dest: Parcel): DateOfBirth {
            return DateOfBirth(dest)
        }

        override fun newArray(size: Int): Array<DateOfBirth?> {
            return arrayOfNulls(size)
        }
    }

}
