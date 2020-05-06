package com.hector.cocheshector.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hector.cocheshector.R
import com.hector.cocheshector.model.Vehiculo
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_info_vehiculo.*
import java.text.DecimalFormat

class InfoVehiculoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_vehiculo)

        val dec = DecimalFormat("###,###,###")

        val vehiculo = intent.getSerializableExtra("vehiculoSelect") as Vehiculo

        tvNombreVehiculo.text = "${vehiculo.marca} ${vehiculo.modelo}"
        tvPrecio.text = "${dec.format(vehiculo.precio!!.toInt())}"
        Picasso.get().load(vehiculo.fotos[0]).into(ivInfoCarro)
        tvanno1.text = vehiculo.anno
        tvkm1.text = vehiculo.km

        if(vehiculo.carroceria=="Clásico" || vehiculo.km!!.toInt()<1000){
            tvGanga.text = "!!ESTE COCHE ES UNA GANGA!!"
        }
        tvcaballos1.text = vehiculo.caballos
        tvcolor1.text = vehiculo.color

    }
}
