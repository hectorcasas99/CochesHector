package com.hector.cocheshector.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.hector.cocheshector.R
import kotlinx.android.synthetic.main.activity_add_carro.*
import org.jetbrains.anko.toast

class AddCarroActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    val arrayMarca = arrayOf("Elige Marca","Aston Martin","Audi","Bentley","BMW","Bugatti","Ferrari","Jaguar","Koenigsegg","Lamborghini","Land Rover","Maserati","Mercedes","Pagani","Porsche","Rolls Royce","Tesla")
    val arrayCarroceria = arrayOf("Elige Carrocería","Coupé","Berlina","SUV","Compacto","Cabrio","Clásico","Hipercar")
    val arrayColor = arrayOf("Elige Color","Rojo","Naranja","Amarillo","Rosa","Azul","Verde","Marrón","Blanco","Gris")
    var marcaCoche:String = ""
    var colorCoche:String = ""
    var carroceriaCoche:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_carro)

        btnAddFoto.setOnClickListener{ addFotos() }
        btnRegistrarVehiculo.setOnClickListener { registrarVehiculo() }
        rellenarSpinnerMarca()
        //rellenarSpinnerColor()
        //rellenarSpinnerCarroceria()
    }
    // SPINNER MARCA
    private fun rellenarSpinnerMarca() {
        val adapterSpinnerMarca =  ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayMarca)

        spinMarca.onItemSelectedListener = this
        spinMarca.adapter = adapterSpinnerMarca
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        marcaCoche = arrayMarca[position]

    }
    override fun onNothingSelected(parent: AdapterView<*>?) { }

   /* // SPINNER COLOR
    private fun rellenarSpinnerColor() {
        val adapterSpinnerColor =  ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayColor)

        spinColor.onItemSelectedListener = this
        spinColor.adapter = adapterSpinnerColor
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        colorCoche = arrayColor[position]
    }
    override fun onNothingSelected(parent: AdapterView<*>?) { }


    // SPINNER CARROCERIA
    private fun rellenarSpinnerCarroceria() {
        val adapterSpinnerCarroceria =  ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayCarroceria)

        spinCarroceria.onItemSelectedListener = this
        spinCarroceria.adapter = adapterSpinnerCarroceria
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        carroceriaCoche = arrayCarroceria[position]

    }
    override fun onNothingSelected(parent: AdapterView<*>?) { }

*/

    private fun addFotos() {

    }
    private fun registrarVehiculo() {
        toast("${marcaCoche}")

    }
}
