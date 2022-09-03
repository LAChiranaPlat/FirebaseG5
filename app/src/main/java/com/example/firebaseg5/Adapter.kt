package com.example.firebaseg5

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseg5.databinding.TemplateBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Adapter(var items:ArrayList<Usuarios>):RecyclerView.Adapter<Adapter.Content>() {

    class Content(view:TemplateBinding):RecyclerView.ViewHolder(view.root) {

        val txtName:TextView
        val txtCurso:TextView
        val btnUpdate:Button
        val btnDelete:Button

        init {
            txtName=view.usuario
            txtCurso=view.curso
            btnUpdate=view.btnUpdate
            btnDelete=view.btnDelete
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.Content {

        val views=TemplateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Content(views)

    }

    override fun onBindViewHolder(holder: Adapter.Content, position: Int) {

        var currentItem=items.get(position)

        holder.apply {
            txtName.text=currentItem.nombres+", "+currentItem.apellidos
            txtCurso.text=currentItem.curso

            btnDelete.setOnClickListener {
                //Log.i("result","Borrar: ${currentItem.idDocument}")
                val db = Firebase.firestore

                db.collection("Student").document(currentItem.idDocument)
                    .delete()
                    .addOnSuccessListener {
                        items.removeAt(position)
                        notifyDataSetChanged()
                    }

            }

            btnUpdate.setOnClickListener {

                it.context.startActivity(Intent(it.context,update::class.java).apply {
                    putExtra("name",currentItem.nombres)
                    putExtra("lname",currentItem.apellidos)
                    putExtra("curso",currentItem.curso)
                    putExtra("doc",currentItem.idDocument)
                })

                Log.i("result","Actualizar: ${currentItem.idDocument}")
            }
        }

    }

    override fun getItemCount()=items.size
}