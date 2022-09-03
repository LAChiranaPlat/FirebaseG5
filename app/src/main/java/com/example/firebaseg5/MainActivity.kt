package com.example.firebaseg5

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        //INTEGRACIÓN A FIREBASE

        val db = Firebase.firestore

        layout.apply {

            btnSave.setOnClickListener {
                // Create a new user with a first and last name
                val user = hashMapOf(
                    "nombres" to tilName.editText?.text.toString(),
                    "apellidos" to tilLName.editText?.text.toString(),
                    "curso" to "LAChirana Plat 2022"
                )

                //Añadir Regsitro

               /* db.collection("Student")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d("x", "DocumentSnapshot added with ID: ${documentReference.id}")
                        tilName.editText?.text?.clear()
                        tilLName.editText?.text?.clear()
                        tilName.editText?.requestFocus()
                    }
                    .addOnFailureListener { e ->
                        Log.w("x", "Error adding document", e)
                    }*/

                val coleccion=db.collection("ADK")
                coleccion.document("Javascript_Experto").set(user)
                    .addOnSuccessListener {
                        tilLName.requestFocus()
                        tilName.editText?.text!!.clear()
                        tilLName.editText?.text!!.clear()
                    }.addOnFailureListener { e ->
                        Log.w("x", "Error adding document", e)


                }



            }
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

        }
    }

}