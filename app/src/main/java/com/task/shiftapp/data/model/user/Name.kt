package com.task.shiftapp.data.model.user

import android.os.Parcel
import android.os.Parcelable

data class Name(
    val title: String,
    val first: String,
    val last: String,
) : Parcelable {
    override fun toString(): String {
        return "$title $first $last"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, p1: Int) {
        dest.apply {
            writeString(title)
            writeString(first)
            writeString(last)
        }
    }

    constructor(dest: Parcel) : this(
        title = dest.readString().toString(),
        first = dest.readString().toString(),
        last = dest.readString().toString(),
    )

    companion object CREATOR : Parcelable.Creator<Name> {
        override fun createFromParcel(dest: Parcel): Name {
            return Name(dest)
        }

        override fun newArray(size: Int): Array<Name?> {
            return arrayOfNulls(size)
        }
    }

}
