package com.example.minhquan.bflagclient.sign.signin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.utils.*
import com.facebook.AccessTokenTracker
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_signin.*
import com.example.minhquan.bflagclient.utils.PreferenceHelper.set
import com.example.minhquan.bflagclient.utils.PreferenceUtil
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.support.v4.content.res.TypedArrayUtils.getResourceId
import android.content.res.TypedArray
import java.util.*

const val GOOGLE_SIGN_IN_CODE = 100
const val EMAIL = "email"
const val EMPTY_ERROR = "The value cannot be empty!"


class SignInFragment : Fragment(), SignInContract.View {

    private lateinit var presenter: SignInContract.Presenter
    private lateinit var body: JsonObject
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var accessTokenTracker : AccessTokenTracker

    private var count: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false) as ViewGroup
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SignInPresenter(this)

        setupView()

        tvSignUp.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.viewPager)?.currentItem = 1
        }

        getStateCheckBox()


//        val attrs = intArrayOf(R.attr.selectableItemBackground)
//        val typedArray = activity!!.obtainStyledAttributes(attrs)
//        val backgroundResource = typedArray.getResourceId(0, 0)
//        button2.setBackgroundResource(backgroundResource)

    }

    private fun getStateCheckBox() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity!!.baseContext)
        val checked = prefs.getBoolean("checked", false)
        checkBox.isChecked = checked

    }

    private fun setupView() {

        btnSignIn.setOnClickListener {

            var check = true
            if (TextUtils.isEmpty(edtEmail.text.toString())){
                edtEmail.error = EMPTY_ERROR
                check = false
            }
            if (TextUtils.isEmpty(edtPassword.text.toString())){
                edtPassword.error = EMPTY_ERROR
                check = false
            }
            if(check){

                body = JsonObject().buildSignInJson(edtEmail.text.toString(), edtPassword.text.toString())
                // save state of checkbox
                val prefs = PreferenceManager.getDefaultSharedPreferences(activity!!.baseContext)
                prefs.edit().putBoolean("checked", checkBox.isChecked).apply()

                val body = JsonObject().buildSignInJson(edtEmail.text.toString(), edtPassword.text.toString())
                presenter.startSignIn(body)
            }

        }

        getSignInGoogle()

        getSignInFacebook()

    }

    private fun getSignInGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(context!!, gso)

        img_google.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE)
        }
    }

    private fun getSignInFacebook() {

        callbackManager = CallbackManager.Factory.create()

        btn_facebook_sign_in.setReadPermissions(Arrays.asList(EMAIL))
        btn_facebook_sign_in.fragment = this
        btn_facebook_sign_in.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        // TODO:
                        Log.i("Sig up with Facebook", "Success")
                        Log.d("Token facebook", loginResult.accessToken.userId)
                        SharedPreferenceHelper.getInstance(context!!).setTokenFacebook(loginResult.accessToken.userId)
                    }

                    override fun onCancel() {
                        // TODO:
                        Log.i("Sig up with Facebook","Canceled")

                    }

                    override fun onError(exception: FacebookException) {
                        // TODO:
                        Log.e("Sig up with Facebook", exception.localizedMessage)
                    }
                })
    }

    override fun onSignInSuccess(result: SuccessResponse) {
        Toast.makeText(context, "Sign in success!!", Toast.LENGTH_SHORT).show()
        Log.d("Sign in return", result.token)

        SharedPreferenceHelper.getInstance(context!!).setToken(result.token)

        startActivity(Intent(context,HomeActivity::class.java))

    }

    /**
     *  Override function handle return data on request : Google or Facebook sign in
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient, FacebookCallback
        when (requestCode) {
            GOOGLE_SIGN_IN_CODE -> {
                // The Task returned from this call is always completed, no need to attach a listener.
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
            else -> callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            val body = JsonObject().buildSignUpJson(account.email!!,
                    "123456",
                    account.displayName!!,
                    account.givenName!!,
                    account.familyName!!)
            Log.d("Google Account", body.toString())
            Log.d("Token google", account.idToken)
            SharedPreferenceHelper.getInstance(context!!).setTokenFacebook(account.idToken)

            //TODO: Auto sign up or just fill require fields with Google account info
            //presenter.startSignUp(body)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ApiException return", "signInResult:failed code=" + e.statusCode)

        }

    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun setPresenter(presenter: SignInContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        count++
        if (count < MAX_RETRY)
            Snackbar.make(activity!!.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setAction(RETRY) {
                        presenter.startSignIn(body)
                    }
                    .show()
        else
            Snackbar.make(activity!!.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .show()
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
        return ConnectivityUtil.isConnected(this.activity!!)
    }
}