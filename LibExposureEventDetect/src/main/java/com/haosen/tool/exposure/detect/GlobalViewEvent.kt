package com.haosen.tool.exposure.detect

import android.view.View
import com.haosen.tool.exposure.detect.core.ViewExposureDetectNode.Companion.hasInitExposureListener
import com.haosen.tool.exposure.detect.core.ViewExposureDetectNode.Companion.registerExposureListener
import com.haosen.tool.exposure.detect.bean.EventType
import com.haosen.tool.exposure.detect.callback.ViewEvent
import com.haosen.tool.exposure.detect.core.ViewExposureDetectNode
import com.haosen.tool.exposure.detect.bean.DetectionConfig
import com.haosen.tool.exposure.detect.core.ViewExposureListener.Companion.addViewExposureDetectThresholdListener


/**
 * FileName: GlobalExposure
 * Author: haosen
 * Date: 2025/3/1 22:49
 * Description:
 **/
object GlobalViewEvent {
    internal var detectionConfig: DetectionConfig = DetectionConfig()
        private set

    @JvmStatic
    fun registerEvent(config: DetectionConfig = DetectionConfig(), listener: ViewEvent) {
        if (hasInitExposureListener())
            return
        detectionConfig = config
        registerExposureListener(listener)
    }

    @JvmStatic
    fun View.setDesc(data: Any?) {
        data?.also {
            setTag(R.id.tag_view_event_report_data, it)
        }
        addViewExposureDetectThresholdListener(detectionConfig.threshold) {}
    }

    @JvmStatic
    fun onViewClickEvent(view: View) {
        ViewExposureDetectNode.listener?.onViewEvent(
            view,
            EventType.CLICK,
            view.getTag(R.id.tag_view_event_report_data)
        )
    }
}