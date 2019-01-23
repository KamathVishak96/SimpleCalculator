package com.example.firebasetest

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import timber.log.Timber

@Suppress("DEPRECATION")
class FirebaseInstanceService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {

        val refreshedToken = FirebaseInstanceId.getInstance().token

        Timber.d("onTokenRefresh: $refreshedToken")

    }
}
