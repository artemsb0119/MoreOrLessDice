package com.example.moreorlessdice

import com.google.gson.annotations.SerializedName

data class Rule(
    @SerializedName("name") val name: String,
    @SerializedName("text") val text: String
)
