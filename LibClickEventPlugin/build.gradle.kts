plugins {
    id("java-library")
    id("kotlin")
    id("maven-publish")
    id("java-gradle-plugin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.asm)
    implementation(libs.asm.commons)
    implementation(libs.asm.analysis)
    implementation(libs.asm.util)
    implementation(libs.asm.tree)
    implementation(libs.gradle) {
        exclude(group = "org.ow2.asm", module = "asm")
    }
    implementation(libs.gson)
}

gradlePlugin {
    plugins {
        create("TracePlugin") {
            id = "click.event.collection" //这里是插件的ID
            implementationClass = "com.haosen.clickEventCollection.plugin.TracePlugin" //这里是包名+类名
        }
    }
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                // isAllowInsecureProtocol = true // 如果Maven仓库仅支持http协议, 请打开此注释
                url = uri("https://your-repository") // 请填入你的仓库地址
                authentication {
                    create<BasicAuthentication>("basic")
                }
                credentials {
                    username = "your-username" // 请填入你的用户名
                    password = "your-password" // 请填入你的密码
                }
            }
        }

        publications {
            create<MavenPublication>("release") {
                from(components["java"])
                groupId = "com.haosen.plugin" // 请填入你的组件名
                artifactId = "click.event.collection" // 请填入你的工件名
                version = "1.0.0" // 请填入工件的版本名

                pom {
                    name.set("name") // (可选)为工件取一个名字
                    url.set("../repo") // (可选)网站地址
                    developers {
                        developer {
                            name.set("name") // (可选)开发者名称
                            email.set("email") // (可选)开发者邮箱
                        }
                    }
                }
            }
        }
    }
}

