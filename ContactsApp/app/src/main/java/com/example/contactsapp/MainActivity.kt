package com.example.contactsapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_child.view.*
import kotlinx.android.synthetic.main.recycler_view_item.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contact_list.layoutManager = LinearLayoutManager(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { android.Manifest.permission.READ_CONTACTS },
                111
            )
        } else {
            readContact()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            readContact()
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        readContact()
    }
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                readContact()
            else
                btn_read_contact.setOnClickListener{
                    Toast.makeText(applicationContext, "No permission for read contacts / Нет разрешения для чтения контактов", Toast.LENGTH_SHORT).show()
                }
        }

        private fun readContact() {
            btn_read_contact.setOnClickListener{
                val contactList : MutableList<ContactDTO> = ArrayList()
                val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
                if (contacts != null) {
                    while (contacts.moveToNext()){
                        val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val photoUri = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                        val obj = ContactDTO()
                        if(photoUri != null) {
                            obj.image = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photoUri))
                        }
                        obj.name = name
                        obj.number = number
                        contactList.add(obj)
                    }

                    contact_list.adapter = ContactAdapter(contactList, this)
                    Toast.makeText(applicationContext, "Found ${contactList.size} contacts / Нашлось ${contactList.size} контактов", Toast.LENGTH_SHORT).show()
                    contacts.close()
                }
            }
        }

    class ContactAdapter(items: List<ContactDTO>, ctx: Context) :
        RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

        private var list = items
        private var context = ctx

        override fun getItemCount(): Int {
            return list.size
        }
        override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
            holder.name.text = list[position].name
            holder.number.text = list[position].number
            if (list[position].image != null)
                holder.image.setImageBitmap(list[position].image)
            else
                holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher_round))
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ContactAdapter.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
            )
        }
        class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
            val name = v.tv_name!!
            val number = v.tv_number!!
            val image = v.iv_image!!
            init {
                itemView.setOnClickListener{
                    var uri = Uri.parse("tel:"+number.text.toString())
//                    startActivity(Intent(Intent.ACTION_DIAL, uri))
                }
            }
        }
    }

    fun openContactByNumber(view: View) {
        var uri = Uri.parse("tel:"+tv_number.text.toString())
        startActivity(Intent(Intent.ACTION_DIAL, uri))
    }
}