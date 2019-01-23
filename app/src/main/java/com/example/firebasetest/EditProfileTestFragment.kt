package com.example.firebasetest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import com.example.utils.extensions.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile_test.*
import kotlinx.android.synthetic.main.fragment_edit_profile_test.*

class EditProfileTestFragment : Fragment() {

    private val dbRootReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        btnSaveChangesTest.setOnClickListener {

            val user = UserDetails(
                etEditUserNameTest.text.toString(),
                etEditPhoneNumber.text.toString()
            )

            dbRootReference.child(uid.toString()).let {

                    it.child("name")
                    .setValue(user.name)
                    .addOnCompleteListener { task ->
                        activity?.toast(
                            if (task.isSuccessful)
                                "Successful" else "Unsuccessful"
                        )
                    }

                it.child("number")
                    .setValue(user.number)
                    .addOnCompleteListener {task ->
                        activity?.toast(
                        if (task.isSuccessful)
                            "Successful" else "Unsuccessful"
                        )
                    }
            }
            fragmentManager?.popBackStack()
        }

        activity?.run {
            etEditUserNameTest.text = SpannableStringBuilder(tvUserName.text.toString())
            etEditPhoneNumber.text = SpannableStringBuilder(tvContactNumber.text.toString())
        }
    }
}

data class UserDetails(var name: String = "", var number: String = "")
