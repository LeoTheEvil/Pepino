package com.pepino.automation.Servicio;

import com.pepino.automation.Modelo.Kappa;

import java.util.List;
import java.util.Optional;

public interface ServicioKappa {
    Kappa contratarKappa(Kappa kappa);
    List<Kappa> listarTodosKappas(int offset, int size);
    Optional<Kappa> buscarKappa(Long idKappa);
    Kappa actualizarKappa(Long id, Kappa kappaAModificar);
    boolean despedirKappa(Long idKappa);
}