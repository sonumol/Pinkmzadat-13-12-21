package com.platinummzadat.qa.views.deeplink

import android.os.Bundle
import com.platinummzadat.qa.MzActivity
import com.platinummzadat.qa.views.root.RootActivity
import org.jetbrains.anko.startActivity
import raj.nishin.wolfpack.wlog

class DeepLinkActivity : MzActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action: String? = intent?.action
        val data: String = intent?.data?.toString() ?: ""
        wlog("DEEP LINK ACTION $action")
        wlog("DEEP LINK DATA $data")
        val itemId = try {
            data.substring(data.lastIndexOf("/")+1).toInt()
        } catch (e: Exception) {
            -1
        }
        startActivity<RootActivity>(
            "type" to "redirect",
            "item_id" to itemId
        )
        finish()
    }

}
