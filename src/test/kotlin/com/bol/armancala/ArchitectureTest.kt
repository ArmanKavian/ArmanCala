package com.bol.armancala

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.Test


class ArchitectureTest {
    @Test
    fun testArchitecture() {
        val importedClasses = ClassFileImporter().importPackages(BASE_PACKAGE)
        val controllerAccessesRepository: ArchRule = Architectures.layeredArchitecture()
            .layer("Controller").definedBy("$BASE_PACKAGE.controller..")
            .layer("UseCase").definedBy("$BASE_PACKAGE.usecase.impl..")
            .layer("Repository").definedBy("$BASE_PACKAGE.repository..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("UseCase").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("UseCase")
        controllerAccessesRepository.check(importedClasses)
    }

    companion object {
        private const val BASE_PACKAGE = "com.bol.armancala"
    }
}