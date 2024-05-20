package com.tuba.retrofitcalismasi.sevice
import com.tuba.retrofitcalismasi.model.CyrpoModel
import retrofit2.http.GET
import retrofit2.Call

interface CyrptoAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): retrofit2.Call<List<CyrpoModel>>

}