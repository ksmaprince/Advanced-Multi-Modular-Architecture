sealed class Build {

    open val versionNameSuffix = ""
    open val applicationIdSuffix = ""
    open val isMinifyEnabled = false
    open val isDebuggable = false
    open val enableUnitTestCoverage = false

    object Debug: Build(){
        override val versionNameSuffix = "-DEBUG"
        override val applicationIdSuffix = ".debug"
        override val isMinifyEnabled = false
        override val isDebuggable = true
        override val enableUnitTestCoverage = true
    }
    object ReleaseExternalQa: Build(){
        override val versionNameSuffix = "-QA"
        override val applicationIdSuffix = ".releaseExternalQa"
        override val isMinifyEnabled = false
        override val isDebuggable = false
        override val enableUnitTestCoverage = true
    }
    object Release: Build(){
        override val isMinifyEnabled = true
        override val isDebuggable = false
        override val enableUnitTestCoverage = false
    }
}