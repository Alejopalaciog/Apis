package com.tcs.certificacion.capaapi.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src\\test\\resources\\features\\prueba_api.feature", glue =
        "com.tcs.certificacion.capaapi.stepdefinition", snippets = SnippetType.CAMELCASE,
        tags = "")
public class PruebaApi {
}
