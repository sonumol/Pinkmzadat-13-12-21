package com.platinummzadat.qa.views.registration.profilephoto

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import com.fxn.utility.PermUtil
import com.platinummzadat.qa.*
import com.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import kotlinx.android.synthetic.main.activity_profile_photo_registration.*
import net.idik.lib.cipher.so.CipherClient
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*
import raj.nishin.wolfrequest.ERROR


class ProfilePhotoRegistrationActivity : MzActivity(), ProfilePhotoContract.View {
    override lateinit var presenter: ProfilePhotoContract.Presenter
    private var path = ""
    private lateinit var progress: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProfilePhotoPresenter(this)
        setContentView(com.platinummzadat.qa.R.layout.activity_profile_photo_registration)

        clProfilePhoto.onClick {
            openCamera()
        }
        clUpload.onClick {
            uploadPhoto()
        }
        tvSkip.onClick {
            showSuccess()

        }
        llTac.onClick {
           /* val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                this@ProfilePhotoRegistrationActivity,
                Uri.parse(TOS_URL)
            )*/
            startActivity<TermsOfServiceActivity>()
        }
        progress = getProgressDialog()
    }

    private fun uploadPhoto() {
        if ("" != path) {
            uploadPhoto(path)
        } else {
            openCamera()
            toast(getString(R.string.select_photo))
        }
    }

    override fun showSuccess() {
        startActivity<ProceedMessage>()
        finish()
    }

    override fun showNoInternet() {
        noInternetAlert()
    }

    override fun showLoading() {
        progress.show()
        toast(getString(R.string.uploading))
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun showApiError() {
        root.longSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            uploadPhoto()
        }
    }

    override fun sessionTimeOut() {
    }


    private fun openCamera() {
        Pix.start(
            this@ProfilePhotoRegistrationActivity,
            Options.init()
                .setRequestCode(RC_PICK_PROFILE_IMAGE)
                .setCount(1)
                .setImageQuality(ImageQuality.REGULAR)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RC_PICK_PROFILE_IMAGE) {
            path = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
            setAvatar(ivProfilePhoto, path)
            clPlaceHolder visibility invisible
            ivProfilePhoto visibility visible
        } else {
            path = ""
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    toast(getString(R.string.approve_permissions_to_open_image_picker))
                }
                return
            }
        }
    }

    var backPressTime = 0L
    override fun onBackPressed() {
        if (currentLocalTimeInMillis < backPressTime + 500) {
            super.onBackPressed()
        } else {
            backPressTime = currentLocalTimeInMillis
            toast(getString(R.string.press_back_again_to_exit))
        }
    }


    private fun uploadPhoto(filePath: String) {
        showLoading()
        uploadFile(
            filePath, CipherClient.serverApi() + "upload_profile_photo"
        ) { status, error ->
            hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    showApiError()
                }
                status -> {
                   showSuccess()
                }
                else -> {
                    showApiError()
                }
            }
        }
    }
}
