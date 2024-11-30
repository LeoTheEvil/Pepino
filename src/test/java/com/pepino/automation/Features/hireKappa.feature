Feature: Hire_Kappa

  Scenario: Hire a new kappa

    Given un kappa llamado Kappasuke
    And de rango Kappitan
    And de clase Samurai
    When el usuario hace un Post
    Then el kappa es contratado