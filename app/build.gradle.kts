import build.BuildConfig
import build.BuildCreator
import build.BuildDimensions
import build.BuildTypes
import deps.androidTestDeps
import deps.androidx
import deps.debugTestDeps
import deps.hilt
import deps.loginModule
import deps.okHttp
import deps.retrofit
import deps.room
import deps.testDeps
import flavors.BuildFlavor
import release.ReleaseConfig
import signing.BuildSigning
import test.TestBuildConfig

plugins {
    id(plugs.BuildPlugins.KOTLIN_ANDROID)
    id(plugs.BuildPlugins.ANDROID_APPLICATION)
    id(plugs.BuildPlugins.ANDROID)
    id("org.jetbrains.kotlin.plugin.compose")
    id(plugs.BuildPlugins.KSP)
}

android {
    namespace = BuildConfig.APP_ID
    compileSdk = BuildConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildConfig.APP_ID
        minSdk = BuildConfig.MIN_SDK_VERSION
        targetSdk = BuildConfig.TARGET_SDK_VERSION
        versionCode = ReleaseConfig.VERSION_CODE
        versionName = ReleaseConfig.VERSION_NAME

        testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
    }

    signingConfigs{
        BuildSigning.Release(project).create(this)
        BuildSigning.ReleaseExternalQA(project).create(this)
        BuildSigning.Debug(project).create(this)
    }

    buildTypes {
        BuildCreator.Release(project).create(this).apply {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(BuildTypes.RELEASE)
        }
        BuildCreator.Debug(project).create(this).apply {
            signingConfig = signingConfigs.getByName(BuildTypes.DEBUG)
        }
        BuildCreator.ReleaseExternalQa(project).create(this).apply {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(BuildTypes.RELEASE_EXTERNAL_QA)
        }
    }
    flavorDimensions.add(BuildDimensions.APP)
    flavorDimensions.add(BuildDimensions.STORE)

    productFlavors {
        BuildFlavor.Google.create(this)
        BuildFlavor.Huawei.create(this)
        BuildFlavor.Driver.create(this)
        BuildFlavor.Client.create(this)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    loginModule()
    androidx()
    hilt()
    room()
    okHttp()
    retrofit()
    testDeps()
    androidTestDeps()
    debugTestDeps()
}