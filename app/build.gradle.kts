plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "edu.SpaceLearning.SpaceEnglish"
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.SpaceLearning.SpaceEnglish"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-ads:24.4.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.readystatesoftware.sqliteasset:sqliteassethelper:2.0.1")
    implementation("com.google.android.play:review:2.0.2")
    implementation("com.itextpdf:itext7-core:9.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.0")
    //implementation("com.mikepenz:fastadapter:5.7.0")
    implementation("androidx.annotation:annotation:1.9.1")
    //implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.2")
    //implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.2")
    implementation("androidx.activity:activity:1.10.1")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")


        // Import the BoM for the Firebase platform
        implementation(platform("com.google.firebase:firebase-bom:33.16.0"))

        // Add the dependency for the Firebase Authentication library
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation("com.google.firebase:firebase-auth")

        // Also add the dependencies for the Credential Manager libraries and specify their versions
        implementation("androidx.credentials:credentials:1.5.0")
        implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
        implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")


}
