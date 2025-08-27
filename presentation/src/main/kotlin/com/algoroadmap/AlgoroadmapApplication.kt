package com.algoroadmap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.algoroadmap"])
class AlgoroadmapApplication

fun main(args: Array<String>) {
    runApplication<AlgoroadmapApplication>(*args)
}