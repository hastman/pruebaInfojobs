package es.angel.pruebaInfojobs.helper;

import es.angel.pruebaInfojobs.exception.InternalError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

public class CryptoHelper {

    public static String hashData(String data) {
        try {
            return Arrays.toString(MessageDigest.getInstance("MD5")
                    .digest(Optional.ofNullable(data).orElse("").getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error creating Hash password " + e.toString());
            throw new InternalError();
        }
    }
}
