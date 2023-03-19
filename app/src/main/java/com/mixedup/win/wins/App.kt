package com.mixedup.win.wins

import android.app.Application
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : Application()  {

    private val ONESIGNAL_APP_ID = "f55e7a30-0c03-404a-bf8f-9a50dcc9b6e0"
    private val YANDEX_API_KEY = "1923ca78-434e-4ce5-bd2c-eb8103941b0f"

    override fun onCreate() {
        super.onCreate()
        initOneConfig()
        val config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY).build()
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);
    }

    private fun initOneConfig() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this)

        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}