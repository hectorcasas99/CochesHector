package com.hector.cocheshector.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.hector.cocheshector.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast

class LoginActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var  mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        login_btn.setOnClickListener(this)
        register_btn.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.login_btn -> {
                val email = edt_email.text.toString()
                val password = edt_password.text.toString()
                if( email.isNotEmpty() && password.isNotEmpty()){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        } else {
                            longToast("Email o contraseña incorrectos")
                        }
                    }
                } else {
                    longToast("Email o contraseña vacíos!!")
                }
            }
        }
        when(view.id) {
            R.id.register_btn -> {
               startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}
