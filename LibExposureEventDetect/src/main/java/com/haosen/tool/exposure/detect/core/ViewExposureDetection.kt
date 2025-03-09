package com.haosen.tool.exposure.detect.core

import android.graphics.Rect
import android.view.View
import com.haosen.tool.exposure.detect.GlobalViewEvent.detectionConfig
import com.haosen.tool.exposure.detect.core.ViewExposureRectListener.Companion.addViewExposureListener
import com.haosen.tool.exposure.detect.core.ViewExposureRectListener.Companion.calculateExposureRatio
import com.haosen.tool.exposure.detect.bean.EventType
import com.haosen.tool.exposure.detect.R
import com.haosen.tool.exposure.detect.bean.DetectionConfig

interface ViewExposureRectListener {
    fun onExposureRectChanged(view: View, rect: Rect)

    companion object {
        @JvmStatic
        fun View.addViewExposureListener(listener: ViewExposureRectListener?) {
            listener ?: return
            getOrCreateViewExposureDetectNode().addExposureListener(listener)
        }

        @JvmStatic
        fun View.removeViewExposureDetectListener(listener: ViewExposureRectListener?) {
            listener ?: return
            viewExposureDetectNode?.removeExposureListener(listener)
        }

        @JvmStatic
        fun View.calculateExposureRatio(rect: Rect?) =
            if (rect == null || rect.isEmpty || width == 0 || height == 0)
                0f
            else
                rect.width().toFloat() * rect.height() / (width * height)
    }
}

typealias ViewExposureJudgmentStrategy = (view: View, rect: Rect, lastExpose: Boolean?) -> Boolean

abstract class ViewExposureListener(
    val strategy: ViewExposureJudgmentStrategy
) :
    ViewExposureRectListener {
    private var exposed: Boolean? = null
    override fun onExposureRectChanged(view: View, rect: Rect) {
        val expose = strategy(view, rect, exposed)
        if (expose == exposed) {
            return
        }
        exposed = expose
        if (STATISTICS_MOD) {
            if (expose) {
                sStatisticsData.exposureVisibleCount++
            } else {
                sStatisticsData.exposureGoneCount++
            }
            //统计模式不再触发动作,以免计入回调时间
            return
        }
        onChange(expose)
        if (expose)
            ViewExposureDetectNode.listener?.onViewEvent(
                view,
                EventType.EXPOSURE,
                view.getTag(R.id.tag_view_event_report_data)
            )
    }

    abstract fun onChange(exposed: Boolean)

    companion object {
        @JvmStatic
        fun View.addViewExposureDetectThresholdListener(
            threshold: Float = 0f,
            onChange: ((exposed: Boolean) -> Unit)? = null
        ): ViewExposureListener {
            val strategy: ViewExposureJudgmentStrategy = when (threshold) {
                0f -> sOnePixelStrategy
                1f -> sFullPixelStrategy
                else -> {
                    { view, rect, _ ->
                        view.calculateExposureRatio(rect) >= threshold
                    }
                }
            }
            val listener = object : ViewExposureListener(strategy) {
                override fun onChange(exposed: Boolean) {
                    onChange?.invoke(exposed)
                }
            }
            addViewExposureListener(listener)
            return listener
        }

        private val sOnePixelStrategy: ViewExposureJudgmentStrategy = { _, rect, _ ->
            rect.width() > 0 && rect.height() > 0
        }

        private val sFullPixelStrategy: ViewExposureJudgmentStrategy = { v, rect, _ ->
            val vWith = v.width
            val vHeight = v.height
            vWith > 0 && vHeight > 0 && rect.width() == vWith && rect.height() == vHeight
        }

        @JvmStatic
        fun View.asViewExposureDetectRoot(config: DetectionConfig = detectionConfig) {
            viewExposureDetectNode?.let {
                throw Error("必须在设置ViewExposureDetectRectListener前设定ExposureDetectRoot,并且只能设置一次")
            }
            viewExposureDetectNode = ViewExposureDetectRoot(this, config)
        }
    }
}