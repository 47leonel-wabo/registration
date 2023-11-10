package com.wbt.registrationapp

import java.io.Serializable

data class Profile(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val jobPosition: String?
) : Serializable
