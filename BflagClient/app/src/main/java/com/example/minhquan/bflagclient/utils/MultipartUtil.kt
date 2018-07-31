package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import java.io.File


fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part {
    val file = File(FileUtil().getPath(context,fileUri))

    // create RequestBody instance from file
    val requestFile =
    RequestBody.create(
            MediaType.parse("image/*"),
            file
    )

    val requestFile_ =
    RequestBody.create(
            MediaType.parse(context.contentResolver.getType(fileUri)),
            file
    )

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}



fun HashMap<String, RequestBody>.buildRequestBody(firstName: String?, lastName: String?, username: String?) : HashMap<String, RequestBody> {

    put("first_name", createPartFromString(firstName))
    put("last_name", createPartFromString(lastName))
    put("username", createPartFromString(username))

    return this
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
private fun createPartFromString(partString: String?): RequestBody {
    return RequestBody.create(MultipartBody.FORM, partString)
}