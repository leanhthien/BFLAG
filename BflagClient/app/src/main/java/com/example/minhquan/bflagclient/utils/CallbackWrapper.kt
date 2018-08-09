/**
 * A custom CallbackWrapper to handle response form server that emphasizes on error exceptions
 */

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

    private val weakReference: WeakReference<BaseView<*>> = WeakReference(view)

    protected abstract fun onSuccess(result: T)

    /**
     * Handle server return successfully response
     */
    override fun onNext(result: T) {
        // Return StatusCodes of different cases from API and handle.
        onSuccess(result)
    }

    /**
     * Handle when server return error
     */
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

    override fun onComplete() {}

    /**
     * Parse the error message that server return
     * @param responseBody Error body with message
     */
    private fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("error")
        } catch (e: Exception) {
            e.message!!
        }
    }
}