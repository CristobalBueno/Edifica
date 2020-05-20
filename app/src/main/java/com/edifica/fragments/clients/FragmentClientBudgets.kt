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
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.Transactions
import com.edifica.models.User
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
    val TAG = "miApp"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View =
            inflater.inflate(R.layout.fragment_client_budgets, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_client_budgets)

        val mAdapter = TransactionsClientAdapter(transactions, this)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mAdapter

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactions = loadTransactionsfromDataBase()
        db.collection("transaction")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    override fun onItemClick(Transaction: Transactions, position: Int) {
    }

    private fun loadTransactionsfromDataBase(): ArrayList<Transactions> {
        val query = db.collection("transaction")
        var dbAllAds: ArrayList<Transactions> = arrayListOf()
        val token: Token = Token.readToken(File(context?.filesDir ,Dataholder.FILENAME))

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
