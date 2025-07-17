pluginManagement {
<<<<<<< HEAD
    plugins {
        id("com.google.gms.google-services") version "4.4.1"
    }
=======
>>>>>>> c0bf7419bbb4cf659c123eb49f3de2938aab4afd
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

<<<<<<< HEAD
rootProject.name = "Carte de presentation"
=======
rootProject.name = "Planifa"
>>>>>>> c0bf7419bbb4cf659c123eb49f3de2938aab4afd
include(":app")
 