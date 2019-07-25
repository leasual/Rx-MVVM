package com.wesoft.rxmvvm.base.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.wesoft.rxmvvm.base.BuildConfig

/**
 * Created by james.li on 2017/bg/20.
 */

fun Context.isNetworkAvailable(): Boolean {
    val connectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectionManager?.activeNetworkInfo?.isConnected ?: false
}

fun Context.toast(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}

fun Context.formatMessage(format: Int, message: Int): String {
    return String.format(this.resources.getString(format),
            this.resources.getString(message))
}

fun Context.toast(message: Int, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}

fun Context.logv(tag: String = this.javaClass.simpleName, message: String) {
    if (BuildConfig.DEBUG) {
        Log.v(tag, message)
    }
}

fun Context.logv(message: String) {
    if (BuildConfig.DEBUG) {
        Log.v(this.javaClass.simpleName, message)
    }
}

fun Context.logd(tag: String = this.javaClass.simpleName, message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, message)
    }
}

fun Context.logd(message: String) {
    if (BuildConfig.DEBUG) {
        Log.v(this.javaClass.simpleName, message)
    }
}

fun Context.logi(tag: String = this.javaClass.simpleName, message: String) {
    Log.i(tag, message)
}

fun Context.logi(message: String) {
    if (BuildConfig.DEBUG) {
        Log.i(this.javaClass.simpleName, message)
    }
}

fun Context.logw(tag: String = this.javaClass.simpleName, message: String) {
    if (BuildConfig.DEBUG) {
        Log.w(tag, message)
    }
}

fun Context.logw(message: String) {
    if (BuildConfig.DEBUG) {
        Log.w(this.javaClass.simpleName, message)
    }
}

fun Context.loge(tag: String = this.javaClass.simpleName, message: String) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, message)
    }
}

fun Context.loge(message: String) {
    if (BuildConfig.DEBUG) {
        Log.e(this.javaClass.simpleName, message)
    }
}

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}
