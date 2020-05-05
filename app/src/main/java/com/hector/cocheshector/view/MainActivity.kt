package com.hector.cocheshector.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hector.cocheshector.R
import com.hector.cocheshector.adapter.CustomAdapter
import com.hector.cocheshector.model.Vehiculo

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MenuActivity"
    private lateinit var adapter: CustomAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var vehiculos: ArrayList<Vehiculo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val iduser = intent.getSerializableExtra("idUser") as String?

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        initRV()
        setListener()

        fab.setOnClickListener { view ->
            toast("tu: ${iduser}")
        }
    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.rowvehiculos)

        /*
        adapter = CustomAdapter(this, R.layout.row)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
         */
    }

    private fun setListener() {

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
