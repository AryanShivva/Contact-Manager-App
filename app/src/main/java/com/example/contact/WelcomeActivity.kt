package com.example.contact

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WelcomeActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    lateinit var dialog : Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        supportActionBar?.hide()

        val userName    = findViewById<TextInputEditText>(R.id.etNam)
        val userMail   = findViewById<TextInputEditText>(R.id.etMai)
        val userContact = findViewById<TextInputEditText>(R.id.etContact)
        val signUpButton = findViewById<Button>(R.id.btnSignUp)


        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialogue)
        var buttonGood = dialog.findViewById<Button>(R.id.button3)
        var buttonFeedback = dialog.findViewById<Button>(R.id.button)

        buttonGood.setOnClickListener {
            dialog.dismiss()
        }

        buttonFeedback.setOnClickListener {
            //intent can be given
            finish()
        }

        signUpButton.setOnClickListener {

            val name = userName.text.toString()
            val mail = userMail.text.toString()
            val usrContact = userContact.text.toString()

            val contact = Contact(name, mail, usrContact)

            databaseReference = FirebaseDatabase.getInstance().getReference("Contact")


        }

        signUpButton.setOnClickListener {
            dialog.show()
        }




    }
}