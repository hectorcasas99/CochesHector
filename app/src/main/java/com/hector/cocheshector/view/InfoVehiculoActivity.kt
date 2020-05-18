package com.hector.cocheshector.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hector.cocheshector.R
import com.hector.cocheshector.model.Usuario
import com.hector.cocheshector.model.Vehiculo
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_info_vehiculo.*
import kotlinx.android.synthetic.main.activity_info_vehiculo.tvNombreVehiculo
import kotlinx.android.synthetic.main.activity_info_vehiculo.tvPrecio
import kotlinx.android.synthetic.main.rowvehiculos.*
import kotlinx.android.synthetic.main.rowvehiculos.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import java.text.DecimalFormat

class InfoVehiculoActivity : AppCompatActivity() {

    private val TAG = "InfoVehiculoActivity"
    private lateinit var vehiculo: Vehiculo
    private lateinit var db: FirebaseFirestore
    private lateinit var user: Usuario
    val dec = DecimalFormat("###,###,###")

    var email: String? =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_vehiculo)

        db = FirebaseFirestore.getInstance()
        vehiculo = intent.getSerializableExtra("vehiculoSelect") as Vehiculo


        //usuario = intent.getSerializableExtra("usuario") as Usuario
        infoUser()
        rellenarDatos()

    }



    private fun infoUser() {
            db.collection("usuarios").document("${vehiculo.iduser}").get()
                .addOnSuccessListener { document ->
                    if(document.exists()){
                        //traemos datos
                        email = document.getString("email")
                        Log.d(TAG, "el propietario es:  ${email}")
                    } else {
                        Log.d(TAG, "no existe")
                    }
                }


    }


    private fun rellenarDatos() {
        tvNombreVehiculo.text = "${vehiculo.marca} ${vehiculo.modelo}"
        tvPrecio.text = "${dec.format(vehiculo.precio!!.toInt())}"

        if(vehiculo.fotos.equals("")){
            Picasso.get().load(R.drawable.coche).into(ivCarro)
        }else {
            Picasso.get().load(vehiculo.fotos).into(ivcarro)
        }

        tvanno1.text = vehiculo.anno
        tvkm1.text = "${dec.format(vehiculo.km!!.toInt())}"
        tvCarroceria1.text = vehiculo.carroceria
        if(vehiculo.carroceria=="Clásico" || vehiculo.km!!.toInt()<1000){
            tvGanga.text = "!!ESTE COCHE ES UNA GANGA!!"
        }
        if(vehiculo.carroceria == "Cabrio"){
            tvCarroceria2.text = "!No te despeines¡"
        }
        if(vehiculo.caballos!!.toInt()>700){
            tvcaballos3.text = "No te verán pasar"
        }

        tvcaballos1.text = "${dec.format(vehiculo.caballos!!.toInt())}"
        tvcolor1.text = vehiculo.color

        // DATOS USE

        tvemail1.text = email

    }
}