package com.example.minhquan.bflagclient.utils

import com.google.gson.JsonObject
import java.util.*

/**
 * Create a sign up JsonObject
 */
fun JsonObject.buildSignUpJson(email: String, password: String, username: String, firstName: String, lastName: String): JsonObject {

    addProperty(EMAIL,email)
    addProperty(PASSWORD, password)
    addProperty(USERNAME, username)
    addProperty(FIRST_NAME, firstName)
    addProperty(LAST_NAME, lastName)

    return this
}

/**
 * Create a sign in JsonObject
 */
fun JsonObject.buildSignInJson(email: String, password: String): JsonObject {

    addProperty(EMAIL,email)
    addProperty(PASSWORD, password)

    return this
}

/**
 * Create a reset password JsonObject
 */
fun JsonObject.buildResetJson(email: String): JsonObject {

    addProperty(EMAIL, email)

    return this
}

/**
 * Create a reset auth password JsonObject
 */
fun JsonObject.buildResetAuthJson(email: String, resetCode: String, password: String?) : JsonObject {

    addProperty(EMAIL, email)
    addProperty(RESET_CODE, resetCode)
    addProperty(PASSWORD, password)

    return this
}

/**
 * Create a create room JsonObject
 */
fun JsonObject.buildCreateRoom(name: String): JsonObject {

    addProperty(NAME, name)
    return this
}

private const val EMAIL = "email"
private const val USERNAME = "username"
private const val FIRST_NAME = "first_name"
private const val LAST_NAME = "last_name"
private const val PASSWORD = "password"
private const val RESET_CODE = "reset_code"
private const val NAME = "name"