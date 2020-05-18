package com.hector.cocheshector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hector.cocheshector.R
import com.hector.cocheshector.model.Vehiculo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info_vehiculo.view.*
import kotlinx.android.synthetic.main.rowvehiculos.view.*
import kotlinx.android.synthetic.main.rowvehiculos.view.tvCarroceria
import kotlinx.android.synthetic.main.rowvehiculos.view.tvNombreVehiculo
import kotlinx.android.synthetic.main.rowvehiculos.view.tvPrecio
import kotlinx.android.synthetic.main.rowvehiculos.view.tvkm
import java.text.DecimalFormat

/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Vehiculo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setVehiculos(vehiculos: List<Vehiculo>) {
        this.dataList = vehiculos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Vehiculo){
           val dec = DecimalFormat("###,###,###")
            itemView.tvNombreVehiculo.text = "${dataItem.marca} ${dataItem.modelo}"
            itemView.tvPrecio.text = "${dec.format(dataItem.precio!!.toInt())}€"
            itemView.tvkm.text = "${dec.format(dataItem.km!!.toInt())}"
            itemView.tvCarroceria.text = dataItem.carroceria
            itemView.tvCaballos.text = dataItem.caballos
            //itemView.tvanno.text = dataItem?.anno
            if(dataItem.fotos.equals("")){
                Picasso.get().load(R.drawable.coche).into(itemView.ivCarro)
            }else {
                Picasso.get().load(dataItem?.fotos).into(itemView.ivCarro)
            }

            itemView.tag = dataItem

        }

    }
}