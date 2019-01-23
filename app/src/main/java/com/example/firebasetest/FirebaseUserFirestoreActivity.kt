package com.example.firebasetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import com.example.utils.extensions.toast
import com.google.firebase.firestore.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_firebase_user_firestore.*

class FirebaseUserFirestoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_user_firestore)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        FirebaseFirestore.getInstance().firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()


        FirebaseFirestore.getInstance().collection("Message").document(etFolderNameToDelete.text.toString())
            .addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
                if (e != null) {

                    return@EventListener
                }

                if (snapshot != null && snapshot.exists()) {
                    tvFirebaseDocDetails.text = snapshot.data.toString()
                } else {

                }
            })

        btnFirestoreSaveMessage.setOnClickListener {
            save()
        }

        btnDeleteDocFirestore.setOnClickListener {
            FirebaseFirestore.getInstance().collection("Messages").document(etFolderNameToDelete.text.toString())
                .delete()
        }

        btnGetData.setOnClickListener {
            FirebaseFirestore.getInstance().collection("Messages").document(etFolderNameToDelete.text.toString()).get()
                .addOnSuccessListener {
                    tvFirebaseDocDetails.text = it.data.toString()
                }
                .addOnFailureListener {

                }
        }

        btnGetDataCache.setOnClickListener {
            FirebaseFirestore.getInstance().collection("Messages").document(etFolderNameToDelete.text.toString())
                .get(Source.CACHE)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        tvFirebaseDocDetails.text = it.result?.data.toString()
                    } else {

                    }
                }

        }


    }

    private fun save() {
        val map = HashMap<String, Any>()

        map[MSG1_KEY] = etFirestoreMsgOne.text.toString()
        map[MSG2_KEY] = etFirestoreMsgTwo.text.toString()

        FirebaseFirestore.getInstance().collection("Messages")
            .document(etFirestoreDocName.text.toString())
            .set(map)
            .addOnSuccessListener {
                toast("Success")
            }
            .addOnFailureListener {
                toast("Failed")
            }

    }


    companion object {
        const val MSG1_KEY = "Message 1"
        const val MSG2_KEY = "Message 2"
    }
}
