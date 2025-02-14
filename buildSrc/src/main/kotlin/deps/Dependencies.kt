package deps

object Dependencies {
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${DependenciesVersions.CORE_KTX}"
    const val ANDROIDX_LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${DependenciesVersions.LIFE_CYCLE_RUNTIME_KTX}"
    const val ANDROIDX_ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${DependenciesVersions.ACTIVITY_COMPOSE}"
    const val ANDROIDX_COMPOSE = "androidx.compose:compose-bom:${DependenciesVersions.COMPOSE_BOM}"
    const val ANDROIDX_UI = "androidx.compose.ui:ui"
    const val ANDROIDX_COMPOSE_UI_GRAPHIC = "androidx.compose.ui:ui-graphics"
    const val ANDROIDX_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
    const val ANDROIDX_COMPOSE_MATERIAL3 = "androidx.compose.material3:material3"

    const val HILT_ANDROID = "com.google.dagger:hilt-android:${DependenciesVersions.HILT}"
    const val HILT_COMPOSE = "androidx.hilt:hilt-work:${DependenciesVersions.HILT_COMPOSE}"
    const val HILT_NAVIGATION = "androidx.hilt:hilt-navigation:${DependenciesVersions.HILT_COMPOSE}"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${DependenciesVersions.HILT}"
    const val HILT_AGP = "com.google.dagger:hilt-android-gradle-plugin:${DependenciesVersions.HILT}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${DependenciesVersions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${DependenciesVersions.RETROFIT}"
    const val RETROFIT_KOTLIN_COROUTINE_ADAPTER = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${DependenciesVersions.RETROFIT_COROUTINE_ADAPTER_VERSION}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${DependenciesVersions.OKHTTP}"
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${DependenciesVersions.OKHTTP}"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${DependenciesVersions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${DependenciesVersions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${DependenciesVersions.ROOM}"

}