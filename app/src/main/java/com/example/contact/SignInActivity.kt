package com.example.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    companion object{
        const val KEY1 ="com.example.day16database.SignInActivity,mail"
        const val KEY2 ="com.example.day16database.SignInActivity,name"
        const val KEY3 ="com.example.day16database.SignInActivity,id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar?.hide()//to hide actionbar

        val signInButton = findViewById<Button>(R.id.btnSignIn)
        val userName = findViewById<TextInputEditText>(R.id.userNameEditText)

        signInButton.setOnClickListener {
            //take refrence till node "users" in database
            //the user enters the name in our app
            val uniqueId=userName.text.toString()

            if(uniqueId.isNotEmpty()){
                readData(uniqueId)
            }else{
                //display message
                Toast.makeText(this,"please enter user name", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun readData(uniqueId: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.child(uniqueId).get().addOnSuccessListener {
            //checks whether user exists or not
            //it means uniqueId
            if(it.exists()){
                //welcome user in our app ,with intent
                //we are getting value using ".value" and using "it.child" we reach to the path email etc
                val email=it.child("email").value
                val name=it.child("name").value
                val userId=it.child("uniqueId").value
                //INTENT
                val intentWelcome = Intent(this, WelcomeActivity::class.java)
                intentWelcome.putExtra(KEY2, name.toString())
                intentWelcome.putExtra(KEY1, email.toString())
                intentWelcome.putExtra(KEY3,userId.toString())
                startActivity(intentWelcome)

            }else{
                Toast.makeText(this, "User doesm't exist", Toast.LENGTH_SHORT).show()
            }

        } .addOnFailureListener {
            Toast.makeText(this,"Failed,Error in database from our side", Toast.LENGTH_SHORT).show()
        }





    }
}