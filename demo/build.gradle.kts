import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.json:json:20230227")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.0")
	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.test {
	useJUnitPlatform()
}

tasks.withType<Test> {
	useJUnitPlatform()
}
