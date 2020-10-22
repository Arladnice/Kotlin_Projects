package com.example.contacts

import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Array(1) {android.Manifest.permission.READ_CONTACTS},111)
        }
        else
            readContact()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            readContact()
    }
    private fun readContact() {
        var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, cols,
                    null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
    }
}

private fun Any.query(
    contentUri: Uri?,
    cols: Array<String>,
    nothing: Nothing?,
    displayName: String
): Cursor? {
    TODO("Not yet implemented")
}
