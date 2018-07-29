package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.minhquan.bflagclient.chat.IMAGE_DIRECTORY_PATH
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import android.provider.MediaStore

/**
 * Function for preparing photo and returning the absolute path of photo
 * @return path - The absolute path of photo for handle
 */
fun Context.savePhoto(myBitmap: Bitmap) : String {
    val bytes = ByteArrayOutputStream()
    myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
    val photoDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY_PATH)

    // Have the object build the directory structure, if needed.
    Log.d("Directory path", photoDirectory.toString())

    if (!photoDirectory.exists()) photoDirectory.mkdirs()

    try {
        Log.d("Directory path after", photoDirectory.toString())
        val f = File(photoDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg"))
        f.createNewFile()
        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        MediaScannerConnection.scanFile(this,
                                        arrayOf(f.path),
                                        arrayOf("image/jpeg"),
                                        null)
        fo.close()
        Log.d("TAG", "File Saved::--->" + f.absolutePath)

        return f.absolutePath
    }
    catch (e: IOException) {
        e.printStackTrace()
    }

    return ""
}

/**
 * Function for getting full path of chosen photo from gallery
 */
fun Context.getPath(uri: Uri) : String {

    val file = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = contentResolver.query(uri,
            file, null, null, null)

    cursor.moveToFirst()

    val columnIndex = cursor.getColumnIndex(file[0])
    val imageDecode = cursor.getString(columnIndex)
    cursor.close()
    return imageDecode
}