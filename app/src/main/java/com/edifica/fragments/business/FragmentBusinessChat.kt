package com.edifica.fragments.business

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.activities.business.ActivityBusinessChat
import com.edifica.activities.business.ActivityBusinessMain
import com.edifica.activities.clients.ActivityClientChat
import com.edifica.adapters.TransactionsBusinessAdapter
import com.edifica.interfaces.TransactionListener
import com.edifica.models.Ads
import com.edifica.models.Transactions
import com.edifica.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class FragmentBusinessChat : Fragment() , TransactionListener {

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

    private fun loadAdapter() {
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recycler_Business_Chat)
        val mAdapter = TransactionsBusinessAdapter(transactions, this)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = mAdapter
    }

    override fun acceptOnItemClick(transaction: Transactions, position: Int) {}

    override fun cancelOnItemClick(transaction: Transactions, position: Int) {

        val dialogBuilder = MaterialAlertDialogBuilder(context, R.style.MyMaterialAlertDialog)

        dialogBuilder.setMessage(R.string.fragment_business_profile_chat_question_delete_alert_dialog)
            .setPositiveButton(R.string.fragment_business_profile_chat_affirmative_delete_alert_dialog) { _, _ ->
                db.collection("transaction").document(transaction.id).delete()

                transactions.remove(transaction)

                loadAdapter()
            }
            .setNegativeButton(R.string.fragment_business_profile_chat_negative_delete_alert_dialog) { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()

        alert.setTitle(R.string.fragment_business_profile_chat_action_delete_alert_dialog)
        alert.show()

    }

    override fun chatOnItemClick(transaction: Transactions, position: Int) {
        val intent = Intent(context, ActivityClientChat::class.java)
        intent.putExtra("user", transaction.ad.user.uid)
        startActivity(intent)
    }

}
