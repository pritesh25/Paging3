package com.mindorks.example.paging3.data

import com.mindorks.example.paging3.data.response.StackResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("2.3/questions")
    suspend fun getStackListData(
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int,
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "activity",
        @Query("site") site: String = "stackoverflow",
    ): Response<StackResponse>

    companion object {

        var ORDER_TYPE = "desc"
        var SORT_TYPE = "activity"
        const val PAGE_SIZE = 10

        fun getStackApiService(): APIService {

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }

    }
}
