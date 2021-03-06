plugins {
    kotlin("js") version "1.4.0"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.4.0"
}

group = "com.drrufus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js {
        //useCommonJs()
        //nodejs()
        browser {
            testTask {
                enabled = false
            }
        }
        binaries.executable()
    }
    sourceSets {
        main {
            kotlin.srcDir("src")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:1.0-M1-1.4.0-rc")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.8")
    implementation(npm("node-fetch", "2.6.0"))
    //testImplementation("org.jetbrains.kotlin:kotlin-test-js")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile>().configureEach {
    kotlinOptions {
        moduleKind = "commonjs"
    }
}

/*tasks.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsExec>().named("nodeRun") {
    workingDir = project.buildDir.resolve("js/packages/kjs4aws")
}*/
