plugins {
    alias(roningradle.plugins.buildconventions.kotlin.library)
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.micrometer.statsd)
    api(libs.common.fhir.r4.models)

    testImplementation(libs.assertj)
}
