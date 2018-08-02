package com.example.minhquan.bflagclient.chat

import android.os.Handler
import android.util.Log
import com.example.minhquan.bflagclient.model.Chat
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import java.net.URI
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

const val BASE_SERVER_URI = "ws://glacial-journey-54219.herokuapp.com/cable"
const val TOKEN = "Token"
const val ROOM_CHANNEL = "RoomChannel"
const val ROOM_ID = "room_id"
const val CONTENT = "content"
const val MAX_ATTEMPT = 10

class ChatPresenter (val view: ChatContract.View): ChatContract.Presenter {


    //Instance of interface created for Retrofit API calls
    private val service by lazy {
        //Initializing Retrofit stuff
        RetrofitUtil.builderBflagService()
    }

    //Rx Java object that tracks fetching activity
    private var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun startSendLogChat(actionType: String, localChat: MutableList<Chat>, subscription: Subscription) {

        if (localChat.size > 0 && view.isNetworkConnected() && subscription.onConnected != null) {
            for (i in 0 until localChat.size) {

                subscription.perform(actionType,
                        mapOf(CONTENT to localChat[i].message!!.content))
            }
            localChat.clear()
        }
        else {
            Log.e("Send message","There something went wrong")
        }
    }

    override fun startSendImageChat(token: String, filePart: MultipartBody.Part, roomId: Int) {
        view.showProgress(true)

        disposable  = service.getSendImage(token, roomId, filePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<SuccessResponse>(view) {
                    override fun onSuccess(result: SuccessResponse) {
                        view.onSendImageChatSuccess(result)
                    }
                })
    }

}
