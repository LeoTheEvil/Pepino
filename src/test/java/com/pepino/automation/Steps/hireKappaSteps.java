package com.pepino.automation.Steps;

import com.pepino.automation.Modelo.Kappa;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.equalTo;

public class hireKappaSteps {
    Kappa kappa = new Kappa();
    Long id;
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
        id=given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
        .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
        .extract().jsonPath().getObject("id",Long.class);
    }
    @Then("el kappa es contratado")
    public void kappaContratado() {
        given().port(port).when().get("/api/kappa/"+id).then().body("id", equalTo(id));
    }
}