import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    java
    groovy
    id("org.springframework.boot") version "2.5.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("com.diffplug.spotless") version "5.0.0"
}

group = "com.amex.order"

repositories {
    mavenCentral()
}


java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

val springCloudVersion = "2020.0.3"
val testcontainersVersion = "1.15.3"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-guava")

    implementation("org.springdoc:springdoc-openapi-ui:1.5.9")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")

    testImplementation("org.codehaus.groovy:groovy-all:3.0.5")
    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.0-groovy-3.0")

    testImplementation("org.testcontainers:postgresql:1.15.3")
    testImplementation("org.testcontainers:spock:1.15.3")
    testImplementation("io.rest-assured:rest-assured")
}

configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${testcontainersVersion}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}


testSets {
    create("test-integration")
}
