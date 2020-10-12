package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var isNewOperation = true
    var isPlusMinus = true
    var oldNumber = ""
    var operation = "+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun numberEvent(view: View) {
        if(isNewOperation){
            editText.setText("")
        }
        isNewOperation = false
        var buttonClick = editText.text.toString()
        var buttonSelect = view as Button
        when(buttonSelect.id){
            button1.id -> {buttonClick += "1"}
            button2.id -> {buttonClick += "2"}
            button3.id -> {buttonClick += "3"}
            button4.id -> {buttonClick += "4"}
            button5.id -> {buttonClick += "5"}
            button6.id -> {buttonClick += "6"}
            button7.id -> {buttonClick += "7"}
            button8.id -> {buttonClick += "8"}
            button9.id -> {buttonClick += "9"}
            button0.id -> {buttonClick += "0"}
            buttonComma.id -> {buttonClick += "."}
            buttonPlusMinus.id -> {
                if (isPlusMinus){
                buttonClick = "-$buttonClick"}
                isPlusMinus = false
            }
        }
        editText.setText(buttonClick)
    }

    fun operatorEvent(view: View) {
        isNewOperation = true
        oldNumber = editText.text.toString()
        var buttonSelect = view as Button
        when(buttonSelect.id){
            buttonMultiply.id -> {operation = "*"}
            buttonPercent.id -> {operation = "/"}
            buttonMinus.id -> {operation = "-"}
            buttonPlus.id -> {operation = "+"}
        }
    }

    fun equalEvent(view: View) {
        var newNumber = editText.text.toString()
        var result = 0.0
        when(operation) {
            "+" -> {result = oldNumber.toDouble() + newNumber.toDouble()}
            "*" -> {result = oldNumber.toDouble() * newNumber.toDouble()}
            "/" -> {result = oldNumber.toDouble() / newNumber.toDouble()}
            "-" -> {result = oldNumber.toDouble() - newNumber.toDouble()}
        }
        editText.setText(result.toString())
    }

    fun delEvent(view: View) {
        editText.setText("0")
        isNewOperation = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("editText", editText.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editText.text = savedInstanceState.getString("editText")
    }

}

