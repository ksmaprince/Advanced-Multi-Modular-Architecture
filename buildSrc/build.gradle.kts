plugins{
    `kotlin-dsl`
}

repositories{
    google()
    mavenCentral()
}

//Lecture - 12
dependencies{
    api(kotlin("gradle-plugin:1.7.20"))
    implementation("com.android.tools.build:gradle:8.7.3")
}