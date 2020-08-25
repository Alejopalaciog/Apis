package com.tcs.certificacion.capaapi.task;

import com.tcs.certificacion.capaapi.endpoints.ReqresEndpoint;
import com.tcs.certificacion.capaapi.utils.BuildBody;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.*;

public class Crear implements Task {

    private Map<String,String> datos;
    @Override
    public <T extends Actor> void performAs(T actor) {
        String body = BuildBody.conLaPlantilla("src\\test\\resources\\templates\\crear_usuarios.json").yLosValores(datos);
            actor.attemptsTo(Post.to(ReqresEndpoint.obtenerEndpoint("crear_usuarios"))
                    .with(request -> request
                            .header("Content-Type", "application/json")
                    .body(body)));

            String response = SerenityRest.lastResponse().getBody().asString();
        System.out.println(response);

        actor.attemptsTo(Get.resource());

    }

    public static Crear unUsuario(){
        return instrumented(Crear.class);
    }

    public Crear conLosDatos(Map<String,String> datos){
        this.datos = datos;
        return this;
    }
}
