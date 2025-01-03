package com.pepino.automation.Steps;

import com.pepino.automation.Modelo.Kappa;
import com.pepino.automation.Repositorio.RepositorioKappa;
import com.pepino.automation.Servicio.ExcepcionNoEncuentraKappa;
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
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
public class hireKappaSteps {
    Kappa kappa = new Kappa();
    Kappa kappa1 = new Kappa();
    Kappa kappa2 = new Kappa();
    Kappa kappa3 = new Kappa();
    List<Kappa> kappaList = new ArrayList<>();
    RepositorioKappa repositorioMock = Mockito.mock(RepositorioKappa.class);
    Exception error = null;
    private long idCounter = 1L;
    int statusCode;
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
    @Given("una kappa llamada Nitori Kawashiro")
    public void unaKappaLlamadaNitoriKawashiro() {
        kappa.setNombre("Nitori Kawashiro");
    }
    @Given("un kappa llamado Kappabunta")
    public void unKappaLlamadoKappabunta() {
        kappa.setNombre("Kappabunta");
    }
    @Given("un kappa llamado Kappataro")
    public void unKappaLlamadoKappataro() {
        kappa.setNombre("Kappataro");
    }
    @Given("un kappa sin nombre")
    public void unKappaSinNombre() {
        kappa.setNombre("");
    }
    @Given("un kappa llamado Comillas")
    public void unKappaLlamadoComillas() {
        kappa.setNombre("\"");
    }
    @Given("un id de kappa vacio")
    public void idKappaVacio() {
        kappa.setId(null);
    }
    @Given("de rango Kappitan")
    public void rangoKappitan()
    {
        kappa.setRango("Kappitan");
    }
    @Given("de rango Kappataz")
    public void rangoKappataz()
    {
        kappa.setRango("Kappataz");
    }
    @Given("de rango Kappa")
    public void rangoKappa()
    {
        kappa.setRango("Kappa");
    }
    @Given("de clase Samurai")
    public void claseSamurai() {
        kappa.setClase("Samurai");
    }
    @Given("de clase Ingeniero")
    public void claseIngeniero() {
        kappa.setClase("Ingeniero");
    }
    @Given("de clase Ninja")
    public void claseNinja() {
        kappa.setClase("Ninja");
    }
    @Given("de clase Arquero")
    public void claseArquero() {
        kappa.setClase("Arquero");
    }
    @Given("sin clase")
    public void sinClase() {
        kappa.setClase("");
    }
    @Given("es el primer kappa")
    public void primerKappa() {
        kappa1=kappa;
        servKappa.contratarKappa(kappa1);
    }
    @Given("es el segundo kappa")
    public void segundoKappa() {
        kappa2=kappa;
        servKappa.contratarKappa(kappa2);
    }
    @Given("es el tercer kappa")
    public void tercerKappa() {
        kappa3=kappa;
        servKappa.contratarKappa(kappa3);
    }
    @When("el kappa existe en la base de datos")
    public void kappaExisteEnBaseDeDatos() {
        servKappa.contratarKappa(kappa);
        // Verifica que el Kappa tiene un ID antes de realizar la búsqueda
        assert kappa.getId() != null : "El Kappa no tiene un ID asignado";
    }
    @When("el usuario hace un Post")
    public void usuarioHacePost() {
        servKappa.contratarKappa(kappa);
    }
    @When("el usuario hace un Get")
    public void usuarioHaceGet() {
        try {
            servKappa.buscarKappa(kappa.getId());
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Put de rango Kappitan")
    public void usuarioHacePutRangoKappitan() {
        kappa.setRango("Kappitan");
        servKappa.actualizarKappa(kappa.getId(), kappa);
    }
    @When("el usuario hace un Put de rango vacio")
    public void usuarioHacePutRangoVacio() {
        kappa.setRango("");
        servKappa.actualizarKappa(kappa.getId(), kappa);
    }
    @When("el usuario hace un Delete")
    public void usuarioHaceDelete() {
        servKappa.despedirKappa(kappa.getId());
    }
    @When("el usuario lista todos los kappas empezando por {int} en paginas de {int}")
    public void usuarioListaTodosKappas(int offset, int size) {
        servKappa.listarTodosKappas(offset,size);
    }
    @Then("el kappa es contratado")
    public void kappaContratado() {
        // Verifica que el Kappa tiene un ID antes de realizar la búsqueda
        assert kappa.getId() != null : "El Kappa no tiene un ID asignado";
        Optional<Kappa> kappaContratado = servKappa.buscarKappa(kappa.getId()); // Verifica que el Kappa esté guardado
        // Realiza la aserción aquí, por ejemplo:
        assert kappaContratado.isPresent() : "Kappa no encontrado en el repositorio";
        assert kappaContratado.get().getNombre().equals(kappa.getNombre()) : "Nombre del Kappa no coincide";
    }
    @Then("el kappa es ascendido a Kappitan")
    public void kappaAscendidoKappitan() {
        // Verifica que el Kappa tiene un ID antes de realizar la búsqueda
        assert kappa.getId() != null : "El Kappa no tiene un ID asignado";
        Optional<Kappa> kappaActualizado = servKappa.buscarKappa(kappa.getId());
        assert kappaActualizado.isPresent() : "Kappa no encontrado en el repositorio";
        // Realiza la aserción aquí, por ejemplo:
        assert kappaActualizado.get().getRango().equals("Kappitan") : "El rango del Kappa no coincide";
    }
    @Then("el kappa es despedido")
    public void kappaDespedido() {
        assert kappa.getId() == null : "El Kappa no pudo ser eliminado";
    }
    @Then("el kappa es rechazado")
    public void kappaRechazado() {
        assert kappa.getId() == null : "El Kappa no pudo ser eliminado";
    }
    @Then("mensaje de error {string}")
    public void kappaNoEncontrado(String mensajeEsperado) {
        if (error == null) {
            fail("Kappa encontrado");
        }
        assertEquals(mensajeEsperado, error.getMessage());
    }
    @Then("el ascenso es rechazado")
    public void ascensoRechazado() {
        // Verifica que el Kappa tiene un ID antes de realizar la búsqueda
        assert kappa.getId() != null : "El Kappa no tiene un ID asignado";
        Optional<Kappa> kappaActualizado = servKappa.buscarKappa(kappa.getId());
        assert kappaActualizado.isPresent() : "Kappa no encontrado en el repositorio";
        // Realiza la aserción aquí, por ejemplo:
        assert kappaActualizado.get().getRango().equals("Kappataz") : "El rango del Kappa no coincide";
    }
    @Then("muestra una lista de todos los kappas")
    public void muestraListaTodosKappas() {
        assert kappa1.getId() != null : "El Kappa no tiene un ID asignado";
        Optional<Kappa> kappaContratado1 = servKappa.buscarKappa(kappa1.getId()); // Verifica que el Kappa esté guardado
        assert kappaContratado1.isPresent() : "Kappa no encontrado en el repositorio";
        assert kappaContratado1.get().getNombre().equals(kappa1.getNombre()) : "Nombre del Kappa no coincide";
        assert kappa2.getId() != null : "El Kappa no tiene un ID asignado";
        Optional<Kappa> kappaContratado2 = servKappa.buscarKappa(kappa2.getId()); // Verifica que el Kappa esté guardado
        assert kappaContratado2.isPresent() : "Kappa no encontrado en el repositorio";
        assert kappaContratado2.get().getNombre().equals(kappa2.getNombre()) : "Nombre del Kappa no coincide";
        assert kappa3.getId() != null : "El Kappa no tiene un ID asignado";
        Optional<Kappa> kappaContratado3 = servKappa.buscarKappa(kappa3.getId()); // Verifica que el Kappa esté guardado
        assert kappaContratado3.isPresent() : "Kappa no encontrado en el repositorio";
        assert kappaContratado3.get().getNombre().equals(kappa3.getNombre()) : "Nombre del Kappa no coincide";
    }
}