package com.edifica.fragments.clients

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.activities.clients.ActivityClientChat
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.adapters.TransactionsClientAdapter
import com.edifica.interfaces.TransactionListener
import com.edifica.models.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientBudgets : Fragment(), TransactionListener {

    private lateinit var activityMain: ActivityClientMain
    private lateinit var transactions: ArrayList<Transactions>

    private var recyclerView: RecyclerView? = null
    private lateinit var mAdapter: RecyclerView.Adapter<TransactionsClientAdapter.MainViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activityMain = activity as ActivityClientMain
        transactions = activityMain.transactions

        return inflater.inflate(R.layout.fragment_client_budgets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun acceptOnItemClick(transaction: Transactions, position: Int) {
        transaction.isAccepted = true

        activityMain.transactionUpdate(transaction)

        loadAdapter()
    }

    override fun cancelOnItemClick(transaction: Transactions, position: Int) {

        val dialogBuilder = MaterialAlertDialogBuilder(context, R.style.MyMaterialAlertDialog)

        dialogBuilder.setMessage(R.string.fragment_client_profile_chat_question_delete_alert_dialog)
            .setPositiveButton(R.string.fragment_client_profile_chat_affirmative_delete_alert_dialog) { _, _ ->
                transactionDelete(transaction)
            }
            .setNegativeButton(R.string.fragment_client_profile_chat_negative_delete_alert_dialog) { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()

        alert.setTitle(R.string.fragment_client_profile_chat_action_delete_alert_dialog)
        alert.show()
    }

    override fun chatOnItemClick(transaction: Transactions, position: Int) {
        val intent = Intent(context, ActivityClientChat::class.java)
        intent.putExtra("user", transaction.userBusiness.uid)
        startActivity(intent)
    }

    private fun transactionDelete(transaction: Transactions) {
        activityMain.transactionDelete(transaction)

        transactions.remove(transaction)

        loadAdapter()
    }

    private fun loadAdapter() {
        recyclerView = activity?.findViewById<RecyclerView>(R.id.recycler_client_budgets)
        mAdapter = TransactionsClientAdapter(transactions, this)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = mAdapter
    }

}
