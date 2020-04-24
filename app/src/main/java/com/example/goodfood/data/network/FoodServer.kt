package com.example.goodfood.data.network

import com.example.goodfood.BuildConfig
import com.example.goodfood.model.RandomResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.spoonacular.com/"

interface FoodServer {

    @GET("recipes/random")
    suspend fun getRandom(
        @Query("number") number:Int,
        @Query("tags") tags:String? = null     // can be diets, meal types, cuisines, or intolerances
    ): retrofit2.Response<RandomResponse>

    companion object{
        operator fun invoke():FoodServer{
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val apiInterceptor = object :Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val oldUrl = chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.API_KEY)
                        .build()
                    val newUrl = chain.request()
                        .newBuilder()
                        .url(oldUrl)
                        .build()
                    return chain.proceed(newUrl)
                }
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(apiInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(FoodServer::class.java)
        }
    }
}