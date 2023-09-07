pluginManagement {
	repositories {
		google()
		maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public") }
		mavenCentral()
		gradlePluginPortal()
	}
}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public") }
		mavenCentral()
	}
}

rootProject.name = "admob-native-templates"
include(":sample")
include(":native-templates")
