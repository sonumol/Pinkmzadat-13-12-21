package com.platinummzadat.qa.views.registration.profilephoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.platinummzadat.qa.R
import com.platinummzadat.qa.views.root.RootActivity
import kotlinx.android.synthetic.main.activity_proceed_message.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity


class ProceedMessage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceed_parent)
        btnProceed.onClick {
            // showSuccess()

            val intent = Intent(this@ProceedMessage, RootActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("type","profile")
            startActivity(intent)
            finish()
        }

    }

}