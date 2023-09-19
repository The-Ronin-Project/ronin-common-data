rootProject.name = "ronin-common-data-root"

// libraries
include(":common")

// catalog
include(":ronin-common-data-catalog")

findProject(":ronin-common-data-catalog")?.name = "ronin-common-data"

pluginManagement {
    repositories {
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-public/")
        }
        mavenLocal()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-public/")
        }
        mavenLocal()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("roningradle") {
            from("com.projectronin.services.gradle:ronin-gradle-catalog:2.3.6")
        }
    }
}
