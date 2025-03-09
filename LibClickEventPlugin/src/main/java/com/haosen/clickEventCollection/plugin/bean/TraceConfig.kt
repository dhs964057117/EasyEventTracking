package com.haosen.clickEventCollection.plugin.bean

open class TraceConfig(
    var configs: List<Config>
)

open class Config(
    var packages: List<String>,
    var methods: TraceMethod
)

open class TraceMethod(
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
     * 方式插入时机, 0: 方法退出前; 1: 方法进入时
     */
    var onMethod: Int = 0,
)