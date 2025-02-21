plugins{
    `kotlin-dsl`
//    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false

}

repositories{
    google()
    mavenCentral()
    gradlePluginPortal()
}

//Lecture - 12
dependencies{
    api(kotlin("gradle-plugin:1.7.20"))
    implementation("com.android.tools.build:gradle:8.7.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.22.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.51.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
    implementation("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.20")
    implementation("com.squareup:javapoet:1.13.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.1.0")
    // Kotlin Serialization (If Needed)
//    implementation("org.jetbrains.kotlin.plugin.serialization:2.1.0")

    // Add KSP plugin
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.1.0-1.0.29")
}