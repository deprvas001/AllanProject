package com.development.allanproject.data.network

import com.development.allanproject.constant.ApiConstant
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.model.signupModel.SignResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ServiceApi {

//    @FormUrlEncoded
//    @POST("update-user")
//    suspend fun userLogin(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Response<SignResponse>

    @POST("update-user")
    suspend fun userLogin(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PersonalDetailPost
    ): Response<SignResponse>


    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ServiceApi{
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceApi::class.java)
        }
    }
}