Feature: Hire_Kappa

  Scenario: Hire a new kappa

    Given un kappa llamado Kappasuke
    And de rango Kappitan
    And de clase Samurai
    When el usuario hace un Post
    Then el kappa es contratado

  Scenario: Update a kappa

    Given una kappa llamada Nitori Kawashiro
    And de rango Kappataz
    And de clase Ingeniero
    When el kappa existe en la base de datos
    And el usuario hace un Put de rango Kappitan
    Then el kappa es ascendido a Kappitan

  Scenario: Dismiss a kappa

    Given un kappa llamado Kappabunta
    And de rango Kappa
    And de clase Ninja
    When el kappa existe en la base de datos
    And el usuario hace un Delete
    Then el kappa es despedido

  Scenario: Fail to hire a kappa due to empty name

    Given un kappa sin nombre
    And de rango Kappa
    And de clase Ninja
    When el usuario hace un Post
    Then el kappa es rechazado

  Scenario: Fail to hire a kappa due to empty class

    Given un kappa llamado Kappabunta
    And de rango Kappa
    And sin clase
    When el usuario hace un Post
    Then el kappa es rechazado

  Scenario: Fail to hire a kappa due to invalid name
    Given un kappa llamado Comillas
    And de rango Kappa
    And de clase Ninja
    When el usuario hace un Post
    Then el kappa es rechazado

  Scenario: Fail to find a kappa
    Given un id de kappa vacio
    When el usuario hace un Get
    Then mensaje de error "Kappa no encontrado"

  Scenario: Fail to update a kappa due to empty new class
    Given una kappa llamada Nitori Kawashiro
    And de rango Kappataz
    And de clase Ingeniero
    When el kappa existe en la base de datos
    And el usuario hace un Put de rango vacio
    Then el ascenso es rechazado

  Scenario: Fail to dismiss a kappa
    Given un id de kappa vacio
    When el usuario hace un Delete
    Then mensaje de error "Kappa no encontrado"