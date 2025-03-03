import deps.androidTestDeps
import deps.androidx
import deps.debugTestDeps
import deps.hilt
import deps.okHttp
import deps.retrofit
import deps.testDebugDeps
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
  id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
  namespace = "com.khun.domain"
}

dependencies {
  androidx()
  okHttp()
  retrofit()
  hilt()
  testDeps()
  androidTestDeps()
  debugTestDeps()
  testImplDeps()
  testDebugDeps()
}
