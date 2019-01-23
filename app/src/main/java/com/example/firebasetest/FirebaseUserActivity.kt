package com.example.firebasetest

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import com.example.R
import com.example.utils.extensions.toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_firebase_user.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FirebaseUserActivity : AppCompatActivity() {

    private lateinit var sentCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_user)

        btnFbUserRegister.setOnClickListener {
            Timber.d("onCreate: ${etFbUserNumber.text.toString()}")

            val input = EditText(this)
            input.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            input.inputType = InputType.TYPE_CLASS_NUMBER

            AlertDialog.Builder(this).run {
                setTitle("Verification")
                setMessage("Enter the OTP")
                setPositiveButton("Verify") { _, _ ->
                    signInWithPhoneAuthCredential(
                        PhoneAuthProvider.getCredential(
                            sentCode,
                            input.text.toString()
                        )
                    )
                }
                setView(input)
                show()
            }


            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                etFbUserNumber.text.toString(),
                60,
                TimeUnit.SECONDS,
                this,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential?) {

                    }

                    override fun onVerificationFailed(exception: FirebaseException?) {

                    }

                    override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                        sentCode = verificationId.toString()
                    }

                })


        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    toast("Successful")
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        toast("Invalid Code")
                    }
                }
            }
    }


}
