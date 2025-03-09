package com.haosen.tool.exposure.detect.callback

import android.view.View
import com.haosen.tool.exposure.detect.bean.EventType

abstract class ViewEvent {
    open fun onViewEvent(view: View, event: EventType, data: Any?) {}
}