package com.edifica.interfaces

import com.edifica.models.Transactions

interface TransactionListener {
    fun acceptOnItemClick(transaction: Transactions, position: Int)
    fun cancelOnItemClick(transaction: Transactions, position: Int)
    fun chatOnItemClick(transaction: Transactions, position: Int)
}

