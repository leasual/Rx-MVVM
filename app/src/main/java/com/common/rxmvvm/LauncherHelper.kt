package com.common.rxmvvm

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment

private const val packageName = BuildConfig.APPLICATION_ID

fun Activity.startTo(addressableActivity: AddressableActivity, requestCode: Int = 0, finish: Boolean = false, body: Intent.() -> Unit) {
    when (requestCode) {
        0 -> {
            this.startActivity(Intent().setClassName(packageName, addressableActivity.className).apply(body))
            if (finish) { this.finish() }
        }
        else -> this.startActivityForResult(Intent().setClassName(packageName, addressableActivity.className).apply(body), requestCode)
    }
}

fun Fragment.startTo(addressableActivity: AddressableActivity, requestCode: Int = 0, body: Intent.() -> Unit) {
    when (requestCode) {
        0 -> this.startActivity(Intent().setClassName(packageName, addressableActivity.className).apply(body))
        else -> this.startActivityForResult(Intent().setClassName(packageName, addressableActivity.className).apply(body), requestCode)
    }
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
    object SmartRefresh: AddressableActivity {
        override val className: String = "$packageName.SmartRefreshActivity"
    }
}