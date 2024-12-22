
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
            }
        }
        val commonTest by getting{
            dependencies {
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
            }
        }
        val androidMain by getting{
            dependsOn(commonMain)
            dependencies{
                implementation(libs.kotlin.stdlib)
            }
        }
        val androidUnitTest by getting{
            dependsOn(commonTest)
            dependencies {
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
            }

        }
        val desktopMain by getting{
            dependsOn(commonMain)
            dependencies{
                implementation(libs.kotlin.stdlib)
            }
        }
        val desktopTest by getting{
            dependsOn(commonTest)
            dependencies {
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
            }
        }
    }

}
// Enable JUnit Platform to run Kotest tests
tasks.withType<Test>().configureEach {
    useJUnitPlatform()  // Ensures JUnit Platform is used for Kotest
}

android {
    namespace = "com.github.kanvas.library"  // Library namespace
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testOptions {
            targetSdk = libs.versions.android.targetSdk.get().toInt()
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
}