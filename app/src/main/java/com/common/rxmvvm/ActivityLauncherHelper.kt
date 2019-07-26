package com.common.rxmvvm

import android.app.Activity
import android.content.Intent

private const val packageName = BuildConfig.APPLICATION_ID

fun startTo(activity: Activity, addressableActivity: AddressableActivity, body: Intent.() -> Unit) {
    activity.startActivity(Intent().setClassName(packageName, addressableActivity.className).apply(body))
}

interface AddressableActivity{
    val className: String
}

object Activities {
    object Login: AddressableActivity {
        override val className: String = "$packageName.LoginActivity"
    }
    object Main: AddressableActivity {
        override val className: String = "$packageName.MainActivity"
    }
    object WebView: AddressableActivity {
        override val className: String = "$packageName.WebViewActivity"
    }
}