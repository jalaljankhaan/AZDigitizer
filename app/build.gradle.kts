import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

val sonatypeUsername: String = localProperties.getProperty("sonatypeUsername")
val sonatypePassword: String = localProperties.getProperty("sonatypePassword")
val sonatypeMavenCentralUrl: String = localProperties.getProperty("sonatypeMavenCentralUrl")

val signingKeyId: String = localProperties.getProperty("signing.keyId")
val signingPassword: String = localProperties.getProperty("signing.password")
val signingSecretKeyRingFile: String = localProperties.getProperty("signing.secretKeyRingFile")

val projectRepository: String = localProperties.getProperty("projectRepository")

android {
    namespace = "com.jalaljankhan.azdigitizer"
    compileSdk = 35

    defaultConfig {
        //applicationId = "com.jalaljankhan.azdigitizer"
        minSdk = 21
        lint.targetSdk = 35
        //versionCode = 1
        //versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        viewBinding = true
    }

    signingConfigs {
        create("release") {
            keyAlias = signingKeyId
            keyPassword = signingPassword
            storeFile = file(signingSecretKeyRingFile)
            storePassword = signingPassword
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("aar") {
                from(components["release"])
                groupId = "com.jalaljankhan"
                artifactId = "azdigitizer"
                version = "1.0"

                pom {
                    name.set("AZDigitizer")
                    description.set("AZDigitizer is a simple library developed in kotlin to help the developers to draw different patterns on their android devices to test the screen touch.")
                    url.set(projectRepository)
                    inceptionYear.set("2024")

                    // Set License
                    licenses {
                        license {
                            name.set("The MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    // Developer's Information
                    developers {
                        developer {
                            id.set("jalaljankhaan")
                            name.set("Jalal Jan Khan")
                            email.set("jalaljankhan@outlook.com")
                        }
                    }

                    // Specify SCM
                    scm {
                        url.set(projectRepository)
                    }
                }
            }
        }

        // **Configure publishing to Maven Central**
        repositories {
            maven {
                // Update URL based on your Sonatype environment (staging or release)
                url = uri(sonatypeMavenCentralUrl)

                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
            }
        }
    }

    // **Enable GPG signing for all publications**
    signing {
        sign(publishing.publications)
    }
}