package com.practica.proyect_no_name.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.edifica.R

class FormularySpinnerAdapter(context: Context, var textViewResourceId: Int, var objects: Array<String>) : ArrayAdapter<String>(context, textViewResourceId, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView (position: Int, convertView: View?, parent: ViewGroup) : View {
        var view: View? = convertView

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.formulary_spinner_row, parent, false)
        }

        var textView = view?.findViewById<TextView>(R.id.formulary_tex_spinner_row)

        textView?.text = getItem(position)

        return view!!
    }

}