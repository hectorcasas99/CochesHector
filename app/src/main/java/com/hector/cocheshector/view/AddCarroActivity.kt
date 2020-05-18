package com.hector.cocheshector.view

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hector.cocheshector.R
import kotlinx.android.synthetic.main.activity_add_carro.*
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.HashMap

class AddCarroActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    private val TAG = "AddCarroActivity"
    private lateinit var db: FirebaseFirestore
    val arrayCarroceria = arrayOf("Elige Carrocería","Coupé","Berlina","SUV","Compacto","Cabrio","Clásico","Hipercar")
    var carroceriaCoche:String = ""

    var foto:String = ""
    var iduser: String? = ""
    var selectedPhotoUri: Uri? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_carro)

        iduser = intent.getSerializableExtra("idUser") as? String


        db = FirebaseFirestore.getInstance()
        btnAddFoto.setOnClickListener{ addFotos() }
        btnRegistrarVehiculo.setOnClickListener { registrarVehiculo() }
        rellenarSpinnerCarroceria()


    }
    // SPINNER MARCA
    private fun rellenarSpinnerCarroceria() {
        val adapterSpinnerCarroceria =  ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayCarroceria)

        spinCarroceria.onItemSelectedListener = this
        spinCarroceria.adapter = adapterSpinnerCarroceria
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        carroceriaCoche = arrayCarroceria[position]

    }
    override fun onNothingSelected(parent: AdapterView<*>?) { }



    private fun addFotos() {
        Log.d(TAG, "hola soy tu foto")

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data!=null){
            Log.d(TAG, "foto select")
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            ivFoto.setBackgroundDrawable(bitmapDrawable)
        }

    }
    private fun subirfoto(){
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/user/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "success upload: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    foto = it.toString()
                    Log.d(TAG, "success upload: ${foto}")
                }
            }
    }

    private fun registrarVehiculo() {
        val marca = edt_marca.text.toString()
        val modelo = edt_modelo.text.toString()
        val color = edt_color.text.toString()
        val anno = edt_anno.text.toString()
        val carroceria = carroceriaCoche
        val caballos = edt_caballos.text.toString()
        val km = edt_km.text.toString()
        val precio = edt_precio.text.toString()
        val fotos = foto
        val iduser = "4TtGH5vgH52wKQutjKfY"
        val comprado = false

        if (iduser != null) {
            if(marca.isEmpty() || modelo.isEmpty() || color.isEmpty() || anno.isEmpty() || carroceria.equals("Elige Carrocería")|| caballos.isEmpty() || km.isEmpty() || precio.isEmpty() || fotos.isEmpty() || iduser.isEmpty() ){
                toast("todos los campos son necesarios")
            } else {
                val vehiculo: MutableMap<String, Any> = HashMap()
                vehiculo["marca"] = marca
                vehiculo["modelo"] = modelo
                vehiculo["color"] = color
                vehiculo["anno"] = anno
                vehiculo["carroceria"] = carroceria
                vehiculo["caballos"] = caballos
                vehiculo["km"] = km
                vehiculo["precio"] = precio
                vehiculo["fotos"] = fotos
                vehiculo["iduser"] = iduser
                vehiculo["comprado"] = comprado

                db.collection("vehiculos")
                    .add(vehiculo as Map<String, Any>)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d(TAG, "Vehiculo registrado correctamente")
                            subirfoto()

                        } else {
                            Log.d(TAG, "Fallo al registrar el usuario")
                        }
                    }

                toast("Vehiculo registrado correctamente")
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }


}
