package com.example.minhquan.bflagclient.api

import com.example.minhquan.bflagclient.model.*
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

    @Multipart
    @PUT("/api/v1/user/edit")
    fun getEdit(@Header("token") token: String,
                @Part filePart: MultipartBody.Part?,
                @PartMap() mapPart: HashMap<String, RequestBody>?)
            : Observable<User>

    @DELETE("/api/v1/user/sign_out")
    fun getSignOut(@Header("token") token: String)
            : Observable<SuccessResponse>

    @GET("/api/v1/user")
    fun getUser(@Header("token") token: String)
            : Observable<User>

    @POST("/api/v1/reset_password")
    fun getReset(@Body body: JsonObject)
            : Observable<SuccessResponse>

    @POST("/api/v1/reset_password/auth")
    fun getResetPassword(@Body body: JsonObject)
            : Observable<SuccessResponse>

    @Multipart
    @POST("/api/v1/images")
    fun getSendImage(@Header("token") token: String,
                     @Part("room_id") roomId: Int,
                     @Part filePart: MultipartBody.Part)
            : Observable<SuccessResponse>

    @GET("/api/v1/rooms/{id}/{offset}")
    fun getHistoryChat(@Header("token") token: String,
                       @Path("id") id: Int,
                       @Path("offset") offset: Int)
            : Observable<HistoryChatResponse>

    @GET("/api/v1/rooms/subscribed")
    fun getSubscribedRooms(@Header("Token") token: String)
            : Observable<ListRoomResponse>

    @GET("/api/v1/online_users")
    fun getOnlineUsers(@Header("Token") token: String)
            : Observable<ListOnlineUsers>

    @POST("/api/v1/rooms")
    fun createRoom(@Header("Token") token: String,
                  @Body body: JsonObject)
            : Observable<Room>

    @GET("/api/v1/rooms/search")
    fun searchRoom(@Header("Token") token: String,
                   @Query("q") query: String )
            : Observable<ListRoomResponse>

    @GET("/api/v1/user/search")
    fun searchUser(@Header("Token") token: String,
                   @Query("q") query: String )
            : Observable<ListOnlineUsers>

    @DELETE("/api/v1/rooms/unsubscribe/{room_id}")
    fun getUnsubcribe(@Header("Token") token: String,
                    @Path("room_id") id: Int)
            : Observable<SuccessResponse>


}