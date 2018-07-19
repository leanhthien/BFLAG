package com.example.minhquan.bflagclient.utils

import com.google.gson.JsonObject

fun buildSignUpJson(email: String, password: String, username: String, firstName: String, lastName: String) : JsonObject {

    val body = JsonObject()
    body.addProperty("email",email)
    body.addProperty("password", password)
    body.addProperty("username", username)
    body.addProperty("first_name", firstName)
    body.addProperty("last_name", lastName)

    return body
}

fun buildSignInJson(email: String, password: String) : JsonObject {

    val body = JsonObject()
    body.addProperty("email",email)
    body.addProperty("password", password)

    return body
}

fun buildEditJson(username: String?, firstName: String?, lastName: String?) : JsonObject {

    val body = JsonObject()
    body.addProperty("username", username)
    body.addProperty("first_name", firstName)
    body.addProperty("last_name", lastName)

    return body
}