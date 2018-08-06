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
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.resetpassword.ResetPasswordActivity
import com.example.minhquan.bflagclient.sign.SignActivity
import com.facebook.login.LoginManager
import java.util.*

const val GOOGLE_SIGN_IN_CODE = 100
const val PUBLIC_PROFILE = "public_profile"
const val USER_FRIENDS = "user_friends"
const val EMPTY_ERROR = "The value cannot be empty!"


class SignInFragment : Fragment(), SignInContract.View {

    private lateinit var presenter: SignInContract.Presenter
    private lateinit var body: JsonObject
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    private var count: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false) as ViewGroup
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SignInPresenter(this)

        setupView()

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
                presenter.startSignIn(body)
            }

        }

        tvSignUp.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.viewPager)?.currentItem = 1
        }

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this@SignInFragment.activity,ResetPasswordActivity::class.java))
            this@SignInFragment.activity!!.finish()
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

        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        // TODO:
                        Log.i("Sig up with Facebook", "Success")
                        Log.d("Token facebook", loginResult.accessToken.token)
                        SharedPreferenceHelper.getInstance(context!!).setTokenFacebook(loginResult.accessToken.token)
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

        img_facebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this,
                    Arrays.asList(PUBLIC_PROFILE, USER_FRIENDS))
        }

    }

    override fun onSignInSuccess(result: SuccessResponse) {

        Toast.makeText(context, "Sign in success!!", Toast.LENGTH_SHORT).show()
        Log.d("Sign in return", result.token)

        showProgress(false)

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
            else ->  {
                callbackManager.onActivityResult(requestCode, resultCode, data)}
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.

            SharedPreferenceHelper.getInstance(context!!).setTokenGoogle(account.idToken)

            val username = account.email!!.split('@')[0]
            val user = User(null,
                            account.email,
                            null,
                            username,
                            account.givenName,
                            account.familyName,
                            null,
                            null,
                            null)

            val signActivity= activity as SignActivity
            signActivity.getAutoSignup(user)
            activity?.findViewById<ViewPager>(R.id.viewPager)?.currentItem = 1


        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ApiException return", "signInResult:failed code=" + e.statusCode)

        }

    }

    override fun onStop() {
        super.onStop()
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }

    override fun showProgress(isShow: Boolean) {

        when (isShow) {
            true -> {
                loader_sign_in.visibility = View.VISIBLE
                loader_sign_in.playAnimation()
            }
            false -> {
                loader_sign_in.visibility = View.GONE
                loader_sign_in.pauseAnimation()
            }
        }
    }

    override fun setPresenter(presenter: SignInContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        showProgress(false)

        val error = if (message == TIME_OUT || message == NETWORK_ERROR) message else UNKNOWN_ERROR
        count++
        if (count < MAX_RETRY)
            Snackbar.make(activity!!.findViewById(android.R.id.content), error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(RETRY) {
                        presenter.startSignIn(body)
                    }
                    .show()
        else
            Snackbar.make(activity!!.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
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