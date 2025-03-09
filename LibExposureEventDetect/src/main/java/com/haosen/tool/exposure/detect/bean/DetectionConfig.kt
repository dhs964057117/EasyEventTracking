package com.haosen.tool.exposure.detect.bean


/**
 * FileName: DetectionConfig
 * Author: haosen
 * Date: 2025/3/8 9:21
 * Description:
 **/
data class DetectionConfig(
    val threshold: Float = 0f,
    val effectiveExposureTime: Long = 200L
)
