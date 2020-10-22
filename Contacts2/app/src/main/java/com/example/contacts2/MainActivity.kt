package com.example.contacts2

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var namesList = mutableListOf<String>(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
    private var numbersList = mutableListOf<String>(ContactsContract.CommonDataKinds.Phone.NUMBER)
    private var imagesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Array(1){Manifest.permission.READ_CONTACTS}, 111)
        }
        else
            readContact()


        postToList()

        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(namesList, numbersList, imagesList)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readContact()
    }

    private fun readContact() {

    }

    private fun addToList(name: String, number: String, image: Int) {
        namesList.add(name)
        numbersList.add(number)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i in 1..25){
            addToList("Name $i", "Number $i", R.mipmap.ic_launcher_round)
        }
    }
}