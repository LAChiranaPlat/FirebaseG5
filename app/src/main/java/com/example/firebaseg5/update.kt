package com.example.firebaseg5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseg5.databinding.ActivityUpdateBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class update : AppCompatActivity() {

    lateinit var layout:ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout= ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(layout.root)

        var nombres=intent.getStringExtra("name")
        var apellidos=intent.getStringExtra("lname")
        var id=intent.getStringExtra("doc")
        var course=intent.getStringExtra("curso")

        layout.tilNewName.editText?.setText(nombres.toString())

        val db=Firebase.firestore

        layout.btnModify.setOnClickListener {

            var doc=db.collection("Student").document(id.toString())

            db.runTransaction {

                it.update(doc,"nombres",layout.tilNewName.editText?.text.toString(),"apellidos","----")

            }.addOnSuccessListener {
                startActivity(Intent(this,MainActivity::class.java))
            }

        }

    }
}