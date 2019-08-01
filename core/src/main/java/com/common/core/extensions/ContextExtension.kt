package com.common.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import com.common.core.utils.AddressableActivity

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
        0 -> this.startActivity(Intent().setClassName(context!!.packageName, addressableActivity.className).apply(body))
        else -> this.startActivityForResult(Intent().setClassName(context!!.packageName, addressableActivity.className).apply(body), requestCode)
    }
}

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


//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)