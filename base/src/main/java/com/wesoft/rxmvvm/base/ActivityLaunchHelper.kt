package com.wesoft.rxmvvm.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

object ActivityLaunchHelper {

    private const val URL_BASE = "https://gank.io"

    //Login
    private const val URL_LOGIN = "$URL_BASE/login"
    //Main
    private const val URL_MAIN = "$URL_BASE/main"

    fun launchLogin(activity: Activity, bundle: Bundle?, requestCode: Int?) {
        baseLaunch(URL_LOGIN, activity, bundle, requestCode)
    }

    fun launchLogin(fragment: Fragment, bundle: Bundle?, requestCode: Int?) {
        baseLaunch(URL_LOGIN, fragment, bundle, requestCode)
    }

    fun launchMain(activity: Activity, bundle: Bundle?, requestCode: Int?) {
        baseLaunch(URL_MAIN, activity, bundle, requestCode)
    }

    fun launchMain(fragment: Fragment, bundle: Bundle?, requestCode: Int?) {
        baseLaunch(URL_MAIN, fragment, bundle, requestCode)
    }


    private fun baseLaunch(url: String, activity: Activity, bundle: Bundle?, requestCode: Int?) {
        try {
            val intent = baseIntent(url, activity)
            if (null != bundle) {
                intent.putExtras(bundle)
            }
            if (null != requestCode) {
                activity.startActivityForResult(intent, requestCode)
            } else {
                activity.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun baseLaunch(url: String, fragment: Fragment, bundle: Bundle?, requestCode: Int?) {
        try {
            val intent = baseIntent(url, fragment.context)
            if (null != bundle) {
                intent.putExtras(bundle)
            }
            if (null != requestCode) {
                fragment.startActivityForResult(intent, requestCode)
            } else {
                fragment.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun baseIntent(url: String, context: Context? = null): Intent {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addCategory(Intent.CATEGORY_DEFAULT)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        //if set context null will show user switch browser or app to open, else it use app to open by default
        if (context != null) {
            Log.d("test", "packageName= ${context.packageName}")
            intent.`package` = context.packageName
        }
        return intent
    }
}