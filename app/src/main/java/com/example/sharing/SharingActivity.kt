package com.example.sharing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import com.example.R
import com.example.utils.extensions.toast
import kotlinx.android.synthetic.main.activity_sharing.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec


class SharingActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)

        videoView2.setOnClickListener {
            videoView2.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=GDkL2uEn8cg"))
            videoView2.start()
        }

        /*btnShareTest.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }
            startActivity(sendIntent)
        }*/

        var encryptedString: ByteArray

        var decryptedString: ByteArray
        btnShareTest.setOnClickListener {

            KeyGenerator.getInstance("AES").apply {
                init(256)
                val key = this.generateKey()
                Cipher.getInstance("AES/CBC/PKCS5PADDING").let {
                    it.init(Cipher.ENCRYPT_MODE, key)
                    encryptedString =
                        it.doFinal(etPlainText.text.toString().toByteArray())
                    it.iv
                    //toast(Base64.encodeBase64String(encryptedString))
                }
                Cipher.getInstance("AES/CBC/PKCS5PADDING").let{

                    it.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(key.encoded))
                    decryptedString = it.doFinal(encryptedString)
                    toast(decryptedString.toString())
                }
            }
        }



        btnAddContact.setOnClickListener {
            Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
            }.also { intent ->
                // Make sure that the user has a contacts app installed on their device.
                intent.resolveActivity(packageManager)?.run {
                    startActivity(intent)
                }
            }
        }

    }
}


