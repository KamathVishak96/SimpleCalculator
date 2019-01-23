package com.example.firebasetest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.MimeTypeMap
import com.bumptech.glide.Glide
import com.example.R
import com.example.utils.extensions.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile_test.*
import timber.log.Timber

class ProfileTestActivity : AppCompatActivity() {

    private val dbRootReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }
    private val dbNameReference by lazy {
        dbRootReference.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("name")
    }
    private val dbNumberReference by lazy {
        dbRootReference.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("number")
    }

    private val dbFileNameReference by lazy {
        dbRootReference.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("profilePicName")

    }

    private val dbUrlReference by lazy {
        dbRootReference.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("profilePicUrl")

    }
    private val storageReference by lazy {
        FirebaseStorage.getInstance().getReference("uploads")
    }
    private var fileName: String? = null
    var downloadedUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_test)

        tvUserMailID.text = FirebaseAuth.getInstance().currentUser?.email.toString()

        ivSetImage.setOnClickListener {
            startActivityForResult(
                Intent().setType("image/*")
                    .setAction(Intent.ACTION_GET_CONTENT), PICK_IMAGE_REQUEST
            )
        }

        btnLogoutTest.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            finish()
            startActivity(Intent(this, FirebaseTestActivity::class.java).putExtra("firebase", "hello"))

        }

        btnEditProfileTest.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.flUserDetailsTest, EditProfileTestFragment())
                .addToBackStack(null)
                .commit()
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user?.displayName == null || user.phoneNumber == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flUserDetailsTest, EditProfileTestFragment())
                .addToBackStack(null)
                .commit()
        } else {
            tvEmailId.text = user.email.toString()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            uploadFile(data.data)
        }
    }

    override fun onStart() {
        super.onStart()

        dbNameReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.toException(), "onCancelled: ")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                tvUserName.text = snapshot.getValue(String::class.java)
            }

        })

        dbNumberReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.toException(), "onCancelled: ")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                tvContactNumber.text = snapshot.getValue(String::class.java)
            }

        })



        dbUrlReference.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.toException(), "onCancelled: ")
            }

            override fun onDataChange(snapShot: DataSnapshot) {

                Glide.with(this@ProfileTestActivity)
                    .load(downloadedUrl)
                    .into(ivSetImage)
            }
        })



        storageReference.child(fileName.toString()).downloadUrl.addOnSuccessListener {
            downloadedUrl = it.toString()
            Timber.d("onStart: $downloadedUrl")
        }


    }

    private fun uploadFile(imageUri: Uri?) {

        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(imageUri))
        if (imageUri != null) {

            fileName = "${System.currentTimeMillis()}.$extension"


            val reference = storageReference.child(fileName.toString())
            val uploadTask = reference.putFile(imageUri)
            uploadTask.addOnSuccessListener {
                FirebaseAuth.getInstance().currentUser?.uid?.run {

                    dbRootReference.child(this).let { root ->
                        root.child("profilePicName")
                            .setValue(fileName.toString())
                            .addOnSuccessListener {
                                storageReference.child(fileName.toString()).downloadUrl.addOnSuccessListener {
                                    downloadedUrl = it.toString()
                                    root.child("profilePicUrl")
                                        .setValue(downloadedUrl)
                                        .addOnSuccessListener {
                                            toast("Successful")
                                            Glide.with(this@ProfileTestActivity)
                                                .load(downloadedUrl)
                                                .into(ivSetImage)
                                        }
                                        .addOnFailureListener{
                                            toast("Failed")
                                        }
                                }
                            }
                            .addOnFailureListener {
                                toast("upload Failed")
                            }

                    }
                }
            }
            uploadTask.addOnFailureListener {
                toast("Failed")
            }


            /*val reference = storageReference.child(fileName)
            val uploadTask = reference.putFile(imageUri)

            uploadTask.addOnSuccessListener {

                Timber.i("uploadFile: downloadUrl: ${reference.downloadUrl}")
                FirebaseAuth.getInstance().currentUser?.uid?.run {

                    dbRootReference.child(this).let {
                        it.child("profilePicUri")
                            .setValue(imageUri.toString())
                            .addOnSuccessListener {
                                toast("Profile Picture changed Successfully")
                            }

                        it.child("profilePicUrl")
                            .setValue(reference.downloadUrl.toString())
                            .addOnCompleteListener {
                                if (it.isSuccessful)
                                    toast("SUccesful")
                                else
                                    toast("not successful")
                            }
                    }
                }
            }.addOnProgressListener {
                pbUploadStatus.progress = (((100.0 * it.bytesTransferred) / it.totalByteCount).toInt())
            }*/
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 101
    }

}
