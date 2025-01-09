
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin{
    androidTarget()
    jvm("desktop")
    sourceSets{
        val commonMain by getting{
            dependencies{
                implementation(libs.kotlin.stdlib)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.material)
            }
        }
        val commonTest by getting{
            dependencies{
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
            }
        }
        val desktopMain by getting{
            dependencies{
                implementation(libs.kotlin.stdlib)
                implementation(compose.desktop.common)
            }
        }
        val desktopTest by getting{
            dependencies{
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
            }
        }
        val androidMain by getting{
            dependencies{
                implementation(libs.kotlin.stdlib)
                api(libs.androidx.activity.compose)
            }
        }
        val androidUnitTest by getting{
            dependencies{
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
                implementation(libs.robolectric)
                implementation(libs.junit)
            }
        }
    }
    jvmToolchain(17)
}
tasks.withType<Test>().configureEach {
    useJUnitPlatform()  // Ensures JUnit Platform is used for Kotest
}

android{
    namespace = "org.github.kanvas.whiteboard"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testOptions {
            targetSdk = libs.versions.android.targetSdk.get().toInt()
            unitTests {
                isIncludeAndroidResources = true
            }
        }
        lint{
            targetSdk = libs.versions.android.targetSdk.get().toInt()
        }
        // No applicationId, versionCode, or versionName here
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true  // For release, minify enabled (proguard)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}