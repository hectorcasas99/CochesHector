package com.hector.cocheshector.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hector.cocheshector.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_login.register_btn
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), View.OnClickListener  {

    private val TAG = "RegisterActivity"

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        register_btn.setOnClickListener(this)
        profile_picture.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.register_btn -> {
                onRegister()
            }
        }
        when (view.id){
            R.id.profile_picture -> {
                fotoPerfil()
            }
        }
    }

    private fun fotoPerfil() {
        toast("soy tu fotito")
    }
    private fun onRegister() {
        val user = mAuth.currentUser
        val nombreCompleto = edt_nombreCompleto.text.toString()
        val nick = edt_nick.text.toString()
        val email = edt_email.text.toString()
        val pass1 = edt_password.text.toString()
        val pass2 = edt_password2.text.toString()

        if(nombreCompleto.isEmpty() || nick.isEmpty() || email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()){
            longToast("Todos los campos son necesarios")
        } else {
            if(pass1 == pass2){
                mAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener {
                    if(it.isSuccessful){
                        //add user

                        if(it.isSuccessful){
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }else {
                            longToast("lo siento algo no va bien")
                        }
                    } else {
                        longToast("adios")
                    }
                }
            } else {
                longToast("Las contraseñas no coinciden")
            }
        }
    }

}
