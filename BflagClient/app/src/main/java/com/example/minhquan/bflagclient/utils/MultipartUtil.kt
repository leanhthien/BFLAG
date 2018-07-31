package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.net.Uri
import com.facebook.common.file.FileUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import java.io.File



/*fun prepareFilePart(partName: String , fileUri: Uri): MultipartBody.Part {
    val file = FileUtils.getFile(this, fileUri)

    // create RequestBody instance from file
    val requestFile =
    RequestBody.create(
            MediaType.parse(contentResolver.getType(fileUri)),
            file
    )

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, file.getName(), requestFile)
}*/

/**
 * Convert Uri into File, if possible.
 *
 * @return file A local file that the Uri was pointing to, or null if the
 * Uri is unsupported or pointed to a remote resource.
 * @see .getPath
 * @author paulburke
 */
fun getFile(context: Context, uri: Uri?): File? {
    /*if (uri != null) {
        val path = getPath(context, uri)
        if (path != null && isLocal(path)) {
            return File(path!!)
        }
    }*/
    return null
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