package com.mifos.mifosxdroid.passcode;

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import butterknife.BindView
import butterknife.ButterKnife
import com.mifos.mifosxdroid.HomeActivity
import com.mifos.mifosxdroid.R
import com.mifos.mifosxdroid.SplashScreenActivity
import com.mifos.mifosxdroid.core.util.Toaster
import com.mifos.mobile.passcode.MifosPassCodeActivity
import com.mifos.mobile.passcode.utils.EncryptionUtil
import com.mifos.mobile.passcode.utils.PasscodePreferencesHelper
import com.mifos.utils.Constants

class PassCodeActivity : MifosPassCodeActivity() {

    @BindView(R.id.cl_rootview)
    lateinit var clRootView: NestedScrollView

    private var currPassCode: String? = null
    private var isToUpdatePassCode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ButterKnife.bind(this)
        clRootView.setBackgroundColor(android.R.attr.colorBackground)

        intent?.let {
            currPassCode = it.getStringExtra(Constants.CURR_PASSWORD)
            isToUpdatePassCode = it.getBooleanExtra(Constants.IS_TO_UPDATE_PASS_CODE, false)
        }
    }

    override fun showToaster(view: View?, msg: Int) {
        Toaster.show(view, msg, Toaster.SHORT)
    }

    override fun startLoginActivity() {
        startActivity(Intent(this, SplashScreenActivity::class.java))
        finish()
    }

    override fun getLogo(): Int {
        return R.drawable.mifos_logo
    }

    override fun getEncryptionType(): Int {
        return EncryptionUtil.FINERACT_CN
    }

    override fun startNextActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isToUpdatePassCode && !currPassCode.isNullOrEmpty()) {
            PasscodePreferencesHelper(this).apply {
                savePassCode(currPassCode)
            }
        }
        finish()
    }
}
