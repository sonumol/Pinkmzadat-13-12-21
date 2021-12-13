package com.platinummzadat.qa


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.platinummzadat.qa.views.root.MzFragmentListener


open class MzFragment : Fragment() {
    var fragmentListener: MzFragmentListener? = null
    var firstLoad = true
    lateinit var fragmentView: View
    fun onCreateView(layout: Int, inflater: LayoutInflater, container: ViewGroup?): View? {
        if (this::fragmentView.isInitialized) {
            firstLoad = false
        } else {
            fragmentView = inflater.inflate(layout, container, false)
            firstLoad = true
        }
        return fragmentView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentListener = if (context is MzFragmentListener) context else null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }


}
