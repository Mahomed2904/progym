/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author mahom
 */
public class Status {
    private final int statusCode;
    private final String message;

    public Status(int statusCode, String mesage) {
        this.statusCode = statusCode;
        
        if(statusCode == 0)
            this.message = "Sucesso: " + mesage;
        else
            this.message = "Erro: " + mesage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMesage() {
        return message;
    }
}
