Feature: Hire_Kappa

  Scenario: Hire a new kappa

    Given un kappa llamado Kappasuke de rango Kappitan y clase Samurai
    When el usuario hace clic en Post
    Then el kappa es contratado