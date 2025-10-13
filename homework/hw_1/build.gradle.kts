plugins {
    id("java");
    id("org.springframework.boot") version "3.2.0";
    id("io.spring.dependency-management") version "1.1.4";
}

group = "zoo"
version = "1.0.0"

java{
    sourceCompatibility = JavaVersion.VERSION_17;
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testRuntimeOnly("org.mockito:mockito-core:5.8.0")
}

tasks.withType<Test>() {
    useJUnitPlatform()
    testLogging{
        events("passed", "skipped", "failed");
    }
}