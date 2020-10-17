package com.example.contacts

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Спрашиваем разрешение на использование информации о контактах на телефоне

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Array(1){Manifest.permission.READ_CONTACTS}, 111)
        }
        else
            readContact()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readContact()
    }

    private fun readContact() {

        var from = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        ).toTypedArray()

        var to = intArrayOf(android.R.id.text1, android.R.id.text2)

        var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

//        var photo_uri = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            cols, null, null, ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

//        Класс SimpleCursorAdapter позволяет назначать данные курсора, используемые источником данных, для компонентов

        var adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, rs, from, to, 0)

        listView1.adapter = adapter

//        Следит за изменением текста юзером

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    cols, "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?", Array(1){"%$newText%"},
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                adapter.changeCursor(rs)
                return false
            }
        })
    }
}