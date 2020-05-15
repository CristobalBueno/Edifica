package com.edifica.models

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import java.io.File

class Token(val name: String, val phone: String, val email: String, val identifier: Int) {

    private val FILENAME = "token"
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private val fileToWrite = FILENAME

    fun saveToken(context : Context) {
        val encryptedFile = encryptedFile(context)

        encryptedFile.openFileOutput().use { outputStream ->
            // Write data to your encrypted file
        }
    }

    fun readToken(context : Context) {
        val encryptedFile = encryptedFile(context)

            encryptedFile.openFileInput().use { inputStream ->
                // Read data from your encrypted file
            }
    }

    private fun encryptedFile(context : Context) : EncryptedFile {
        val file = File(context?.filesDir, FILENAME)

        val fileToWrite = FILENAME
        val encryptedFile = EncryptedFile.Builder(
            File(file, fileToWrite),
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        return encryptedFile
    }
}
