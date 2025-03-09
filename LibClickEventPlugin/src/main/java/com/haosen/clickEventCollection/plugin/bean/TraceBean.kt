package com.haosen.clickEventCollection.plugin.bean

import java.io.Serializable

class TraceBean(
    /**
     * 需要埋点类名(全路径)
     */
    var owner: String = "",
    /**
     * 需要埋点方法名
     */
    var name: String = "",
    /**
     * 需要埋点方法描述
     */
    var desc: String = "",
    /**
     * 需要埋点注解方法描述(全路径)
     */
    var annotationDesc: String = "",
    /**
     * 需要埋点注解参数名
     * String:注解参数名
     * String:参数类型
     */
    var annotationParams: Map<String, String> = LinkedHashMap(),
    /**
     * 需要埋点注解参数值
     * String:注解参数名
     * Object:参数值
     */
    var annotationData: MutableMap<String, Any?> = HashMap(),
    /**
     * 埋点类名(全路径)
     */
    var traceOwner: String = "",
    /**
     * 埋点方法名
     */
    var traceName: String = "",
    /**
     * 埋点方法描述
     */
    var traceDesc: String = "",
    /**
     * 方式插入时机, 0: 方法进入时; 1: 方法退出前
     */
    var onMethod: Int = 1,
) : Cloneable, Serializable {

    public override fun clone(): TraceBean {
        return try {
            super.clone() as TraceBean
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
            TraceBean()
        }
    }

}