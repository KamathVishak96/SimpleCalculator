package com.example.firebasetest

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_firebase_login_test.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


class FirebaseLoginTestFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_firebase_login_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLoginFirebaseTest.setOnClickListener {
            loginUser()
        }

        tvIfLoginRegister.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flFirebaseTest, FirebaseRegisterTestFragment())
                ?.commit()
        }

        btnSignInWithLink.setOnClickListener {
            AlertDialog.Builder(context).run {
                setTitle("Passwordless Sign In")

                val input = EditText(context)
                input.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                setPositiveButton("Send Verificatin Mail") { _, _ ->

                    FirebaseAuth.getInstance().sendSignInLinkToEmail(
                        input.text.toString(), ActionCodeSettings.newBuilder()
                            .setAndroidPackageName("com.example.simplecalculator", true, "22")
                            //.setUrl()
                            .build()
                    )
                        .addOnCompleteListener { it ->
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Main sent", Toast.LENGTH_SHORT).show()
                            }
                        }


                }
                setView(input)
                show()
            }
        }

        btnSignInWithGoogle.setOnClickListener {
            startActivityForResult(
                GoogleSignIn.getClient(
                    context!!, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("494183608063-d6m4i763u15i64jgidi3atmrqj6l1ev0.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                ).signInIntent, 101
            )
        }

        btnSignInWithPhone.setOnClickListener {
            AlertDialog.Builder(context).run {
                setTitle("Enter Phone number")

                val inputNumber = EditText(context)
                inputNumber.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                inputNumber.inputType = InputType.TYPE_CLASS_PHONE
                setPositiveButton("Send OTP") { _, _ ->

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        inputNumber.text.toString(),
                        60,
                        TimeUnit.SECONDS,
                        activity!!,
                        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
                                Timber.d("onVerificationCompleted")
                            }

                            override fun onVerificationFailed(exception: FirebaseException?) {

                            }

                            override fun onCodeSent(
                                verificationId: String?,
                                token: PhoneAuthProvider.ForceResendingToken?
                            ) {
                                super.onCodeSent(verificationId, token)
                                Timber.d("onCodeSent: $verificationId")
                            }


                        }
                    )
                }
                setView(inputNumber)
                show()
            }
        }

        tvForgotPassword.setOnClickListener {

            AlertDialog.Builder(context).run {
                setTitle("Reset Password")

                val input = EditText(context)
                input.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                setPositiveButton("Send Reset Mail") { _, _ ->
                    FirebaseAuth.getInstance()
                        .sendPasswordResetEmail(input.text.toString())
                        .addOnCompleteListener { it ->
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Reset Mail sent Successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Invalid Input or Account Does not exist",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                }
                setView(input)
                show()

            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {

            }
        }
    }


    private fun loginUser() {

        val email = etEmailForLogin.text.toString()
        val pwd = etPasswordForLogin.text.toString()

        if (email.isEmpty() || pwd.isEmpty()) {
            return
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (!FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                            FirebaseAuth.getInstance().signOut()
                            AlertDialog.Builder(context)
                                .setMessage("Email not Verified.")
                                .show()
                            return@addOnCompleteListener
                        }
                        if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                            activity?.finish()

                            Toast.makeText(context, "Successfully Logged in", Toast.LENGTH_SHORT).show()
                            startActivity(
                                Intent(context, ProfileTestActivity::class.java).putExtra(
                                    "profile",
                                    "hello"
                                )
                            )
                        } else {
                            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {


        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    setPassword()


                } else {
                    Toast.makeText(activity?.applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setPassword() {
        AlertDialog.Builder(context).run {
            setTitle("Set Password")

            val password = EditText(context)
            password.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

            setPositiveButton("Confirm") { _, _ ->
                FirebaseAuth.getInstance().currentUser
                    ?.updatePassword(password.text.toString())
                    ?.addOnCompleteListener {
                        if (it.isSuccessful)
                            startActivity(
                                Intent(
                                    activity?.applicationContext,
                                    ProfileTestActivity::class.java
                                )
                            )
                        else setPassword()
                    }
            }
            setView(password)
            show()

        }.setOnDismissListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                setPassword()
            }
        }
    }
}