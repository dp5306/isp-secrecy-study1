package isp.secrecy;

import fri.isp.Agent;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.Key;

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
    public static void main(String[] args) throws Exception {
        final String message = "I would like to keep this text confidential Bob. Kind regards, Alice.";
        System.out.println("[MESSAGE] " + message);
        // TODO
        final byte[] mySecret = {(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 2, (byte) 255}; //byte can have 255 values..
        System.out.println("[Key]: " + Agent.hex(mySecret));
        // System.out.println(mySecret.length);
        Key secretKeySpec = new SecretKeySpec(mySecret, "DES");
        System.out.println(Agent.hex(secretKeySpec.getEncoded()));
        Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
        encrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        final byte[] cipherText = encrypt.doFinal(message.getBytes());

        byte[] originalKey = bruteForceKey(cipherText, message);
        System.out.println("Found key: " + Agent.hex(originalKey));
        Key key = new SecretKeySpec(originalKey, "DES");
        System.out.println(Agent.hex(key.getEncoded()));
        Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
        decrypt.init(Cipher.DECRYPT_MODE, key);
        try{
            byte[] possibleMessage = decrypt.doFinal(cipherText);
            System.out.println("[decrypted with found key]: "+new String(possibleMessage));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] bruteForceKey(byte[] ct, String message) throws Exception {
        // TODO
        byte[] possibleKey = {(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0}; // byte is from 0 to 255
        // only last tree bytes need to be searched...
        for (int i = 0; i <= 255; i++){
            for (int j = 0; j <= 255; j++){
                for (int k = 0; k <= 255; k++){
                    possibleKey[7] = (byte) k;
                    possibleKey[6] = (byte) j;
                    possibleKey[5] = (byte) i;

                    Key secretKey = new SecretKeySpec(possibleKey, "DES");
                    Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
                    decrypt.init(Cipher.DECRYPT_MODE, secretKey);
                    try{
                        byte[] possibleMessage = decrypt.doFinal(ct);
                        if (new String(possibleMessage).equals(message)) {
                            System.out.println("key that decrypt message");
                            for (int a = 0; a < possibleKey.length; a++) {
                                System.out.println(possibleKey[a]);
                            }
                            return possibleKey;
                        }
                    }catch (Exception e){
                        continue;
                    }
                }
            }
        }

        return null;
    }
}
