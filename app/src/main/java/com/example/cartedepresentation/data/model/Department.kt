package com.example.cartedepresentation.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize // 👈 Import essentiel pour @Parcelize

@Parcelize
data class Department(
    val id: String = "",
    val nom: String = "",
    val directeur: String = "" // clé étrangère vers un user (chef_departement)
) : Parcelable
