package com.example.tubescoba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var emptyusername : EditText
    private lateinit var emptypassword : EditText
    private lateinit var emptyemail : EditText
    private lateinit var buttonsubmit : Button
    private lateinit var buttonback : Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        emptyusername=findViewById(R.id.emptyusername)
        emptypassword=findViewById(R.id.emptypassword)
        emptyemail=findViewById(R.id.emptyemail)
        buttonsubmit=findViewById(R.id.buttonsubmit)
        buttonback=findViewById(R.id.buttonback)

        dbRef= FirebaseDatabase.getInstance().getReference("admin")

        buttonsubmit.setOnClickListener {
            saveAdminData()
        }
        buttonback.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }
    private fun saveAdminData(){
        val username=emptyusername.text.toString()
        val password=emptypassword.text.toString()
        val email=emptyemail.text.toString()

        if (username.isEmpty()){
            emptyusername.error="Please enter nama anda"
        }
        if (password.isEmpty()){
            emptypassword.error="Please enter password anda"
        }
        if (email.isEmpty()){
            emptyemail.error="Please enter email anda"
        }

        val idpegawai=dbRef.push().key!!
        val employee=EmployeeModel(idpegawai,username,password,email)
        dbRef.child(idpegawai).setValue(employee)
            .addOnCompleteListener{
                Toast.makeText(this,"Data berhasil masuk", Toast.LENGTH_LONG).show()
            }

    }
}