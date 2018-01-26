package es.angel.pruebaInfojobs.helper;

import es.angel.pruebaInfojobs.exception.InternalError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class SecurityHelper {

    public static String hashData(String data) {
        try {
            return Arrays.toString(MessageDigest.getInstance("MD5").digest(data.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error creating Hash password " + e.toString());
            throw new InternalError();
        }
    }

    public static String uuidForSession() {
        return UUID.randomUUID().toString();
    }
}
