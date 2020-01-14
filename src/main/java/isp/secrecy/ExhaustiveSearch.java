package isp.secrecy;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static fri.isp.Agent.hex;

/**
 * Implement a brute force key search (exhaustive key search) if you know that the
 * message is:
 * "I would like to keep this text confidential Bob. Kind regards, Alice."
 * <p>
 * Assume the message was encrypted with "DES/ECB/PKCS5Padding".
 * Also assume, the the key has be very poorly chosen. In particular, as an attacker,
 * you are certain that all bytes in the key, with the exception of th last three bytes,
 * have been set to 0.
 * <p>
 * The length of DES key is 8 bytes.
 * <p>
 * To manually specify a key, use the class {@link javax.crypto.spec.SecretKeySpec})
 */
public class ExhaustiveSearch {


    public ExhaustiveSearch() throws NoSuchAlgorithmException {
    }

    public static void main(String[] args) throws Exception {
        final String message = "I would like to keep this text confidential Bob. Kind regards, Alice.";
        System.out.println("[MESSAGE] " + message);

        final Key key = KeyGenerator.getInstance("DES").generateKey();

        // TODO

        final Cipher aes = Cipher.getInstance("DES/ECB/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, key);
        final byte[] ct = aes.doFinal(message.getBytes(StandardCharsets.UTF_8));
        final byte[] iv = aes.getIV();

        bruteForceKey(ct, message);

    }

    public static byte[] bruteForceKey(byte[] ct, String message) throws Exception {
        // TODO

        String desKey = "01234567"; // value from user
        byte[] keyBytes = desKey.getBytes();

        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        Key key = factory.generateSecret(new DESKeySpec(keyBytes));
        System.out.println(key.getEncoded());

        /*for(int b0 = 48; b0 < 122; b0++)
            if((char)b0 == (char) key.getEncoded()[3])
            for(int b1 = 48; b1 < 122; b1++)
                for(int b2 = 48; b2 < 122; b2++)
                    for(int b3 = 48; b3 < 122; b3++)
                        for(int b4 = 48; b4 < 122; b4++)
                            for(int b5 = 48; b5 < 122; b5++)
                                for(int b6 = 48; b6 < 122; b6++)
                                    for(int b7 = 48; b7 < 122; b7++) {
                                        System.out.println(key.getEncoded());
                                        System.out.println((char)key.getEncoded()[0]);
                                        System.out.println((char)key.getEncoded()[1]);
                                        System.out.println((char)key.getEncoded()[2]);
                                        System.out.println((char)key.getEncoded()[3]);
                                        System.out.println((char)key.getEncoded()[4]);
                                        System.out.println((char) b0 + "" + (char) b1 + (char) b2 + "" + (char) b3 + "" + (char) b4 + "" + (char) b5 + "" + (char) b6 + "" + (char) b7);
                                    }*/

        return null;
    }
}
