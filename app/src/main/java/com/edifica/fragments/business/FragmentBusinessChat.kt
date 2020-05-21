package com.edifica.fragments.business

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.adapters.TransactionsBusinessAdapter
import com.edifica.models.Ads
import com.edifica.models.Transactions
import com.edifica.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class FragmentBusinessChat : Fragment() {

    var transactions: ArrayList<Transactions> = arrayListOf()
    var db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_business_chat, container, false)
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

                    if (transaction?.isAccepted == true) {
                        (document["business"] as DocumentReference).get()
                            .addOnCompleteListener {
                                business =
                                    it.result?.toObject(User::class.java)

                                if (business?.uid == auth.currentUser?.uid) {
                                    var documentReference =
                                        (document["ads"] as DocumentReference).get()
                                            .addOnCompleteListener {
                                                ads = it.result?.toObject(Ads::class.java)
                                                var documentReferenceTwo =
                                                    it.result?.getDocumentReference("user")?.get()
                                                        ?.addOnCompleteListener {
                                                            user =
                                                                it.result?.toObject(User::class.java)

                                                            ads?.user = user!!

                                                            transaction?.userBusiness = business!!
                                                            transaction?.ad = ads!!
                                                            Log.d(
                                                                "Transaction",
                                                                transaction.toString()
                                                            )
                                                            transactions.add(transaction!!)
                                                            loadAdapter()
                                                        }
                                            }
                                }
                            }

                    }

                }

            }
    }

    fun loadAdapter() {
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recycler_Business_Chat)
        val mAdapter = TransactionsBusinessAdapter(transactions, this)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = mAdapter
    }

}
