plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm")
    id("com.diffplug.spotless") version "6.25.0"
    id("jacoco")
    id("org.sonarqube") version "3.5.0.2730"
}

group = "com.ahmedharis"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    jacocoTestReport {
        dependsOn("test")
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }

    sonarqube {
        properties {
            property("sonar.projectKey", "ahmedmirza994_currency-exchange")
            property("sonar.organization", "ahmedmirza994")
            property("sonar.host.url", "https://sonarcloud.io")
            property("sonar.java.binaries", file("build/classes/java/main"))
            property("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco/test/jacocoTestReport.xml")
        }
    }
}

spotless {
    java {
        target("src/**/*.java")
        googleJavaFormat()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("runAllChecks") {
    dependsOn("spotlessCheck", "test", "jacocoTestReport")
}
