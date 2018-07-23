package com.example.minhquan.bflagclient.ambert.signup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.TokenResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.buildEditJson
import com.example.minhquan.bflagclient.utils.buildSignUpJson
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_signup.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import java.util.*
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker




const val GOOGLE_SIGN_IN_CODE = 100
const val EMAIL = "email"

class SignUpActivity : AppCompatActivity(), SignContract.View {


    private lateinit var presenter: SignContract.Presenter

    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var accessTokenTracker : AccessTokenTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        SignPresenter(this)

        //setupView()
        //getSignUpGoogle()
        getSignUpFacebook()
    }

    fun setupView() {

        val body = JsonObject().buildSignUpJson("thien@gmail.com",
                "123456",
                "anhthien",
                "Le Anh",
                "Thien")
        val body_2 = JsonObject().buildEditJson(null, null, null)
        val token = "a423ecd5a4ab0379991b9c79d7535ab0"


        btn_test.setOnClickListener{
            //presenter.startSignUp(body)
            //presenter.startSignIn(body_1)
            //presenter.startEdit(token, body_2)
            //presenter.startSignOut(token)

            /*if (isNetworkConnected())
                Log.d("Network status","Connected")
            else
                Log.d("Network status","No network connection")*/

        }

    }

    /**
     * Function for Google Sign up
     */
    private fun getSignUpGoogle() {


        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        if (GoogleSignIn.getLastSignedInAccount(this) == null) {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            btn_google_sign_in.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE)
            }
        }
        else {
            //TODO: Handle event auto sign in
            Log.d("Google sign in status","Already sign in!")
        }


    }


    /**
     * Function for handle return account to get necessary information
     */
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

            //TODO: Auto sign up or just fill require fields with Google account info
            //presenter.startSignUp(body)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ApiException return", "signInResult:failed code=" + e.statusCode)

        }

    }

    private fun getSignUpFacebook() {

        if ( AccessToken.getCurrentAccessToken() == null) {

            callbackManager = CallbackManager.Factory.create()

            // If using the button in a fragment, add a call: btn_facebook_sign_in.setFragment(this)
            btn_facebook_sign_in.setReadPermissions(Arrays.asList(EMAIL))

            btn_facebook_sign_in.registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            // TODO:
                            Log.i("Sig up with Facebook", "Success")
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
        else {
            //TODO: Handle event auto sign in
            Log.d("Facebook sign in status","Already sign in!")
        }



    }


    /**
     *  Override function handle return data on request : Google or Facebook sign in
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

    public override fun onDestroy() {
        super.onDestroy()
        accessTokenTracker.stopTracking()
    }


    /**
     * Functions on request API return successful
     */
    override fun onSignUpSuccess(result: SuccessResponse) {
        Log.d("Sign up return",result.status)
    }

    override fun onSignInSuccess(result: TokenResponse) {
        Log.d("Sign in return",result.token)
    }

    override fun onEditSuccess(result: User) {
        Log.d("Edit return", result.email)
    }

    override fun onSignOutSuccess(result: SuccessResponse) {
        Log.d("Sign out return",result.status)
    }


    override fun showProgress(isShow: Boolean) {

    }

    /**
     * Functions on request API return successful
     */
    override fun showError(message: String) {

        Log.e("Error return", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: SignContract.Presenter) {
        this.presenter = presenter
    }

    override fun onUnknownError(error: String) {
        showError(error)
    }

    override fun onTimeout() {
        showError("Time out")
    }

    override fun onNetworkError() {
        showError("Network Error")
    }

    override fun isNetworkConnected(): Boolean {
        return ConnectivityUtil.isConnected(this)
    }


}
