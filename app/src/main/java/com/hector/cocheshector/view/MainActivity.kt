package com.hector.cocheshector.view

import android.content.Context
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
    var emailCurrent: String=""
    private var vehiculos: ArrayList<Vehiculo> = ArrayList()
    private var iduser: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        iduser = intent.getSerializableExtra("idUser") as? String


        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // OBTENIENDO DATOS DEL CURRENT USER
        emailCurrent = (mAuth.currentUser!!.email).toString()
        val idPreference = getSharedPreferences("datos", Context.MODE_PRIVATE)

        initRV()
        setListener()
        if(iduser!=null){
            actualizarIdUser()
            val idpref = idPreference.edit()
            idpref.putString("iduser", "${iduser}")
            idpref.commit()
        }

        fab.setOnClickListener { view ->
            val userid = idPreference.getString("iduser", "null")
            startActivity(Intent(this,AddCarroActivity::class.java).putExtra("userid",userid))
        }

    }

    private fun actualizarIdUser() {
        val idRef = db.collection("usuarios").document("${iduser}")
        idRef.update("iduser", iduser)
            .addOnSuccessListener {Log.d(TAG, "DocumentSnapshot successfully updated!")}
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    private fun initRV(){
        adapter = CustomAdapter(this, R.layout.rowvehiculos)
        rvVehiculos.adapter = adapter
        rvVehiculos.layoutManager = LinearLayoutManager(this)
    }
    private fun setListener(){
        val docRef = db.collection("vehiculos")
        docRef.addSnapshotListener { snapshot, e ->
            if(e!=null){
                Log.w(TAG, "listen FAIL", e)
                return@addSnapshotListener
            }
            if(snapshot != null && !snapshot.isEmpty){
                documentToList(snapshot.documents)
                adapter.setVehiculos(vehiculos)
            }else{
                Log.d(TAG, "Current data: Null")
            }
        }
    }

    private fun documentToList(documents: List<DocumentSnapshot>) {
        vehiculos.clear()
        documents.forEach{ d ->
            val marca = d["marca"] as? String
            val modelo = d["modelo"] as? String
            val color = d["color"] as? String
            val anno = d["anno"] as? String
            val carroceria = d["carroceria"] as? String
            val caballos = d["caballos"] as? String
            val km = d["km"] as? String
            val precio = d["precio"] as? String
            val fotos = d["fotos"] as? String
            val iduser = d["iduser"] as? String
            val comprado = d["comprado"] as Boolean
            vehiculos.add(Vehiculo(marca = marca, modelo = modelo, color = color, anno = anno, carroceria = carroceria, caballos = caballos, km = km, precio = precio, fotos = fotos, iduser = iduser, comprado = false))
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