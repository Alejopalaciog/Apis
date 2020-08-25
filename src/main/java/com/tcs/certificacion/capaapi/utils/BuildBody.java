package com.tcs.certificacion.capaapi.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class BuildBody {
    private String rutaPantilla;

    public BuildBody(String rutaPantilla) {
        this.rutaPantilla = rutaPantilla;
    }

    public static BuildBody conLaPlantilla(String plantilla) {
        return new BuildBody(plantilla);
    }

    public String yLosValores(Map<String, String> valores) {
        String nuevaPlantilla = parseJson(rutaPantilla);
        for (Map.Entry<String, String> entry : valores.entrySet()) {
            String k = entry.getKey();
            Object a = entry.getValue();
            if (a == null) {
                a = "";
            }
            String v = a.toString();
            String key = "${" + k + "}";
            nuevaPlantilla = nuevaPlantilla.replace(key, v);
        }
        return nuevaPlantilla;
    }

    public String parseJson(String ruta) {
        String resultStr = "";
        try {
            resultStr = readFileAsString(ruta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    public static String readFileAsString(String fileName) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public String conValoresNuevos(Map<String, String> valores) {
        String nuevaPlantilla = parseJson(rutaPantilla);
        for (Map.Entry<String, String> entry : valores.entrySet()) {
            String k = entry.getKey();
            Object a = entry.getValue();
            if (a == null) {
                a = "";
            }
            String v = a.toString();
            String key = "\"" + k + "\":.\"(.*\")";
            String vAux = "\"" + k + "\": \"" + v + "\"";
            if (k.equals("Pais")) {
                key = "\"" + k + "\":(.*)";
                vAux = "\"" + k + "\":" + v;
            }
            nuevaPlantilla = nuevaPlantilla.replaceAll(key, vAux);
        }
        return nuevaPlantilla;
    }

    public static String conLosValoresDelObjeto(String body, String objeto, Map<String, String> campos) {

        JSONObject jo = new JSONObject(body);
        JSONObject objectDelJson = (JSONObject) jo.get(objeto);

        for (Map.Entry<String, String> entry : campos.entrySet()) {
            objectDelJson.put(entry.getKey(), entry.getValue());
        }
        jo = jo.put(objeto, objectDelJson);
        return jo.toString();

    }
}
