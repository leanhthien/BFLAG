package com.example.minhquan.bflagclient.profile.editprofile

import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditProfilePresenter(private val view: EditProfileContract.View): EditProfileContract.Presenter {


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

    override fun startEdit(token: String, filePart: MultipartBody.Part?, mapPart: HashMap<String, RequestBody>?) {
        view.showProgress(true)

        disposable  = service.getEdit(token, filePart, mapPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<User>(view) {
                    override fun onSuccess(result: User) {
                        view.onEditSuccess(result)
                    }
                })

    }

}