package com.haosen.clickEventCollection.plugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.google.gson.Gson
import com.haosen.clickEventCollection.plugin.asm.TraceClassVisitorFactory
import com.haosen.clickEventCollection.plugin.bean.TraceBean
import com.haosen.clickEventCollection.plugin.bean.TraceConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

class TracePlugin : Plugin<Project> {

    private val gson = Gson()
    override fun apply(project: Project) {

        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        project.extensions.create("tracePointConfig", TraceConfig::class.java)
        androidComponents.onVariants { variant ->
            val extension = project.properties["tracePointConfig"] as TraceConfig
            System.out.println("-------apply:${extension.configs.size},${gson.toJson(extension.configs)}---------------------")
            for (i in extension.configs) {
                System.out.println("-------------------for:${gson.toJson(i.methods)}---------------------")
                variant.instrumentation.transformClassesWith(
                    TraceClassVisitorFactory::class.java,
                    InstrumentationScope.ALL
                ) {
                    it.packageName.addAll(i.packages)
                    it.listOfTraces.set(
                        listOf(
                            TraceBean(
                                traceOwner = i.methods.traceOwner,
                                traceName = i.methods.traceName,
                                traceDesc = i.methods.traceDesc, //参数应在desc范围之内
                                owner = i.methods.owner,
                                name = i.methods.name,
                                desc = i.methods.desc,
                                onMethod = i.methods.onMethod,
                            )
                        )
                    )
                }
            }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }

}