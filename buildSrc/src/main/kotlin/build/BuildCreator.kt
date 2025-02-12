package build

import extensions.buildConfigBooleanField
import extensions.buildConfigIntField
import extensions.buildConfigStringField
import com.android.build.api.dsl.ApplicationBuildType
import extensions.getLocalProperty
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

sealed class BuildCreator(val name: String) {
    abstract fun create(action: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType

    class Debug(private val project: Project): BuildCreator(BuildTypes.DEBUG){
        override fun create(namedDomainObjectContainer : NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name){
                versionNameSuffix = Build.Debug.versionNameSuffix
                applicationIdSuffix = Build.Debug.applicationIdSuffix
                isMinifyEnabled = Build.Debug.isMinifyEnabled
                isDebuggable = Build.Debug.isDebuggable
                enableUnitTestCoverage = Build.Debug.enableUnitTestCoverage

                buildConfigStringField(BuildVariables.BASE_URL, project.getLocalProperty("dev_debug_endpoint"))
                buildConfigIntField(BuildVariables.DB_VERSION, project.getLocalProperty("dev.db_version"))
                buildConfigBooleanField(BuildVariables.CAN_CLEAR_CACHE, project.getLocalProperty("dev.clear_cache"))
                buildConfigStringField(BuildVariables.MAP_KEY, project.getLocalProperty("dev.map_key"))
            }
        }
    }

    class Release(private val project: Project): BuildCreator(BuildTypes.RELEASE){
        override fun create(namedDomainObjectContainer : NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name){
                isMinifyEnabled = Build.Release.isMinifyEnabled
                isDebuggable = Build.Release.isDebuggable
                enableUnitTestCoverage = Build.Release.enableUnitTestCoverage

                buildConfigStringField(BuildVariables.BASE_URL, project.getLocalProperty("dev.prod_endpoint"))
                buildConfigIntField(BuildVariables.DB_VERSION, project.getLocalProperty("dev.db_version"))
                buildConfigBooleanField(BuildVariables.CAN_CLEAR_CACHE, project.getLocalProperty("dev.clear_cache"))
                buildConfigStringField(BuildVariables.MAP_KEY, project.getLocalProperty("release.map_key"))
            }
        }
    }

    class ReleaseExternalQa(private val project: Project): BuildCreator(BuildTypes.RELEASE_EXTERNAL_QA){
        override fun create(namedDomainObjectContainer : NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.create(name){
                versionNameSuffix = Build.ReleaseExternalQa.versionNameSuffix
                applicationIdSuffix = Build.ReleaseExternalQa.applicationIdSuffix
                isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
                isDebuggable = Build.ReleaseExternalQa.isDebuggable
                enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage


                buildConfigStringField(BuildVariables.BASE_URL, project.getLocalProperty("dev.qa_endpoint"))
                buildConfigIntField(BuildVariables.DB_VERSION, project.getLocalProperty("dev.db_version"))
                buildConfigBooleanField(BuildVariables.CAN_CLEAR_CACHE, project.getLocalProperty("dev.clear_cache"))
                buildConfigStringField(BuildVariables.MAP_KEY, project.getLocalProperty("qa.map_key"))
            }
        }
    }

}