import deps.androidTestDeps
import deps.androidx
import deps.dataModule
import deps.debugTestDeps
import deps.hilt
import deps.retrofit
import deps.room
import deps.testDeps
import plugs.SharedLibraryGradlePlugin

plugins {
  id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
  namespace = "com.khun.login"
}

dependencies {
  androidx()
  retrofit()
  dataModule()
  hilt()
  room()
  testDeps()
  androidTestDeps()
  debugTestDeps()
}
