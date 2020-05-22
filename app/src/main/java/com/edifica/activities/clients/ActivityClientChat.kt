package com.edifica.activities.clients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.adapters.ChatAdapter
import com.edifica.models.Chat
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_client_chat.*
import java.io.File

class ActivityClientChat : AppCompatActivity() {

    private var chat: Chat? = null
    var db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()
    lateinit var user: String

    lateinit var business: String
    lateinit var client: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_chat)

        submit_button.setOnClickListener {
            val data = hashMapOf(
                "text" to chat_text_input.editText?.text.toString(),
                "user" to auth.currentUser?.uid.toString()
            )

            chat?.messages?.add(data)
            saveCurrentState()

            loadAdapter()
        }
    }

    override fun onStart() {
        super.onStart()

        user = intent.getStringExtra("user")

        loadChat()
    }

    fun loadAdapter() {
        var recyclerView = findViewById<RecyclerView>(R.id.chat_recycler_view)

        val mAdapter = ChatAdapter(chat)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mAdapter
    }

    fun loadChat() {
        val token = Token.readToken(File(filesDir, Dataholder.FILENAME))

        if (token.identifier == 0) {
            client = token.uid
            business = user
        } else {
            client = user
            business = token.uid
        }

        val dbAccess: DocumentReference = db.collection("chat").document(client)

        // If this item exist, we will get it, if not, we will create one
        dbAccess.get().addOnSuccessListener { document ->
            if (document["user"] != null) {
                chat = document.toObject(Chat::class.java)

                (document["user"] as DocumentReference).get()
                    .addOnCompleteListener { user_reference ->
                        chat?.user = user_reference.result?.toObject(User::class.java)!!

                        (document["business"] as DocumentReference).get().addOnCompleteListener {
                            chat?.business = it.result?.toObject(User::class.java)!!
                            if (chat != null) {
                                Log.d("debug", chat.toString())
                                loadAdapter()
                            }
                        }
                    }
            } else {
                chat = Chat()
                val data = hashMapOf(
                    "business" to db.document("users/$business"),
                    "user" to db.document("users/$client"),
                    "messages" to ArrayList<Map<String, Any?>>()
                )

                db.collection("chat").document(client).set(data)
                loadChat()
            }
        }
    }

    fun saveCurrentState() {
        db.collection("chat").document(client).update("messages", chat?.messages)
    }
}
