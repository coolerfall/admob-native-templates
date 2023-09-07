@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.com.android.library)
	alias(libs.plugins.org.jetbrains.kotlin.android)
	alias(libs.plugins.maven.publish)
}

android {
	namespace = "com.coolerfall.admob.templates"
	compileSdk = 34

	defaultConfig {
		minSdk = 19

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
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
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {
	implementation(libs.core.ktx)
	compileOnly(libs.play.services.ads)
	implementation(libs.appcompat)
	implementation(libs.material)
}