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
    val coordinates: Coordinate,
): Parcelable {
    override fun toString(): String {
        return "$street, $city"
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
            writeParcelable(coordinates, flags)
        }
    }

    constructor(dest: Parcel): this(
        street = dest.readParcelableWithVersionChecking<Street>()!!,
        city = dest.readString().toString(),
        state = dest.readString().toString(),
        country = dest.readString().toString(),
        postcode = dest.readString().toString(),
        coordinates = dest.readParcelableWithVersionChecking<Coordinate>()!!,
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
    override fun toString(): String {
        return "$number $name St"
    }

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

data class Coordinate(
    val latitude: String,
    val longitude: String,
): Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeString(latitude)
            writeString(longitude)
        }
    }

    constructor(dest: Parcel): this(
        latitude = dest.readString().toString(),
        longitude = dest.readString().toString(),
    )

    companion object CREATOR : Parcelable.Creator<Coordinate> {
        override fun createFromParcel(dest: Parcel): Coordinate {
            return Coordinate(dest)
        }

        override fun newArray(size: Int): Array<Coordinate?> {
            return arrayOfNulls(size)
        }
    }

}
