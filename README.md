# EasyEventTracking

## 介绍

一个用于快速实现全埋点方案的框架，能够快速收集页面元素的曝光和点击事件，并且在统一的入口进行上报

## LibClickEventPlugin

使用ASM，在编译器扫描符合条件即设置点击事件setOnclickListener或者lambda表达式，在符合条件的方法中插入收集事件的代码，实现无痕埋点。

## LibExposureEventDetect

通过在页面元素中添加用于观察元素是否可见的子View来实现，对元素曝光的检测和上报

## 使用方法

在application中进行注册

```kotlin
    GlobalViewEvent.registerEvent(listener = object : ViewEvent() {
        override fun onViewEvent(view: View, event: EventType, data: Any?) {
            Log.e(TAG, "view:$view, eventType:$event, data:$data")
        }
    })
```

在需要收集曝光事件的元素上设置曝光数据

```kotlin
    textView.setDesc("textView:$index")
```

