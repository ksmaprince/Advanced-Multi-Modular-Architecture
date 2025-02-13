package deps

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project
import test.TestDependencies

fun DependencyHandler.room(){
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.ROOM_RUNTIME)
    ksp(Dependencies.ROOM_COMPILER)
}

fun DependencyHandler.retrofit(){
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.RETROFIT_KOTLIN_COROUTINE_ADAPTER)
}

fun DependencyHandler.okHttp(){
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_INTERCEPTOR)
}

fun DependencyHandler.hilt(){
    implementation(Dependencies.HILT_ANDROID)
    ksp(Dependencies.HILT_COMPILER)
}

fun DependencyHandler.androidx(){
    implementation(Dependencies.ANDROIDX_CORE_KTX)
    implementation(Dependencies.ANDROIDX_CORE_KTX)
    implementation(Dependencies.ANDROIDX_LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(platform(Dependencies.ANDROIDX_COMPOSE))
    implementation(Dependencies.ANDROIDX_UI)
    implementation(Dependencies.ANDROIDX_COMPOSE_UI_GRAPHIC)
    implementation(Dependencies.ANDROIDX_UI_TOOLING_PREVIEW)
    implementation(Dependencies.ANDROIDX_COMPOSE_MATERIAL3)
}

fun DependencyHandler.loginModule(){
    moduleImplementation(project(":features:login"))
}

fun DependencyHandler.homeModule(){
    moduleImplementation(project(":features:home"))
}

fun DependencyHandler.testDeps(){
    testImplementation(TestDependencies.JUNIT)
}

fun DependencyHandler.androidTestDeps(){
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_EXPRESSO_CORE)
    androidTestImplementation(platform(TestDependencies.ANDROIDX_COMPOSE))//libs.androidx.compose.bom
    androidTestImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST_JUNIT4)
    debugImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TOOLING)
    debugImplementation(TestDependencies.ANDROIDX_UI_TEST_MANIFEST)
}

fun DependencyHandler.debugTestDeps(){
    debugImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TOOLING)
    debugImplementation(TestDependencies.ANDROIDX_UI_TEST_MANIFEST)
}