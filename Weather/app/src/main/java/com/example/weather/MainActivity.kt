package com.example.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


@Suppress("PLUGIN_WARNING", "DEPRECATION", "UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()


        var isLight = prefs.getBoolean("ISLIGHT", true)


        if (isLight){
            theme.applyStyle(R.style.Light, true)
        } else {
            theme.applyStyle(R.style.Dark, true)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_main.layoutManager = layoutManager


        imageView3.setOnClickListener {
            isLight = !isLight
            editor.putBoolean("ISLIGHT", isLight).apply()
            restartApplication()
        }

        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=55.75&lon=37.62&units=metric&exclude=current,minutely,daily,alerts&appid=b6e078c82159d758a18b5ae1719b8f3b"
        if (haveInternet(this)){
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    editor.putString("body", body).apply()
                }

                override fun onFailure(call: Call, e: IOException) {
                    println("Fail to execute request")
                }


            })

        }


        val body = prefs.getString("body", "")

        if (body != "") {
            val gson = GsonBuilder().create()
            val homeFeed = gson.fromJson(body, HomeFeed::class.java)

            textView17.text = homeFeed.hourly[0].temp + " °С"
            textView24.text = homeFeed.hourly[0].feels_like + " °С"
            textView20.text = homeFeed.hourly[0].humidity + "%"
            textView22.text = homeFeed.hourly[0].wind_speed + "m/s"
            textView18.text = homeFeed.hourly[0].weather[0].main


            runOnUiThread {
                recyclerView_main.adapter = MainAdapter(homeFeed)
            }
        }
    }

    private fun haveInternet(ctx: MainActivity): Boolean {
        val info = (ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        var connection = false
        if (info != null) {
                connection = info.isConnected
            }
        return connection
    }


    private fun restartApplication() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    class HomeFeed(val hourly: List<Hours>)
    class Hours(val dt: Int, val temp: String, val weather: List<Weather>, val feels_like: String, val humidity: String, val wind_speed: String)
    class Weather(val main: String)

}



