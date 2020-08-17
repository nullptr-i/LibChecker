package com.absinthe.libchecker.bean

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable

const val ERROR = -1
const val ARMV8 = 0
const val ARMV7 = 1
const val ARMV5 = 2
const val NO_LIBS = 3

const val ARMV8_STRING = "arm64-v8a"
const val ARMV7_STRING = "armeabi-v7a"
const val ARMV5_STRING = "armeabi"

class AppItem() : Parcelable {
    var icon: Drawable = ColorDrawable(Color.TRANSPARENT)
    var appName: String = ""
    var packageName: String = ""
    var versionName: String = ""
    var abi: Int = NO_LIBS
    var isSystem: Boolean = false
    var isKotlinUsed: Boolean = false
    var updateTime:Long = 0

    constructor(parcel: Parcel) : this() {
        appName = parcel.readString().toString()
        packageName = parcel.readString().toString()
        versionName = parcel.readString().toString()
        abi = parcel.readInt()
        isSystem = parcel.readByte() != 0.toByte()
        isKotlinUsed = parcel.readByte() != 0.toByte()
        updateTime = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(appName)
        parcel.writeString(packageName)
        parcel.writeString(versionName)
        parcel.writeInt(abi)
        parcel.writeByte(if (isSystem) 1 else 0)
        parcel.writeByte(if (isKotlinUsed) 1 else 0)
        parcel.writeLong(updateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppItem> {
        override fun createFromParcel(parcel: Parcel): AppItem {
            return AppItem(parcel)
        }

        override fun newArray(size: Int): Array<AppItem?> {
            return arrayOfNulls(size)
        }
    }
}