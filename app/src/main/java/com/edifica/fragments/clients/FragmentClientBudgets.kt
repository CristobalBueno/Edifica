package com.edifica.fragments.clients

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.activities.clients.ActivityClientChat
import com.edifica.activities.clients.ActivityClientFormulary
import com.edifica.adapters.TransactionsClientAdapter
import com.edifica.interfaces.TransactionListener
import com.edifica.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientBudgets : Fragment(), TransactionListener {

    var transactions: ArrayList<Transactions> = arrayListOf()
    var db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()
    val TAG = "miApp"
    var recyclerView: RecyclerView? = null
    lateinit var mAdapter: RecyclerView.Adapter<TransactionsClientAdapter.MainViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View =
            inflater.inflate(R.layout.fragment_client_budgets, container, false)

        return rootView
    }

    fun loadAdapter() {
        recyclerView = activity?.findViewById<RecyclerView>(R.id.recycler_client_budgets)
        mAdapter = TransactionsClientAdapter(transactions, this)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = mAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db.collection("transaction")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var ads: Ads? = null
                    var user: User? = null
                    var business: User? = null
                    var transaction: Transactions? = document.toObject(Transactions::class.java)
                    Log.d("Transaction", "${transaction.toString()}")

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


    override fun acceptOnItemClick(transaction: Transactions, position: Int) {
        transaction.isAccepted = true

        db.collection("transaction").document(transaction.id).update(
            "isAccepted", true
        )

        loadAdapter()
    }

    override fun cancelOnItemClick(transaction: Transactions, position: Int) {
        db.collection("transaction").document(transaction.id).delete()

        transactions.remove(transaction)

        loadAdapter()
    }

    override fun chatOnItemClick(transaction: Transactions, position: Int) {
        val intent = Intent(context, ActivityClientChat::class.java)
        intent.putExtra("user", transaction.userBusiness.uid)
        startActivity(intent)
    }

}
