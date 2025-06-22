plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.mindera.presentation"
    compileSdk = libs.versions.compileSdk.get().toInt()
    kotlinOptions {
        jvmTarget = "1.8"
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        minSdk = libs.versions.minSdk.get().toInt()
    }
    tasks.withType<Test> {
        useJUnitPlatform()
        jvmArgs("-XX:+EnableDynamicAgentLoading")
    }

    testOptions {
        packaging {
            resources.excludes.add("META-INF/*")
        }
        unitTests.isReturnDefaultValues = true

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
    implementation(project(":core:base"))
    implementation(project(":core:designsystem"))
    implementation(project(":domain"))


    implementation(libs.bundles.viewModel)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.bundles.uiTest)
    testImplementation(kotlin("test"))

}