plugins {
  id("com.android.application")
  id("kotlin-android")
}

android {
  compileSdk = 30
  buildToolsVersion = "30.0.3"

  defaultConfig {
    applicationId = "com.weicools.common"
    minSdk = 21
    targetSdk = 30
    versionCode = 1
    versionName = "1.0"
  }

  buildTypes {
    debug {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(AndroidXLibs.CoreKtx)
  implementation(AndroidXLibs.AppCompat)
  implementation(AndroidXLibs.FragmentKtx)
  implementation(AndroidXLibs.MaterialDesign)
  implementation(AndroidXLibs.ConstraintLayout)
}