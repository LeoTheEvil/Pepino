package com.pepino.automation.Controlador;

import com.pepino.automation.Modelo.Kappa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Validador {
    public void validar(Kappa kappa) {
        if (kappa.getNombre().isBlank()) {
            throw new ParametroIncorrecto("El nombre no puede ser vacio.");
        }
        if (kappa.getRango().isBlank()) {
            throw new ParametroIncorrecto("El rango no puede ser vacio.");
        }
        if (kappa.getClase().isBlank()) {
            throw new ParametroIncorrecto("La clase no puede ser vacia.");
        }
    }
}