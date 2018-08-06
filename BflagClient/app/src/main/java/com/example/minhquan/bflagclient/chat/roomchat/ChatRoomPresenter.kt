package com.example.minhquan.bflagclient.chat.roomchat

import android.util.Log
import com.example.minhquan.bflagclient.model.Chat
import com.example.minhquan.bflagclient.model.HistoryChatResponse
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.NETWORK_ERROR
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import com.hosopy.actioncable.Subscription
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody


const val CONTENT = "content"
const val SERVER_ERROR = "Can't connect to server"

class ChatPresenter (val view: ChatRoomContract.View): ChatRoomContract.Presenter {



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

    override fun startGetHistoryChat(token: String, id: Int, offset: Int) {
        disposable  = service.getHistoryChat(token, id, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<HistoryChatResponse>(view) {
                    override fun onSuccess(result: HistoryChatResponse) {
                        view.onGetHistoryChatSuccess(result)
                    }
                })
    }

    override fun startSendLogChat(actionType: String, chat: Chat, subscription: Subscription) {

        if (!view.isNetworkConnected())
            view.showError(NETWORK_ERROR)
        else if (subscription.onConnected == null)
            view.showError(SERVER_ERROR)
        else
            subscription.perform(actionType, mapOf(CONTENT to chat.message!!.content))

    }

    override fun startSendImageChat(token: String, filePart: MultipartBody.Part, roomId: Int) {

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
