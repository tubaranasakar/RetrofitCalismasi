package com.tuba.retrofitcalismasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.tuba.retrofitcalismasi.adapter.RecyclerViewAdapter
import com.tuba.retrofitcalismasi.databinding.ActivityMainBinding
import com.tuba.retrofitcalismasi.model.CyrpoModel
import com.tuba.retrofitcalismasi.sevice.CyrptoAPI
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cyrptoModels: ArrayList<CyrpoModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val layoutManager : RecyclerView.LayoutManager =  LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        compositeDisposable = CompositeDisposable()
        loadData()
    }
    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(CyrptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<CyrpoModel>>  {
            override fun onFailure(call: Call<List<CyrpoModel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<CyrpoModel>>,
                response: Response<List<CyrpoModel>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        cyrptoModels = ArrayList(it)

                        cyrptoModels?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(it,this@MainActivity)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }


                        /*
                        for (cryptoModel : CyrpoModel in cyrptoModels!!) {
                            println(cryptoModel.currency)
                            println(cryptoModel.price)

                        }

                         */

                    }
                }

            }

        })
    }

    override fun onItemClick(cryptoModel: CyrpoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
}