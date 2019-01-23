package com.example

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Toast
import com.fondesa.kpermissions.extension.permissionsBuilder
import kotlinx.android.synthetic.main.activity_content_provider.*
import kotlinx.android.synthetic.main.fragment_poster_dialog.*
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class ContentProviderActivity : AppCompatActivity() {

    private val mProjection: Array<String> = arrayOf(
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        ContactsContract.Contacts.HAS_PHONE_NUMBER,
        ContactsContract.Contacts.CONTACT_STATUS
    )
    private var mSelectionClause: String? = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE ?"
    private val mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            val request = permissionsBuilder(Manifest.permission.CAMERA)
            request.build().send()
        } else {

            btnOpenCamera.setOnClickListener {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {

                        val photoFile: File? = try {
                            createImageFile()
                        } catch(e: IOException){
                            null
                        }

                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "com.example.simplecalculator.fileprovider",
                                it
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, 1)
                        }

                    }
                }
            }
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            val request = permissionsBuilder(Manifest.permission.WRITE_CONTACTS)
            request.build().send()
        } else {
            val mNewValues = ContentValues().apply {
                put(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, "Vishak")
            }
        }


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            val request = permissionsBuilder(Manifest.permission.READ_CONTACTS)
            request.build().send()
        } else {
            val mCursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                mProjection,
                mSelectionClause,
                arrayOf("%A%"),
                mOrderBy
            )

            when (mCursor?.count) {
                null -> {
                    Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show()
                }
                0 -> {
                    tvContent.text = "NO CONTACTS"
                    Toast.makeText(this, "UNSUCCESSFUL", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    while (mCursor.moveToNext()) {
                        tvContent.text = tvContent.text.toString() + mCursor.getString(0) + "\t\t\t" +
                                mCursor.getString(2) + "\t\t\t" + mCursor.getString(2) + "\n"
                    }
                }
            }
            mCursor?.close()
        }

        btnSelectFile.setOnClickListener {
            Timber.d("onCreate: ${startActivityForResult((Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }), 42)}")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 42)
                data?.data?.also { uri ->
                    Timber.d("onActivityResult: URI: $uri")
                }
            else if (requestCode == 1)
                ivDialogImage.setImageBitmap(data?.extras?.get("data") as Bitmap)
        }
        btnOpenFile.setOnClickListener {
            with(Dialog(this)) {
                window?.requestFeature(Window.FEATURE_NO_TITLE)
                setContentView(layoutInflater.inflate(R.layout.fragment_poster_dialog, null))
                ivDialogImage.setImageURI(data?.data)
                show()
            }
        }

        btnDeleteFile.setOnClickListener {

            if (data?.data != null)

                AlertDialog.Builder(this)
                    .setMessage("Do you want to delete the file?")
                    .setPositiveButton(
                        "Yes"
                    ) { _, _ ->
                        DocumentsContract.deleteDocument(contentResolver, data.data)
                        Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("NO") { _, _ ->
                        Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show()
                    }
                    .create()
                    .show()
        }
    }

    private lateinit var mCurrentPhotoPath: String


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES) as File
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }
}