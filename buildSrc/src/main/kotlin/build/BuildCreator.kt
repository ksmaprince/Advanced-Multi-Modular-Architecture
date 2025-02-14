package build

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.LibraryBuildType
import extensions.buildConfigBooleanField
import extensions.buildConfigIntField
import extensions.buildConfigStringField
import extensions.getLocalProperty
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

sealed class BuildCreator(val name: String) {
    abstract fun create(action: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType

    abstract fun createLibrary(action: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType

    class Debug(private val project: Project) : BuildCreator(BuildTypes.DEBUG) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name) {
                versionNameSuffix = Build.Debug.versionNameSuffix
                applicationIdSuffix = Build.Debug.applicationIdSuffix
                isMinifyEnabled = Build.Debug.isMinifyEnabled
                isDebuggable = Build.Debug.isDebuggable
                enableUnitTestCoverage = Build.Debug.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.BASE_URL,
                    project.getLocalProperty("dev_debug_endpoint")
                )
                buildConfigStringField(
                    BuildVariables.PIN_CERTIFICATE,
                    project.getLocalProperty("dev.project.certificate")
                )
                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("dev.map_key")
                )
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Debug.isMinifyEnabled
                enableUnitTestCoverage = Build.Debug.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.BASE_URL,
                    project.getLocalProperty("dev_debug_endpoint")
                )
                buildConfigStringField(
                    BuildVariables.PIN_CERTIFICATE,
                    project.getLocalProperty("dev.project.certificate")
                )
                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("dev.map_key")
                )
            }
        }
    }

    class Release(private val project: Project) : BuildCreator(BuildTypes.RELEASE) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Release.isMinifyEnabled
                isDebuggable = Build.Release.isDebuggable
                enableUnitTestCoverage = Build.Release.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.BASE_URL,
                    project.getLocalProperty("dev.prod_endpoint")
                )
                buildConfigStringField(
                    BuildVariables.PIN_CERTIFICATE,
                    project.getLocalProperty("release.project.certificate")
                )
                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("release.map_key")
                )
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Release.isMinifyEnabled
                enableUnitTestCoverage = Build.Release.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.BASE_URL,
                    project.getLocalProperty("dev.prod_endpoint")
                )
                buildConfigStringField(
                    BuildVariables.PIN_CERTIFICATE,
                    project.getLocalProperty("release.project.certificate")
                )
                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("release.map_key")
                )
            }
        }
    }

    class ReleaseExternalQa(private val project: Project) :
        BuildCreator(BuildTypes.RELEASE_EXTERNAL_QA) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.create(name) {
                versionNameSuffix = Build.ReleaseExternalQa.versionNameSuffix
                applicationIdSuffix = Build.ReleaseExternalQa.applicationIdSuffix
                isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
                isDebuggable = Build.ReleaseExternalQa.isDebuggable
                enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.BASE_URL,
                    project.getLocalProperty("dev.qa_endpoint")
                )
                buildConfigStringField(
                    BuildVariables.PIN_CERTIFICATE,
                    project.getLocalProperty("dev.project.certificate")
                )
                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("qa.map_key")
                )
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.create(name) {
                isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
                enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.BASE_URL,
                    project.getLocalProperty("dev.qa_endpoint")
                )
                buildConfigStringField(
                    BuildVariables.PIN_CERTIFICATE,
                    project.getLocalProperty("dev.project.certificate")
                )
                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("qa.map_key")
                )
            }
        }
    }

}