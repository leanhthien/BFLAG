package com.example.minhquan.bflagclient.utils

import com.google.gson.JsonObject

fun JsonObject.buildSignUpJson(email: String, password: String, username: String, firstName: String, lastName: String): JsonObject {

    addProperty("email", email)
    addProperty("password", password)
    addProperty("username", username)
    addProperty("first_name", firstName)
    addProperty("last_name", lastName)

    return this
}

fun JsonObject.buildSignInJson(email: String, password: String): JsonObject {

    addProperty("email", email)
    addProperty("password", password)

    return this
}

fun JsonObject.buildAuthJson(token: String): JsonObject {

    addProperty("Token", token)

    return this
}


fun JsonObject.buildEditJson(username: String?, firstName: String?, lastName: String?): JsonObject {

    addProperty("username", username)
    addProperty("first_name", firstName)
    addProperty("last_name", lastName)

    return this
}

fun JsonObject.buildResetJson(email: String): JsonObject {

    addProperty("email", email)

    return this
}

fun JsonObject.buildCodeJson(email: String, resetCode: String): JsonObject {
    addProperty("email", email)
    addProperty("reset_code", resetCode)

    return this
}

fun JsonObject.buildResetAuthJson(email: String, resetCode: String, password: String?): JsonObject {

    addProperty("email", email)
    addProperty("reset_code", resetCode)
    addProperty("password", password)

    return this
}
