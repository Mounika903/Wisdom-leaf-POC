package `in`.co.wisdom_leaf_poc.remote

import `in`.co.wisdom_leaf_poc.model.Author
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("list?page=1&limit=20")
    suspend fun getBooks(): Response<List<Author>>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder().baseUrl("https://picsum.photos/v2/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }

            return retrofitService!!
        }
    }
}

