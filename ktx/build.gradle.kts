plugins {
  id("com.android.library")
  id("kotlin-android")
}

android {
  compileSdk = 30
  buildToolsVersion = "30.0.3"

  defaultConfig {
    minSdk = 16
    targetSdk = 30

    consumerProguardFiles("consumer-rules.pro")
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
  implementation(AndroidXLibs.Annotation)
  implementation(AndroidXLibs.MaterialDesign)
  implementation(AndroidXLibs.ConstraintLayout)
}