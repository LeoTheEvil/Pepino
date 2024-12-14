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
    When el usuario hace un Put
    Then el kappa es ascendido a Kappitan

  Scenario: Dismiss a kappa

    Given un kappa llamado Kappabunta
    And de rango Kappa
    And de clase Ninja
    When el usuario hace un Delete
    Then el kappa es despedido