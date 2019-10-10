package com.common.rxmvvm

import com.common.core.utils.AddressableActivity

private const val packageName = BuildConfig.APPLICATION_ID

object Activities {
    object Login: AddressableActivity {
        override val className: String = "$packageName.ui.login.LoginActivity"
    }
    object Login1: AddressableActivity {
        override val className: String = "$packageName.ui.login.Login1Activity"
    }
    object Main: AddressableActivity {
        override val className: String = "$packageName.ui.main.MainActivity"
    }
    object WebView: AddressableActivity {
        override val className: String = "$packageName.ui.main.WebViewActivity"
    }
    object SmartRefresh: AddressableActivity {
        override val className: String = "$packageName.ui.main.SmartRefreshActivity"
    }
}