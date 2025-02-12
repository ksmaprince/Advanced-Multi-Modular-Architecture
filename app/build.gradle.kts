import build.BuildConfig
import build.BuildCreator
import build.BuildDimensions
import build.BuildTypes
import deps.Dependencies
import flavors.BuildFlavor
import release.ReleaseConfig
import signing.BuildSigning
import test.TestBuildConfig
import test.TestDependencies

plugins {
    id(plugs.BuildPlugins.KOTLIN_ANDROID)
    id(plugs.BuildPlugins.ANDROID_APPLICATION)
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

    implementation(Dependencies.ANDROIDX_CORE_KTX)
    implementation(Dependencies.ANDROIDX_LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(platform(Dependencies.ANDROIDX_COMPOSE))
    implementation(Dependencies.ANDROIDX_UI)
    implementation(Dependencies.ANDROIDX_COMPOSE_UI_GRAPHIC)
    implementation(Dependencies.ANDROIDX_UI_TOOLING_PREVIEW)
    implementation(Dependencies.ANDROIDX_COMPOSE_MATERIAL3)

    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_EXPRESSO_CORE)
    androidTestImplementation(platform(TestDependencies.ANDROIDX_COMPOSE))//libs.androidx.compose.bom
    androidTestImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST_JUNIT4)
    debugImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TOOLING)
    debugImplementation(TestDependencies.ANDROIDX_UI_TEST_MANIFEST)
}