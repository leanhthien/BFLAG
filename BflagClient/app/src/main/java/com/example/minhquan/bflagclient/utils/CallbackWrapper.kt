package com.example.minhquan.bflagclient.utils

import org.json.JSONObject
import okhttp3.ResponseBody
import com.example.minhquan.bflagclient.base.BaseView
import io.reactivex.observers.DisposableObserver
import com.example.minhquan.bflagclient.base.BaseResponse
import retrofit2.HttpException
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException


abstract class CallbackWrapper<T : BaseResponse>(view: BaseView<*>) : DisposableObserver<T>() {
    //BaseView is just a reference of a View in MVP
    private val weakReference: WeakReference<BaseView<*>> = WeakReference(view)

    protected abstract fun onSuccess(result: T)

    override fun onNext(result: T) {
        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every Response
        onSuccess(result)
    }

    override fun onError(e: Throwable) {
        val view = weakReference.get()
        when (e) {
            is HttpException -> {
                val responseBody = e.response().errorBody()
                view?.onUnknownError(getErrorMessage(responseBody!!))
            }
            is SocketTimeoutException -> view?.onTimeout()
            is IOException -> view?.onNetworkError()
            else -> view?.onUnknownError(e.message!!)
        }
    }

    override fun onComplete() {

    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("error")
        } catch (e: Exception) {
            e.message!!
        }

    }
}