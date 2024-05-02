plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.localmarket"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.localmarket"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.maps.android:android-maps-utils:2.3.0")

    implementation(fileTree(mapOf(
        "dir" to "C:\\Users\\rasta\\AppData\\Local\\Android\\Sdk\\platforms\\android-34",
        "include" to listOf(".jar", ".aar")
    )))

    implementation ("com.google.android.material:material:1.5.0")
    implementation(libs.firebase.firestore)
    implementation(libs.fragment.testing)
    implementation(libs.play.services.maps)
    implementation ("com.google.android.gms:play-services-location:18.0.0")


    // Use the central libs for version management if available
    testImplementation(libs.junit)
    testImplementation("org.mockito:mockito-core:5.10.0")
    testImplementation("org.robolectric:robolectric:4.7.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.powermock:powermock-api-mockito2:2.0.9")
    testImplementation("org.powermock:powermock-module-junit4:2.0.9")

    androidTestImplementation(libs.ext.junit) // Assuming this points to 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation(libs.espresso.core) // Assuming this points to 'androidx.test.espresso:espresso-core:3.4.0'
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test:rules:1.6.0-alpha03")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    androidTestImplementation ("org.mockito:mockito-android:2.24.5")

    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    androidTestImplementation(libs.espresso.contrib)
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")





}