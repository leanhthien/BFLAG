package com.example.minhquan.bflagclient.profile.editprofile

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R

import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_edit_profile.*
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import com.example.minhquan.bflagclient.ambert.capture.*
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_capture.*
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.text.SimpleDateFormat


class EditProfileActivity : AppCompatActivity(), EditProfileContract.View{

    private val myCalendar = Calendar.getInstance()
    private var disposable: Disposable? = null
    private lateinit var path: String
    private lateinit var presenter: EditProfileContract.Presenter
    private lateinit var token: String

    private var count: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val animation = AnimationUtils.loadAnimation(this, R.anim.bouncing)
        constraint.startAnimation(animation)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        tvCancel.setOnClickListener {
            onBackPressed()
        }

        setUpBirthday()


        setUpProfilePicture()

    }

    private fun setUpBirthday() {


        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        edtBirthday.setOnClickListener {
            DatePickerDialog(this@EditProfileActivity, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        edtBirthday.setText(sdf.format(myCalendar.time))
    }


    private fun setUpProfilePicture() {
        EditProfilePresenter(this)

        val rxPermissions = RxPermissions(this)
        rxPermissions.setLogging(true)

        imgProfile.setOnClickListener {

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
//            CAMERA_REQUEST_CODE -> {
//                val thumbnail = data!!.extras!!.get("data") as Bitmap
//                path = savePhoto(thumbnail)
//                img_capture!!.setImageBitmap(thumbnail)
//                Toast.makeText(this@EditProfileActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
//            }
            GALLERY_REQUEST_CODE -> {
                val contentURI = data?.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    path = savePhoto(bitmap)
                    imgProfile!!.setImageBitmap(bitmap)
                    Toast.makeText(this@EditProfileActivity, "Image Loaded!", Toast.LENGTH_SHORT).show()

                    val filePart = prepareFilePart("profile_image",contentURI!!, this)
                    val mapPart = HashMap<String, RequestBody>()
                            .buildRequestBody("Quan","Nguyen","minhquan")
                    presenter.startEdit(token, filePart, mapPart)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@EditProfileActivity, "Failed!", Toast.LENGTH_SHORT).show()
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
                (Environment.getExternalStorageDirectory()).toString() + CaptureActivity.IMAGE_DIRECTORY_PATH)

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

    override fun setPresenter(presenter: EditProfileContract.Presenter) {
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

    companion object {
        const val IMAGE_DIRECTORY_PATH = "/Bflag"
    }

}