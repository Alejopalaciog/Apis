package com.tcs.certificacion.capaapi.endpoints;

public class ReqresEndpoint {

    public static final String CREATE_USERS = "api/users";

    public static String  obtenerEndpoint(String endpoint){
        switch(endpoint){
            case "crear_usuarios":
                return CREATE_USERS;
            default:
                break;
        }
        return "";
    }

}
