package com.example.weather

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hour_weather.view.*

class MainAdapter(private val homeFeed: MainActivity.HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {


    //number of items
    override fun getItemCount(): Int {
        return homeFeed.hourly.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.hour_weather, parent, false)
        return CustomViewHolder(cellForRow)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.textView_time?.text = java.time.format.DateTimeFormatter.ISO_INSTANT
            .format(java.time.Instant.ofEpochSecond(homeFeed.hourly[position].dt.toLong())).substring(11, 16)
        holder.view.temperature_view?.text = homeFeed.hourly[position].temp
        holder.view.imageView_weather.setImageResource(R.mipmap.sunny)
        when (homeFeed.hourly[position].weather[0].main) {
            "Clouds" -> {
                holder.view.imageView_weather.setImageResource(R.mipmap.cloudsss)
            }
            "Clear" -> {
                holder.view.imageView_weather.setImageResource(R.mipmap.bravo)
            }
            "Sunny" -> {
                holder.view.imageView_weather.setImageResource(R.mipmap.sunny)
            }
        }
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}