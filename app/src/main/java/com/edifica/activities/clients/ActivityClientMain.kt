package com.edifica.activities.clients

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.io.File
import java.util.*

class ActivityClientMain : BaseActivity() {

    var db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()

    var guilds = arrayOf<Guild>()

    lateinit var userToken: Token

    val businessUsers = loadAllBusinessDataBase()
    val transactions = loadAllTransactions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        guilds = generateGuilds()
        userToken = Token.readToken(File(this.filesDir, Dataholder.FILENAME))

        val navView: BottomNavigationView = findViewById(R.id.nav_client)

        val navController: NavController = findNavController(R.id.nav_client_fragment)

        NavigationUI.setupWithNavController(navView, navController)
    }

    private fun generateGuilds(): Array<Guild> {
        var guildsGenerated: Array<Guild> = arrayOf()

        for (guild in enumValues<Guild.GuildName>().iterator()) {
            val integer =
                resources.getIdentifier(guild.image.toLowerCase(Locale.ROOT), "drawable", this.packageName)
            val image = resources.getDrawable(integer)

            var guildGenerated = Guild(guild.guildName, image, false)
            guildsGenerated += guildGenerated
        }

        return guildsGenerated
    }

    private fun loadAllBusinessDataBase(): FirestoreRecyclerOptions.Builder<User> {
        val query = db.collection("users").orderBy("identifier", Query.Direction.ASCENDING)

        return FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java)
    }

    private fun loadAllTransactions(): ArrayList<Transactions> {
        val transactionsGenerated = arrayListOf<Transactions>()

        db.collection("transaction")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var ads: Ads?
                    var user: User?
                    var business: User?

                    var transaction: Transactions? = document.toObject(Transactions::class.java)
                    Log.d("Transaction", transaction.toString())

                    (document["ads"] as DocumentReference).get().addOnCompleteListener {
                        ads = it.result?.toObject(Ads::class.java)
                        it.result?.getDocumentReference("user")?.get()
                            ?.addOnCompleteListener {
                                user = it.result?.toObject(User::class.java)

                                if (user?.uid == auth.currentUser?.uid) {
                                    (document["business"] as DocumentReference).get()
                                        .addOnCompleteListener {
                                            business = it.result?.toObject(User::class.java)

                                            ads?.user = user!!
                                            Log.d("Transaction", "$ads => $business")

                                            transaction?.userBusiness = business!!
                                            transaction?.ad = ads!!
                                            Log.d("Transaction", transaction.toString())
                                            transactionsGenerated.add(transaction!!)
                                        }
                                }
                            }
                    }
                }
            }
        return transactionsGenerated
    }

    fun transactionDelete(transaction: Transactions) {
        db.collection("transaction").document(transaction.id).delete()
    }

    fun transactionUpdate(transaction: Transactions) {
        db.collection("transaction").document(transaction.id).update(
            "isAccepted", true
        )
    }
}