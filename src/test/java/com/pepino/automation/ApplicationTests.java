package com.pepino.automation;

import com.pepino.automation.Modelo.Kappa;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    @LocalServerPort
    private int port=8081;

    @Test
    void debe_contratar_un_kappa_y_buscarlo_por_su_ID() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Kappasuke");
        kappa.setRango("Kappitan");
        kappa.setClase("Samurai");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).when().get("/api/kappa/"+id).then().body("id", equalTo(id));
    }
    @Test
    void debe_actualizar_un_kappa() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Nitori Kawashiro");
        kappa.setRango("Kappataz");
        kappa.setClase("Ingeniero");
        Long id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Long.class);
        kappa.setRango("Kappitan");
        given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().put("/api/kappa/"+id).then().statusCode(200);
        given().port(port).when().get("/api/kappa/"+id).then().body("rango", equalTo("Kappitan"));
    }
    @Test
    void debe_despedir_un_kappa() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Kappabunta");
        kappa.setRango("Kappa");
        kappa.setClase("Ninja");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).when().delete("/api/kappa/"+id).then().statusCode(204);
        given().port(port).when().get("/api/kappa/"+id).then().statusCode(404);
    }
    @Test
    void debe_listar_todos_los_kappas() {
        Kappa kappa1 = new Kappa();
        kappa1.setNombre("Kappasuke");
        kappa1.setRango("Kappitan");
        kappa1.setClase("Samurai");
        int id1 = given().port(port).body(kappa1).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        Kappa kappa2 = new Kappa();
        kappa2.setNombre("Nitori Kawashiro");
        kappa2.setRango("Kappataz");
        kappa2.setClase("Ingeniero");
        int id2 = given().port(port).body(kappa2).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).queryParam("offset", 0).queryParam("size", 100).when().get("/api/kappa/").then().body("$",
                hasItem(
                        allOf(
                                hasEntry("id", id1)
                        )
                )
        ).body("$",
                hasItem(
                        allOf(
                                hasEntry("id", id2)
                        )
                )
        );
    }
    @Test
    void debe_fallar_en_contratar_un_kappa_por_nombre_vacio() {
        Kappa kappa = new Kappa();
        kappa.setNombre("");
        kappa.setRango("Kappa");
        kappa.setClase("Ninja");
        given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(400);
    }
    @Test
    void debe_fallar_en_contratar_un_kappa_por_clase_vacia() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Kappabunta");
        kappa.setRango("Kappa");
        kappa.setClase("");
        given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(400);
    }
    @Test
    void debe_fallar_en_encontrar_un_kappa() {
        given().port(port).when().get("/api/kappa/900").then().statusCode(404);
    }
    @Test
    void debe_fallar_en_actualizar_un_kappa() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Nitori Kawashiro");
        kappa.setRango("Kappataz");
        kappa.setClase("Ingeniero");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        kappa.setClase("");
        given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().put("/api/kappa/"+id).then().statusCode(400);
    }
    @Test
    void debe_fallar_en_despedir_un_kappa() {
        given().port(port).when().delete("/api/kappa/901").then().statusCode(404);
    }
    @Test
    void debe_mandar_una_A_en_el_offset() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Kappako");
        kappa.setRango("Kappa");
        kappa.setClase("Sacerdote");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).queryParam("offset", "A").when().get("/api/kappa/").then().statusCode(400);
    }
    @Test
    void debe_mandar_un_mas_en_el_size() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Kappataro");
        kappa.setRango("Kappa");
        kappa.setClase("Arquero");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).queryParam("size", "+").when().get("/api/kappa/").then().statusCode(400);
    }
    @Test
    void debe_mandar_un_0_en_el_size() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Kappariki");
        kappa.setRango("Kappataz");
        kappa.setClase("Hechicero");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).queryParam("size", "0").when().get("/api/kappa/").then().statusCode(400);
    }
    @Test
    void debe_mandar_un_menos_1_en_el_offset() {
        Kappa kappa = new Kappa();
        kappa.setNombre("Kappaburo");
        kappa.setRango("Kappa");
        kappa.setClase("Vigia");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).queryParam("offset", "-1").when().get("/api/kappa/").then().statusCode(400);
    }
    @Test
    void debe_mandar_una_comilla_en_el_nombre() {
        Kappa kappa = new Kappa();
        kappa.setNombre("\"");
        kappa.setRango("Kappa");
        kappa.setClase("Ninja");
        int id = given().port(port).body(kappa).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
    }
    @Test
    void debe_paginar_la_lista_de_kappas() {
        Kappa kappa1 = new Kappa();
        kappa1.setNombre("Kappasuke");
        kappa1.setRango("Kappitan");
        kappa1.setClase("Samurai");
        int id1 = given().port(port).body(kappa1).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        Kappa kappa2 = new Kappa();
        kappa2.setNombre("Nitori Kawashiro");
        kappa2.setRango("Kappataz");
        kappa2.setClase("Ingeniero");
        int id2 = given().port(port).body(kappa2).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        Kappa kappa3 = new Kappa();
        kappa3.setNombre("Kappataro");
        kappa3.setRango("Kappa");
        kappa3.setClase("Arquero");
        int id3 = given().port(port).body(kappa3).contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).when().post("/api/kappa").then().statusCode(201)
                .extract().jsonPath().getObject("id",Integer.class);
        given().port(port).queryParam("offset", 0).queryParam("size", 2).when().get("/api/kappa/").then().body("size()",is(2));
    }
}