package com.hector.cocheshector.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.hector.cocheshector.R
import com.hector.cocheshector.adapter.CustomAdapter
import com.hector.cocheshector.model.Vehiculo

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MenuActivity"
    private lateinit var adapter: CustomAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var vehiculos: ArrayList<Vehiculo> = ArrayList()
    private var fotos: ArrayList<String> = ArrayList()
    val foto = "https://cdn.motor1.com/images/mgl/pvvk1/s1/ferrari-f8-tributo-in-london.jpg"
    val foto2 = "https://cdn.motor1.com/images/mgl/byyNj/s1/ferrari-f8-tributo-in-london.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val iduser = intent.getSerializableExtra("idUser") as String?

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        initRV()
        setListener()

        fotos.add(foto)
        fotos.add(foto2)

        fab.setOnClickListener { view ->
            toast("tu: ${iduser}")
            addCarro()
        }
    }

    private fun addCarro() {
        val vehiculo: MutableMap<String, Any> = HashMap()
        vehiculo["marca"] = "Ferrari"
        vehiculo["modelo"] = "F8 Tributo"
        vehiculo["color"] = "Rojo"
        vehiculo["anno"] = "2020"
        vehiculo["carroceria"] = "Coupé"
        vehiculo["km"] = "200"
        vehiculo["precio"] = "300000"
        vehiculo["fotos"] = fotos
        vehiculo["iduser"] = "4TtGH5vgH52wKQutjKfY\n"


        db.collection("vehiculos")
            .add(vehiculo)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "Usuario registrado correctamente")
                } else {
                    Log.d(TAG, "Fallo al registrar el usuario")
                }
            }
    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.rowvehiculos)
        rvVehiculos.adapter = adapter
        rvVehiculos.layoutManager = LinearLayoutManager(this)
    }

    private fun setListener() {
        val docRef = db.collection("vehiculos")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && !snapshot.isEmpty) {
                documentToList(snapshot.documents)
                adapter.setVehiculos(vehiculos)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }
    private fun documentToList(documents: List<DocumentSnapshot>) {
        vehiculos.clear()
        documents.forEach{d ->
            val marca = d["marca"] as String
            val modelo = d["modelo"] as String
            val color = d["color"] as String
            val anno = d["anno"] as String
            val carroceria = d["carroceria"] as String
            val km = d["km"] as String
            val precio = d["precio"] as String
            val fotos = d["fotos"] as ArrayList<String>
            val iduser = d["iduser"] as String
            vehiculos.add(Vehiculo(marca = marca, modelo = modelo, color = color, anno = anno, carroceria = carroceria, km = km, precio = precio, fotos = fotos, idUser = iduser))
        }
    }

    fun onClickRow(v: View){
      // val vehiculoSelect = v.tag as Vehiculo

          /*
            val cartonmarcado=v.tag as Carton
            if(cartonmarcado.selec){
                cartonesclick.remove(cartonmarcado)
                count--
            }else{
                cartonesclick.add(cartonmarcado)
                count++
            }
            cartonmarcado.selec= !cartonmarcado.selec
            adapter.notifyDataSetChanged()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item!!.itemId
        if( id == R.id.perfil ){
            //startActivity(Intent(this, PerfilActivity::class.java))
        }
        if( id == R.id.salir ){
            alert("¿Seguro que te quieres ir?") {
                yesButton {
                    salir()
                }
                noButton { }
            }.show()
        }

        return super.onOptionsItemSelected(item)
    }
    private fun salir(){
        mAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
