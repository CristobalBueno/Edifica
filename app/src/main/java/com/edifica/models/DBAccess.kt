package com.edifica.models

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DBAccess {

    companion object {
        private lateinit var db: FirebaseFirestore
        private lateinit var auth: FirebaseAuth
        val PHONE = "phone"

        fun saveUser(dbUser: User) {
            db = FirebaseFirestore.getInstance()
            auth = FirebaseAuth.getInstance()

            db.collection("users").document(auth.currentUser?.uid!!)
                .set(dbUser)
                .addOnSuccessListener { documentReference ->
                    Log.d("debug", "Entrada correcta")
                }
                .addOnFailureListener { e ->
                    Log.e("debug", "Fallo al introducir datos")
                }

        }

        fun loadUser(): User? {
            db = FirebaseFirestore.getInstance()
            auth = FirebaseAuth.getInstance()

            val query = db.collection("users").document(auth.currentUser?.uid!!)
            var user: User? = null

            query.get().addOnSuccessListener { document ->
                user = document.toObject(User::class.java)
                Log.d("debug",user.toString())
                Log.d("debug","success updating data")
            }.addOnFailureListener { exception ->
                Log.d("debug","get failed with", exception)
            }
            return user
        }
    }

}