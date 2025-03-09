import com.haosen.clickEventCollection.plugin.bean.Config
import com.haosen.clickEventCollection.plugin.bean.TraceMethod

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("click.event.collection")
}

android {
    namespace = "com.dhs.tools.easyeventtracking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dhs.tools.easyeventtracking"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

tracePointConfig {
    configs = listOf(
        Config(
            packages = listOf("com.dhs.tools.easyeventtracking"),
            methods = TraceMethod(
                traceOwner = "com/haosen/tool/exposure/detect/GlobalViewEvent",
                traceName = "onViewClickEvent",
                traceDesc = "(Landroid/view/View;)V",
                owner = "Landroid/view/View\$OnClickListener;",
                name = "onClick",
                desc = "(Landroid/view/View;)V",
                onMethod = 1,
            )
        )
    )
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    implementation(project(":LibClickEventPlugin"))
    implementation(project(":LibExposureEventDetect"))
}