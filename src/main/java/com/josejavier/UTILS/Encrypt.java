package com.josejavier.UTILS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  Clase Encrypt encargada de encriptar la contraseña
 *  en SHA-256 para la base de datos
 *
 */
public class Encrypt {
    /**
     *  Metodo para encriptar la contraseña en SHA-256 para la base de datos
     * @param password contraseña a encriptar
     * @return contraseña encriptada en SHA-256
     * @throws NoSuchAlgorithmException excepción en caso de que no exista el algoritmo
     */
    public static String Encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();
        StringBuilder sb = new StringBuilder();
        for(byte b: hashedPassword){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }
}
