import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
    alias(libs.plugins.vanniktech.maven.publish)
}

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
                    url.set("https://github.com/jalaljankhaan/AZDigitizer")
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
                        url.set("https://github.com/jalaljankhaan/AZDigitizer")
                    }
                }
            }
        }

        // Repository
        repositories {
            maven {
                name = "Sonatype"
                url = uri("")
            }
        }
    }

    // Configure publishing to Maven Central
    //publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    //signAllPublications()
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
                    url.set("https://github.com/jalaljankhaan/AZDigitizer")
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
                        url.set("https://github.com/jalaljankhaan/AZDigitizer")
                    }
                }
            }
        }

        // **Configure publishing to Maven Central**
        repositories {
            maven {
                // Update URL based on your Sonatype environment (staging or release)
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

                credentials {
                    username = project.findProperty("sonatypeUsername") as String
                    password = project.findProperty("sonatypePassword") as String
                }
            }
        }
    }

    // **Enable GPG signing for all publications**
    signing {
        sign(publishing.publications)
    }
}