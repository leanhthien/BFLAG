package com.example.minhquan.bflagclient.utils

import com.google.gson.JsonObject

fun JsonObject.buildSignUpJson(email: String, password: String, username: String, firstName: String, lastName: String): JsonObject {

    addProperty(EMAIL,email)
    addProperty(PASSWORD, password)
    addProperty(USERNAME, username)
    addProperty(FIRST_NAME, firstName)
    addProperty(LAST_NAME, lastName)

    return this
}

fun JsonObject.buildSignInJson(email: String, password: String): JsonObject {

    addProperty(EMAIL,email)
    addProperty(PASSWORD, password)

    return this
}

fun JsonObject.buildAuthJson(token: String) : JsonObject {

    addProperty(TOKEN, token)

    return this
}


fun JsonObject.buildEditJson(username: String?, firstName: String?, lastName: String?, password: String?) : JsonObject {

    addProperty(USERNAME, username)
    addProperty(FIRST_NAME, firstName)
    addProperty(LAST_NAME, lastName)
    addProperty(PASSWORD, password)

    return this
}

fun JsonObject.buildResetJson(email: String): JsonObject {

    addProperty(EMAIL, email)

    return this
}

fun JsonObject.buildResetAuthJson(email: String, resetCode: String, password: String?) : JsonObject {

    addProperty(EMAIL, email)
    addProperty(RESET_CODE, resetCode)
    addProperty(PASSWORD, password)

    return this
}

const val EMAIL = "email"
const val USERNAME = "username"
const val FIRST_NAME = "first_name"
const val LAST_NAME = "last_name"
const val PASSWORD = "password"
const val RESET_CODE = "reset_code"