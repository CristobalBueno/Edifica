package com.edifica.interfaces

import com.edifica.models.Transactions

interface TransactionListener {
    fun onItemClick(Transaction: Transactions, position: Int)
}

