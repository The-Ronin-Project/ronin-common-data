plugins {
    alias(roningradle.plugins.buildconventions.kotlin.library)
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.micrometer.statsd)
    api("com.projectronin.fhir:common-fhir-r4-models:1.3.0")
}
