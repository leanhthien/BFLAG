package com.example.minhquan.bflagclient.api

import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Multipart

const val BFLAG_BASE_URL = "https://glacial-journey-54219.herokuapp.com"

interface BflagApiService {

    @POST("/api/v1/user/sign_up")
    fun getSignUp(@Body body: JsonObject)
            : Observable<SuccessResponse>

    @POST("/api/v1/user/sign_in")
    fun getSignIn(@Body body: JsonObject)
            : Observable<SuccessResponse>

    @POST("/api/v1/user/auth")
    fun getAuth(@Body body: JsonObject)
            : Observable<SuccessResponse>

    @Multipart
    @PUT("/api/v1/user/edit")
    fun getEdit(@Header("token") token: String,
                @Part filePart: MultipartBody.Part,
                @PartMap() mapPart: HashMap<String, RequestBody>)
            : Observable<User>

    @DELETE("/api/v1/user/sign_out")
    fun getSignOut(@Header("token") token: String)
            : Observable<SuccessResponse>

    @GET("/api/v1/user")
    fun getUser(@Header("token") token: String)
            : Observable<User>



}