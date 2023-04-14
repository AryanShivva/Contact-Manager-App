# Contact_Manager_App


How to Create Dialog with Custom Layout in Android?

In Android, A dialog is a small window that prompts the user to make a decision, provide some additional information, and inform the user about some particular task. The following are the main purposes or goals of a dialog

To warn the user about any activity.
To inform the user about any activity.
To tell the user whether it is an error or not.
To tell the user that his/her desired action has been succeeded.

Step 1: Create a New Project

Step 2: Create another layout XML file(You can create drawable resource files for designing and add into this file)

Create another layout XML file that you want to show in your dialog. You can add any element to this layout.

    <LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="wrap_content"
    android:orientation="vertical"
    android:background="@drawable/back"
    android:layout_height="wrap_content">


    <ImageView
        android:id="@+id/imageView3"
        android:layout_width="wrap_content"
        android:layout_height="150dp"
        app:srcCompat="@drawable/check_mark_icon_tick_symbol_positive_check_mark_logo_flat_icon_png" />

    <TextView
        android:id="@+id/textView4"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Your contact saved sucessfully to our database"
        android:fontFamily="monospace"
        android:textSize="15dp"
        android:textColor="@color/black"
        android:gravity="center"
        android:textStyle="bold"

        />

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="77dp"
        android:layout_margin="20dp"
        android:orientation="horizontal">

        <Button
            android:id="@+id/button"
            android:layout_width="153dp"
            android:layout_height="wrap_content"
            android:layout_margin="10dp"
            android:background="@drawable/bg_alert_background"
            android:backgroundTint="@color/black"
            android:text="Back"

            />

        <Button
            android:id="@+id/button3"
            android:layout_width="153dp"
            android:layout_height="wrap_content"
            android:layout_margin="10dp"
            android:background="@drawable/bg_alert_background"
            android:backgroundTint="@color/black"
            android:text="thank you" />

    </LinearLayout>

    </LinearLayout>
    
Step 3 : Activity.kt Code that should be done to make dialogue box work

    lateinit var dialog : Dialog
    
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
        
->ADDING SOME MORE DETAILS TO THE EXISTING DATABASE FOR ALREADY CREATED USER(Activity.kt file)


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
    
    
->CHECKING WHETHER THE USER EXISTS IN OUR DTABASE OR NOT AND PROCEDDING TO THE ACTIVITY FILE USING INTENTS

SignInActivity.kt File Code

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


    
    
    

