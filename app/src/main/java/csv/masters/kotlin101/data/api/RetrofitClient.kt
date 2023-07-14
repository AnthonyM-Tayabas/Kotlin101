package csv.masters.kotlin101.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}