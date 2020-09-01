package com.withey89.saltgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.SecureRandom

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        button.setOnClickListener {

            val size = edit_text_salt_size.text.toString().toInt()
            val password: String = edit_text_input_password.text.toString()
            val salt = generateSalt(size)
            val saltedPassword = getSHA(salt + password)

            text_view_password_salted.text = String.format(getString(R.string.password_salted), password)
            text_view_salt.text = String.format(getString(R.string.salt), salt)
            text_view_salted_password.text = String.format(getString(R.string.salted_password), saltedPassword)

            Log.d(TAG, "onCreate: salt = $salt")
            Log.d(TAG, "onCreate: salted password = $saltedPassword")
            Log.d(TAG, "onCreate: password = $password")



        }

    }

    private fun getSHA(input: String): String {
        val md = MessageDigest.getInstance("SHA-256");
        val hash = md.digest(input.toByteArray(Charsets.UTF_8))
        return hash.joinToString(separator = "") { "%02x".format(it) }
    }

    private fun generateSalt(size: Int): String {
        val random = SecureRandom()
        val bytes = ByteArray(size)
        random.nextBytes(bytes)
        return bytes.joinToString(separator = "") { "%02x".format(it) }
    }
}