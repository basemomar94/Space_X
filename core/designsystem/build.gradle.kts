plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.mindera.rocketscience.designsystem"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig{
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        minSdk = libs.versions.minSdk.get().toInt()
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        packaging {
            resources.excludes.add("META-INF/*")
        }
        managedDevices {
            allDevices {
                maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>("pixel2api27").apply {
                    device = "Pixel 2"
                    apiLevel = 27
                    systemImageSource = "aosp"
                }
            }
        }
        unitTests.isReturnDefaultValues = true

    }
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    api(libs.bundles.compose)

    api(libs.glide)
    androidTestImplementation(libs.bundles.uiTest)
    testImplementation(kotlin("test"))
}