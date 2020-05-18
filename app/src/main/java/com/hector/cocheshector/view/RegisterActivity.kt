package com.hector.cocheshector.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hector.cocheshector.R
import com.hector.cocheshector.model.Usuario
import kotlinx.android.synthetic.main.activity_add_carro.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_login.register_btn
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.*
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity(), View.OnClickListener  {

    private val TAG = "RegisterActivity"

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var idUser: String = ""
    var selectPhotoUri: Uri? = null
    var foto: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        register_btn.setOnClickListener(this)
       // btntuFoto.setOnClickListener{fotoPerfil()}
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.register_btn -> {
                onRegister()
            }
        }
    }

    /*private fun fotoPerfil() {
        Log.d(TAG, "hola soy tu foto")

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image"
        startActivityForResult(intent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d(TAG, "hola foto select")
            selectPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            profile_picture.setBackgroundDrawable(bitmapDrawable)
        }
        subirfoto()
    }

    private fun subirfoto() {
        if(selectPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/user/$filename")
        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "success upload: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    foto = it.toString()
                    Log.d(TAG, "success upload: ${foto}")
                }
            }
    }*/

    private fun onRegister() {
        val user = mAuth.currentUser
        val nombre = edt_nombreCompleto.text.toString()
        val nick = edt_nick.text.toString()
        val email = edt_email.text.toString()
        val pass1 = edt_password.text.toString()
        val pass2 = edt_password2.text.toString()
        val foto = foto
        var iduser = "null"

        if(nombre.isEmpty() || nick.isEmpty() || email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()){
            longToast("Todos los campos son necesarios")
        } else {
            if(pass1 == pass2){
                mAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener {
                    if(it.isSuccessful){
                        //add user
                        val user: MutableMap<String, Any> = HashMap()
                        user["nombre"] = nombre
                        user["nick"] = nick
                        user["email"] = email
                        user["foto"] = foto
                        user["iduser"] = "null"

                        db.collection("usuarios")
                            .add(user)
                            .addOnCompleteListener { documentReference ->
                                if (it.isSuccessful) {
                                    Log.d(TAG, "Usuario registrado correctamente")
                                    //subirfoto()
                                } else {
                                    Log.d(TAG, "Fallo al registrar el usuario")
                                }
                            }
                            .addOnSuccessListener { documentReference ->
                                if(it.isSuccessful){
                                    Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.id)
                                    idUser = (documentReference.id).toString()
                                    startActivity(Intent(this,MainActivity::class.java).putExtra("idUser", idUser))
                                    finish()
                                } else {
                                    Log.d(TAG, "Fallo al registrar el usuario")
                                }

                            }
                    } else {
                        toast("Fallo al escribir el correo")
                    }
                }
            } else {
                longToast("Las contraseñas no coinciden")
            }
        }
    }



}
