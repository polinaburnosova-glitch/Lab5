plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
     implementation("javax.xml.bind:jaxb-api:2.3.1")
     implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")
}

application {
    mainClass.set("org.example.Main")
}

tasks.test {
    useJUnit()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}