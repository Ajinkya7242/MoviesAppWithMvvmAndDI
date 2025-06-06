plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("org.sonarqube")
}

sonar {
    properties {
        property("sonar.projectKey", "pirateflix_1")
        property("sonar.organization", "myOrganization")
        property("sonar.language", "kotlin")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.projectName", "pirateflix")
        property("sonar.token", "sqp_4a6a64d5a39d5fa3aef9b40464259f11b9cd7074")
        property("sonar.sources", "src/main/")
        property("sonar.gradle.skipCompile", "true")
        property("sonar.exclusions", "**/generated/**, **/databinding/**")

    }
}

android {
    namespace = "com.example.pirateflix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pirateflix"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding=true
    }

    lint {
        disable += "NotificationPermission"
    }

}



dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.airbnb.android:lottie:4.1.0")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")


}