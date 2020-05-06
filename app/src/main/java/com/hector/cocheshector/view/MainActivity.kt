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
    val foto = "https://lh3.googleusercontent.com/proxy/-KMULGughzLjU7geeQ2HVdDKTEUq7hSEx22wagWDjzwkXEQiGpgno0VgBa1UgeSIRJ07WWj7-R4K8LuWeQeJaA1Rt11dQsABVanDucod4JC42MvGnXsb7Y0C7HGAEIX6VdkeBJ9xvzDqmkm6OjhdqJ8gaDCYhQijK-1q3k4"
    val foto2 = "https://img.milanuncios.com/fg/3281/14/328114639_1.jpg?VersionId=fCPavMPAngHMX5oeA8OqCi1mofOaPlQQ"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        initRV()
        setListener()

        fotos.add(foto)
        fotos.add(foto2)

        fab.setOnClickListener { view ->
            startActivity(Intent(this,InfoVehiculoActivity::class.java))
        }
    }

    /*private fun addCarro() {
        val vehiculo: MutableMap<String, Any> = HashMap()
        vehiculo["marca"] = "Lamborghini"
        vehiculo["modelo"] = "Huracan EVO"
        vehiculo["color"] = "Azul"
        vehiculo["anno"] = "2020"
        vehiculo["carroceria"] = "Coupé"
        vehiculo["caballos"] = "640"
        vehiculo["km"] = "200"
        vehiculo["precio"] = "350000"
        vehiculo["fotos"] = fotos
        vehiculo["iduser"] = "4TtGH5vgH52wKQutjKfY"


        db.collection("vehiculos")
            .add(vehiculo)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "Vehicula registrado correctamente")
                    toast("Tu vehiculo se ha añadido correctamente")
                } else {
                    Log.d(TAG, "Fallo al registrar el vehiculo")
                }
            }
    }*/

    private fun initRV(){
        adapter = CustomAdapter(this, R.layout.rowvehiculos)
        rvVehiculos.adapter = adapter
        rvVehiculos.layoutManager = LinearLayoutManager(this)
    }
    private fun setListener(){
        val docRef = db.collection("vehiculos")
        docRef.addSnapshotListener { snapshot, e ->
            if(e != null){
                Log.w(TAG, "listen FAIL", e)
                return@addSnapshotListener
            }
            if(snapshot != null && !snapshot.isEmpty){
                documentToList(snapshot.documents)
                adapter.setVehiculos(vehiculos)
            }else{
                Log.d(TAG, "Current Data: Null")
            }
        }
    }
    private fun documentToList(documents: List<DocumentSnapshot>){
        vehiculos.clear()
        documents.forEach{d ->
            val marca = d["marca"] as? String
            val modelo = d["modelo"] as?String
            val color = d["color"] as? String
            val anno = d["anno"] as? String
            val carroceria = d["carroceria"] as? String
            val caballos = d["caballos"] as? String
            val km = d["km"] as? String
            val precio = d["precio"] as? String
            val fotos = d["fotos"] as ArrayList<String>
            val iduser = d["iduser"] as? String
            vehiculos.add(Vehiculo(marca = marca, modelo = modelo, color = color, anno = anno, carroceria = carroceria, caballos = caballos, km = km, precio = precio, fotos = fotos, iduser = iduser))
        }
    }

    fun onClickRow(v: View){
        val vehiculoSelect = v.tag as Vehiculo
        startActivity(Intent(this,InfoVehiculoActivity::class.java).putExtra("vehiculoSelect", vehiculoSelect))
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