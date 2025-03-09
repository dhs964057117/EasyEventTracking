package com.dhs.tools.easyeventtracking

import android.app.Application
import android.util.Log
import android.view.View
import com.haosen.tool.exposure.detect.GlobalViewEvent
import com.haosen.tool.exposure.detect.bean.EventType
import com.haosen.tool.exposure.detect.callback.ViewEvent


/**
 * FileName: App
 * Author: haosen
 * Date: 2025/3/8 18:32
 * Description:
 **/
class App : Application() {
    companion object {
        const val TAG = "App onViewEvent"
    }

    override fun onCreate() {
        super.onCreate()
        GlobalViewEvent.registerEvent(listener = object : ViewEvent() {
            override fun onViewEvent(view: View, event: EventType, data: Any?) {
                Log.e(TAG, "view:$view, eventType:$event, data:$data")
            }
        })
    }
}