// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath("com.tencent.mm:AndResGuard-gradle-plugin:1.2.19") // Resource obfuscate
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/absinthe/libraries")
        maven("https://dl.bintray.com/rikkaw/Libraries")
        maven("https://dl.bintray.com/rikkaw/MaterialPreference")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
