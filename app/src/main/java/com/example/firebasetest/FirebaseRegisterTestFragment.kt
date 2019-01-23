package com.example.firebasetest

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_firebase_register_test.*
import kotlinx.android.synthetic.main.fragment_firebase_register_test.view.*

class FirebaseRegisterTestFragment : Fragment() {

    private lateinit var fireBaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fireBaseAuth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_firebase_register_test, container, false)

        rootView.btnRegisterFirebaseTest.setOnClickListener {
            registerUser()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tvIfRegSignIn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flFirebaseTest, FirebaseLoginTestFragment())?.commit()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun registerUser() {
        val email = etEmailForSignup.text.toString()
        val pwd = etPasswordForSignup.text.toString()

        if (pwd.isEmpty() || email.isEmpty()) {
            Toast.makeText(context, "Email or Password is empty", Toast.LENGTH_SHORT).show()
        } else
            fireBaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener {
                if (it.isSuccessful) {

                    fireBaseAuth.currentUser?.run {
                        sendEmailVerification().addOnCompleteListener {
                            AlertDialog.Builder(context)
                                .setMessage("Verify your email and Login")
                                .show()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.flFirebaseTest, FirebaseLoginTestFragment())?.commit()
                        }
                    }

                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkEmailExistence(email: String) {

    }
}