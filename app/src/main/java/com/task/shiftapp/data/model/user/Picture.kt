package com.task.shiftapp.data.model.user

import android.os.Parcel
import android.os.Parcelable

data class Picture(
    val medium: String,
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(medium)
    }

    constructor(dest: Parcel) : this(
        medium = dest.readString().toString(),
    )

    companion object CREATOR : Parcelable.Creator<Picture> {
        override fun createFromParcel(dest: Parcel): Picture {
            return Picture(dest)
        }

        override fun newArray(size: Int): Array<Picture?> {
            return arrayOfNulls(size)
        }
    }

}
