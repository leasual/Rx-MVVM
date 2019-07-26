package com.common.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

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