package com.tcs.certificacion.capaapi.stepdefinition;

import com.tcs.certificacion.capaapi.task.Crear;
import cucumber.api.java.Before;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.it.Ma;
import io.restassured.parsing.Parser;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;
import org.hamcrest.Matchers;


import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class PruebaApiDefinition {

    private EnvironmentVariables envVars;

    @Before
    public void preparacionDelEscenario() throws IllegalArgumentException{
        String base_url = envVars.optionalProperty("bases_urls.base_url").orElseThrow(IllegalArgumentException::new);
        String proxy_ip = envVars.optionalProperty("network.proxy_ip").orElseThrow(IllegalArgumentException::new);
        int proxy_port = Integer.parseInt(envVars.optionalProperty("network.proxy_port").orElseThrow(IllegalArgumentException::new));
        SerenityRest.useRelaxedHTTPSValidation("TLS");
        SerenityRest.setDefaultParser(Parser.JSON);
        SerenityRest.setDefaultProxy(proxy_ip,proxy_port);
        setTheStage(new OnlineCast());
        theActorCalled("El Analista").whoCan(CallAnApi.at(base_url));

    }
    @Cuando("el analista consume el recurso crear usuario")
    public void elAnalistaConsumeElRecursoCrearUsuario(List<Map<String,String>> datos) {
        theActorInTheSpotlight().remember("nombre", datos.get(0).get("name"));
        theActorInTheSpotlight().attemptsTo(Crear.unUsuario().conLosDatos(datos.get(0)));
    }

    @Entonces("el deberia ver en el codigo de estado {int}")
    public void elDeberiaVerEnElCodigoDeEstado(int statusCode) {
        theActorInTheSpotlight().should(seeThatResponse(lastResponse -> lastResponse.statusCode(statusCode)));
    }

    @Entonces("validar el nombre")
    public void validarElNombre() {
        String nombre = theActorInTheSpotlight().recall("nombre");
        theActorInTheSpotlight().should(seeThatResponse(lastResponse -> lastResponse.body("name", Matchers.is(nombre))));
    }


}
