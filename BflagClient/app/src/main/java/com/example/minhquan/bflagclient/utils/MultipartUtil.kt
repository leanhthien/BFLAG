package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import java.io.File


fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part {
    val file = File(FileUtil().getPath(context, fileUri))

    // create RequestBody instance from file
    val requestFile =
            RequestBody.create(
                    MediaType.parse("image/*"),
                    file
            )

    /*val requestFile_ =
    RequestBody.create(
            MediaType.parse(context.contentResolver.getType(fileUri)),
            file
    )*/

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}


fun HashMap<String, RequestBody>.buildRequestBody(firstName: String?, lastName: String?, username: String?, password: String?, gender: String?, birthday: String?): HashMap<String, RequestBody> {

    if (firstName != null)
        put("first_name", createPartFromString(firstName))
    if (lastName != null)
        put("last_name", createPartFromString(lastName))
    if (username != null)
        put("username", createPartFromString(username))
    if (password != null)
        put("password", createPartFromString(password))
    if (gender != null)
        put("gender", createPartFromString(gender))
    if (birthday != null)
        put("birth", createPartFromString(birthday))

    return this
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
private fun createPartFromString(partString: String?): RequestBody {
    return RequestBody.create(MultipartBody.FORM, partString)
}


