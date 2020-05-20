package com.edifica.fragments.clients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.adapters.TransactionsClientAdapter
import com.edifica.interfaces.TransactionListener
import com.edifica.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Transaction
import kotlinx.android.synthetic.main.fragment_client_budgets.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientBudgets : Fragment(), TransactionListener {

    var transactions: ArrayList<Transactions> = arrayListOf()
    var db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()
    val TAG = "miApp"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View =
            inflater.inflate(R.layout.fragment_client_budgets, container, false)

        return rootView

    }

    fun loadAdapter() {
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recycler_client_budgets)
        val mAdapter = TransactionsClientAdapter(transactions, this)

        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = mAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactions = loadTransactionsfromDataBase()
        db.collection("transaction")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var ads: Ads? = null
                    var user: User? = null
                    var business: User? = null
                    var transaction: Transactions? = document.toObject(Transactions::class.java)

                    var documentReference =
                        (document["ads"] as DocumentReference).get().addOnCompleteListener {
                            ads = it.result?.toObject(Ads::class.java)
                            var documentReferenceTwo =
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
                                                    transactions.add(transaction!!)
                                                    loadAdapter()
                                                }
                                        }
                                    }
                        }
                }
            }
    }

    override fun onItemClick(Transaction: Transactions, position: Int) {
    }

    private fun loadTransactionsfromDataBase(): ArrayList<Transactions> {
        val query = db.collection("transaction")
        var dbAllAds: ArrayList<Transactions> = arrayListOf()
        val token: Token = Token.readToken(File(context?.filesDir, Dataholder.FILENAME))

        db.collection("transaction")
            .whereEqualTo("user", token.identifier)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        return dbAllAds
    }

}
