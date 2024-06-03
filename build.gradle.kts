plugins {
    kotlin("jvm") version "1.9.23"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.9.0")
    testImplementation("io.kotest:kotest-assertions-core:5.9.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.named<JavaExec>("run") {
    description = "Runs application without command line arguments"
    mainClass = "MainKt"
}

tasks.register<JavaExec>("interact") {
    group = "application"
    description = "Runs application with -i command line argument"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass = "MainKt"
    standardInput = System.`in`
    args = listOf("-i")
    doFirst {
        println("\nRunning Baccarat with -i command line argument...\n")
    }
}
