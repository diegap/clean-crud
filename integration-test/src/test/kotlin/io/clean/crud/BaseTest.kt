package io.clean.crud

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.LogMessageWaitStrategy
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import java.time.Duration

abstract class BaseTest : AbstractTestNGSpringContextTests() {

    companion object {
        const val MONGO_PORT = 27017

        val mongoContainer: KGenericContainer = KGenericContainer("mongo:latest")
                .withExposedPorts(MONGO_PORT)
                .waitingFor(LogMessageWaitStrategy().withRegEx(".* waiting for connections on port 27017.*\\s"))
                .withStartupTimeout(Duration.of(60, java.time.temporal.ChronoUnit.SECONDS))

        @BeforeSuite
        fun startContainers() {
            mongoContainer.start()
        }

        @AfterSuite
        fun shutdownContainers() {
            mongoContainer.stop()
        }

    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            val mongoPort = "spring.data.mongodb.port=${mongoContainer.getMappedPort(MONGO_PORT)}"

            val values = TestPropertyValues.of("it", mongoPort)
            values.applyTo(applicationContext)

        }
    }
}


class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
