package com.example.minhquan.bflagclient.ambert.capture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.*
import kotlinx.android.synthetic.main.activity_capture.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import io.reactivex.disposables.Disposable
import com.tbruyelle.rxpermissions2.RxPermissions
import com.facebook.common.file.FileUtils
import kotlinx.android.synthetic.main.fragment_signin.*
import okhttp3.RequestBody
import kotlin.collections.HashMap


const val CAMERA_REQUEST_CODE = 1
const val GALLERY_REQUEST_CODE = 2


class CaptureActivity : AppCompatActivity(), CaptureContract.View {

    private var disposable: Disposable? = null
    private lateinit var path: String
    private lateinit var presenter: CaptureContract.Presenter
    private lateinit var token: String

    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        CapturePresenter(this)

        val rxPermissions = RxPermissions(this)
        rxPermissions.setLogging(true)

        btn_camera.setOnClickListener {

            disposable = rxPermissions
                    .request(Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted)
                            takePhotoFromCamera()
                        else
                            Toast.makeText(this,
                                    "Permission denied, can't open Camera!",
                                    Toast.LENGTH_SHORT).show()
                    }
        }


        btn_gallery.setOnClickListener {

            disposable = rxPermissions
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted)
                            choosePhotoFromGallery()
                        else
                            Toast.makeText(this,
                                    "Permission denied, can't open photo Gallery!",
                                    Toast.LENGTH_SHORT).show()
                        }

        }

        token =  SharedPreferenceHelper.getInstance(this).getToken()!!



    }

    /**
     * Function for take photo from camera by making an ACTION_IMAGE_CAPTURE Intent
     */
    private fun takePhotoFromCamera() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)

    }

    /**
     * Function for choose photo from gallery by making an ACTION_PICK Intent
     */
    private fun choosePhotoFromGallery() {

        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)

    }

    /**
     * Function for handle data that return from sent Intent
     * @param requestCode param to determine action type: camera or gallery
     *
     */
    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED)
            return

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                val thumbnail = data!!.extras!!.get("data") as Bitmap
                path = savePhoto(thumbnail)
                img_capture!!.setImageBitmap(thumbnail)
                Toast.makeText(this@CaptureActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
            }
            GALLERY_REQUEST_CODE -> {
                val contentURI = data?.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    path = savePhoto(bitmap)
                    img_capture!!.setImageBitmap(bitmap)
                    Toast.makeText(this@CaptureActivity, "Image Loaded!", Toast.LENGTH_SHORT).show()

                    val filePart = prepareFilePart("profile_image",contentURI!!, this)
                    val mapPart = HashMap<String, RequestBody>()
                            .buildRequestBody("Thien","Le","anhthien", null)
                    presenter.startEdit(token, filePart, mapPart)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@CaptureActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    /**
     * Function for preparing photo and returning the absolute path of photo
     * @return path - The absolute path of photo for handle
     */
    private fun savePhoto(myBitmap: Bitmap): String {

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

    companion object {
        const val IMAGE_DIRECTORY_PATH = "/Bflag"
    }


    override fun onEditSuccess(result: User) {

    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setAction(RETRY) {
                        /*presenter.startEdit(token,,)*/
                    }
                    .show()
        else
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .show()
    }

    override fun setPresenter(presenter: CaptureContract.Presenter) {
        this.presenter = presenter
    }

    override fun onUnknownError(error: String) {
        showError(error)
    }

    override fun onTimeout() {
        showError(TIME_OUT)
    }

    override fun onNetworkError() {
        showError(NETWORK_ERROR)
    }

    override fun isNetworkConnected(): Boolean {
        return ConnectivityUtil.isConnected(this)

    }

}

