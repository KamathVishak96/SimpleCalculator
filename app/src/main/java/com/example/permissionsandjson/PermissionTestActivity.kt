package com.example.permissionsandjson

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import com.fondesa.kpermissions.extension.listeners
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_permission_test.*
import timber.log.Timber

class PermissionTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_test)

        btnRequestPermission.setOnClickListener { _ ->
            permissionsBuilder(Manifest.permission.WRITE_EXTERNAL_STORAGE).build().apply {
                listeners {

                    onAccepted {

                    }

                    onDenied {

                    }
                }
            }.run {
                send()
            }

        }

        Timber.d("onCreate: onCreateActivity")

        val jsonString = """{
               "hiddenCard": {
                "rank": "6",
                    "suit": "SPADES"
                  },
                  "visibleCards": [
                    {
                      "rank": "4",
                      "suit": "CLUBS"
                    },
                    {
                      "rank": "A",
                      "suit": "HEARTS"
                    }
                  ]
                }"""




        tvJsonObject.text = Moshi.Builder().build()
            .adapter(SelectedCards::class.java)
            .fromJson(jsonString)
            .toString()

        tvJson.text = Moshi.Builder().build()
            .adapter<SelectedCards>(SelectedCards::class.java).indent("     ")
            .toJson(
                Moshi.Builder().build()
                    .adapter(SelectedCards::class.java)
                    .fromJson(jsonString)
            )
/*
        tvJsonObject.text =
                GsonBuilder().create().fromJson<SelectedCards>(jsonString, SelectedCards::class.java).toString()
*/
        /*tvJson.text = GsonBuilder().setPrettyPrinting().create()
            .toJson(
                GsonBuilder().create()
                    .fromJson<SelectedCards>(jsonString, SelectedCards::class.java)
            )
*/

    }
}

class Cards {
    private var rank = ' '
    private lateinit var suit: Suit

    override fun toString(): String {
        return "Rank: $rank and Suit: $suit"
    }
}

class SelectedCards {
    @SerializedName("hidden_card")
    private var hiddenCard: Cards? = null
    @SerializedName("visible_cards")
    private var visibleCards = listOf<Cards>()

    override fun toString(): String {

        var visibleCardsString = ""
        visibleCards.forEach {
            visibleCardsString += it.toString() + "\n"
        }

        return "Hidden Card: $hiddenCard \nVisible Cards: $visibleCardsString"

    }
}

enum class Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES
}