package com.example.firebaseg5

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseg5.databinding.ActivityMainBinding
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var layout:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layout.root)

        val db = Firebase.firestore

        var lista:ArrayList<Usuarios> = ArrayList()
        var myAdapter:Adapter?=null

        db.collection("Student")
            .get()
            .addOnSuccessListener {
                for (document in it) {

                    lista.add(Usuarios(document.data.get("nombres").toString(),document.data.get("apellidos").toString(),document.data.get("curso").toString(),document.id))
                    myAdapter= Adapter(lista)
                    layout.lista.layoutManager=LinearLayoutManager(this)
                    layout.lista.adapter=myAdapter
                }
            }
            .addOnFailureListener {  }

        layout.apply {

            btnSave.setOnClickListener {
                // Create a new user with a first and last name
                if(tilNameUser.editText?.text!!.isEmpty())
                    return@setOnClickListener

                val user = hashMapOf(
                    "nombres" to tilNameUser.editText?.text.toString(),
                    "apellidos" to "Apellido Agregado",
                    "curso" to "LAChirana Plat 2022"
                )

                db.collection("Student")
                .add(user)
                    .addOnSuccessListener {

                        lista.add(Usuarios(tilNameUser.editText?.text.toString(),"Apellido Agregado","LAChirana Plat 2022",it.id.toString()))
                        myAdapter?.notifyDataSetChanged()

                        tilNameUser.requestFocus()
                        tilNameUser.editText?.text!!.clear()


                    }.addOnFailureListener { e ->
                        Log.w("x", "Error adding document", e)


                }



            }

        /*
            btnLeer.setOnClickListener {

                db.collection("Student")
                    .orderBy("nombres", Query.Direction.ASCENDING)
                    .limit(2)
                    .get()
                    .addOnSuccessListener { result ->

                        for (document in result) {
                            Log.d("Y", "${document.id} => ${document.data.get("curso")},${document.data.get("nombres")},${document.data.get("apellidos")}")
                        }

                    }
                    .addOnFailureListener { exception ->
                        Log.i("Y",exception.message.toString())
                        Log.w("Y", "Error getting documents.", exception)
                    }
            }
            btnBorrar.setOnClickListener{
                var doc=db.collection("ADK").document("Javascript_Experto")
                doc.delete()
                    .addOnSuccessListener {
                        Log.d("Y","Documento Borrado")
                    }
                    .addOnFailureListener {
                        Log.d("Y",it.message.toString())
                    }
            }
            btnUpdate.setOnClickListener {

                var doc=db.collection("Alumnos").document("Javascript_Experto1")

                db.runTransaction {

                    it.update(doc,"nombres","Ricardo","apellidos","Flores","nombres","aaaaaaaaaaaa")

                }.addOnFailureListener {
                    Log.d("Y",it.message.toString())
                }
            }
*/



        }
    }

}