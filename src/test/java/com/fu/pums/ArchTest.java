package com.fu.pums;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.fu.pums");

        noClasses()
            .that()
                .resideInAnyPackage("com.fu.pums.service..")
            .or()
                .resideInAnyPackage("com.fu.pums.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.fu.pums.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
