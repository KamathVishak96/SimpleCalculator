package com.example.dependencyinjection

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.internal.DaggerCollections
import kotlinx.android.synthetic.main.activity_dagger.*
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Suppress("DEPRECATION")
class DaggerActivity : AppCompatActivity() {

    /*@Inject
    @field:Choose("new")
    lateinit var infoNew: Info

    @Inject
    @field:Choose("old")
    lateinit var infoOld: Info*/


    @Inject
    lateinit var info: Info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger)



        btnChangeText.setOnClickListener {
            DaggerComponentForInfo.builder().info(Info(if(tvDaggerTextInfo.text ==  "Hello") "Hai" else "Hai")).build().inject(this)
            tvDaggerTextInfo.text = info.text
        }



    }
}

/*class Info(var text: String)

@Module
class InfoModule {
    @Provides
    @Choose("new")
    fun returnNewText(): Info {
        return Info("New Text")
    }

    @Provides
    @Choose("old")
    fun returnOldText(): Info {
        return Info("Old Text")
    }
}*/

/*@Module
abstract class InfoModule {
    @Binds
    abstract fun returnNewText(info: Info): Info
}*/


@Module
class Info(var text: String) {
    @Singleton
    @Provides
    fun provideInfo(): String{
        return text
    }

    @Singleton
    @Provides
    fun returnNewText(text: String): Info {
        return Info(text)
    }
}

@Singleton
@Component(modules = [Info::class])
interface ComponentForInfo {
    fun inject(app: DaggerActivity)
}

/*@Component(modules = [InfoModule::class])
interface ComponentForInfo {
    fun inject(app: DaggerActivity)
}*/

@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Choose(val value: String = "")