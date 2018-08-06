package com.example.minhquan.bflagclient.utils

import com.example.minhquan.bflagclient.api.BFLAG_BASE_URL
import com.example.minhquan.bflagclient.api.BflagApiService
import com.example.minhquan.bflagclient.chat.roomchat.DATE_TIME_FORMAT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitUtil {

    companion object {

        /**
         * Builder for Bflag API Service
         * @return A BflagApiService instance with Retrofit and RxJava
         */
        fun builderBflagService(): BflagApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gsonDate()))
                    .baseUrl(BFLAG_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client())
                    .build()
            return retrofit.create(BflagApiService::class.java)
        }

        /**
         * Builder for set up Date Format
         */
        private fun gsonDate(): Gson {

            return GsonBuilder()
                    .setDateFormat(DATE_TIME_FORMAT)
                    .setDateFormat(DATE_FORMAT)
                    .create()
        }


        /**
         * Builder for set up a OkHttpClient
         * @return A OkHttpClient
         */
        private fun client(): OkHttpClient {

            return OkHttpClient.Builder()
                    .addNetworkInterceptor { chain ->
                        var request = chain.request()
                        val url = request.url()
                                .newBuilder()
                                .build()
                        request = request.newBuilder()
                                .url(url)
                                .build()
                        chain.proceed(request)
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
        }
    }

}