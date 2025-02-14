package plugs

import build.BuildConfig
import build.BuildCreator
import build.BuildDimensions
import build.BuildTypes
import com.android.build.gradle.LibraryExtension
import flavors.BuildFlavor
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import signing.BuildSigning
import test.TestBuildConfig

class SharedLibraryGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.addPluginConfigurations()
        project.addAndroidConfiguration()
        project.addKotlinOptions()
    }

    private fun Project.addPluginConfigurations() {
        plugins.apply(BuildPlugins.KOTLIN_COMPOSE)
        plugins.apply(BuildPlugins.KOTLIN_ANDROID)
        plugins.apply(BuildPlugins.KSP)
        plugins.apply(BuildPlugins.KTLINT)
        plugins.apply(BuildPlugins.SPOTLESS)
//        plugins.apply(BuildPlugins.DETEKT)
        plugins.apply(BuildPlugins.UPDATE_DEPS_VERSION)
        plugins.apply(BuildPlugins.DOKKA)
    }

    private fun Project.addAndroidConfiguration() {
        extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = BuildConfig.COMPILE_SDK_VERSION

            defaultConfig {
                minSdk = BuildConfig.MIN_SDK_VERSION
                testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER

            }

            signingConfigs{
                BuildSigning.Release(project).create(this)
                BuildSigning.ReleaseExternalQA(project).create(this)
                BuildSigning.Debug(project).create(this)
            }

            buildTypes {
                BuildCreator.Release(project).createLibrary(this).apply {
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                    signingConfig = signingConfigs.getByName(BuildTypes.RELEASE)
                }
                BuildCreator.Debug(project).createLibrary(this).apply {
                    signingConfig = signingConfigs.getByName(BuildTypes.DEBUG)
                }
                BuildCreator.ReleaseExternalQa(project).createLibrary(this).apply {
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
                BuildFlavor.Google.createLibrary(this)
                BuildFlavor.Huawei.createLibrary(this)
                BuildFlavor.Driver.createLibrary(this)
                BuildFlavor.Client.createLibrary(this)
            }
            buildFeatures {
                compose = true
                buildConfig = true
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

        }
    }

    private fun Project.addKotlinOptions() {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }
}