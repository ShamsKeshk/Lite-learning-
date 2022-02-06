package com.example.liteeducation.model

import android.os.Parcel
import android.os.Parcelable

class MaterialDownloadRequest(private val url: String?,private val id: Int) : Parcelable{

    private constructor(parcel: Parcel) : this(parcel.readString(), parcel.readInt())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel?, flags: Int) {
        out?.writeString(url)
        out?.writeInt(id)
    }

    companion object CREATOR : Parcelable.Creator<MaterialDownloadRequest> {
        override fun createFromParcel(parcel: Parcel): MaterialDownloadRequest {
            return MaterialDownloadRequest(parcel)
        }

        override fun newArray(size: Int): Array<MaterialDownloadRequest?> {
            return arrayOfNulls(size)
        }
    }
}