package com.pepino.automation.Steps;

import com.pepino.automation.Modelo.Kappa;
import com.pepino.automation.Repositorio.RepositorioKappa;
import com.pepino.automation.Servicio.ServicioKappa;
import com.pepino.automation.Servicio.ServicioKappaImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CucumberContextConfiguration
public class hireKappaSteps {
    Kappa kappa = new Kappa();
    List<Kappa> kappaList = new ArrayList<>();
    RepositorioKappa repositorioMock = Mockito.mock(RepositorioKappa.class);
    private long idCounter = 1L;
    {
        Mockito.when(repositorioMock.save(Mockito.any(Kappa.class))).thenAnswer(invocation -> {
            Kappa kappaToSave = invocation.getArgument(0);
            if (kappaToSave.getId() == null) { // Verifica si el ID es null antes de asignarlo
                kappaToSave.setId(idCounter++); // Asigna un ID único
            }
            kappaList.add(kappaToSave); // Simula el almacenamiento en una lista
            return kappaToSave; // Devuelve el mismo Kappa como si fuera guardado
        });

        Mockito.when(repositorioMock.findById(Mockito.anyLong())).thenAnswer(invocation -> {
            long id = invocation.getArgument(0);
            // Busca el Kappa en la lista y lo envuelve en un Optional
            return kappaList.stream().filter(k -> k.getId() != null && k.getId() == id).findFirst().map(Optional::of).orElse(Optional.empty());
        });
    }
    ServicioKappa servKappa = new ServicioKappaImpl(repositorioMock);
    @Given("un kappa llamado Kappasuke")
    public void unKappaLlamadoKappasuke() {
        kappa.setNombre("Kappasuke");
    }
    @Given("de rango Kappitan")
    public void rangoKappitan()
    {
        kappa.setRango("Kappitan");
    }
    @Given("de clase Samurai")
    public void claseSamurai() {
        kappa.setClase("Samurai");
    }
    @When("el usuario hace un Post")
    public void usuarioHacePost() {
        servKappa.contratarKappa(kappa);
    }
    @Then("el kappa es contratado")
    public void kappaContratado() {
        // Verifica que el Kappa tiene un ID antes de realizar la búsqueda
        assert kappa.getId() != null : "El Kappa no tiene un ID asignado";
        Optional<Kappa> kappaContratado = servKappa.buscarKappa(kappa.getId()); // Verifica que el Kappa esté guardado
        // Realiza la aserción aquí, por ejemplo:
        assert kappaContratado.isPresent() : "Kappa no encontrado en el repositorio";
        assert kappaContratado.get().getNombre().equals("Kappasuke") : "Nombre del Kappa no coincide";
    }
}