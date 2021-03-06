package isp.secrecy;

import fri.isp.Agent;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

/**
 * EXERCISE:
 * - Study the example
 * - Play with different ciphers
 * <p>
 * - Homework: Oscar intercepts the message and would like to decrypt the ciphertext. Help Oscar to
 * decrypt the cipher text using brute force key search (exhaustive key search) if Oscar knows
 * that Alice has send the following message "I would like to keep this text confidential Bob. Kind regards, Alice."
 * (Known-plaintext attack) (Use DES and manually set a poor key; class {@link javax.crypto.spec.SecretKeySpec})
 * <p>
 * https://docs.oracle.com/javase/10/security/java-cryptography-architecture-jca-reference-guide.htm
 */
public class SymmetricCipherExample {
    // STREAM CIPHERS
    // RC4

    // BLOCK CIPHERS
    // DES with padding: "DES/ECB/PKCS5Padding"
    // Tripple DES with padding: "DESede/ECB/PKCS5Padding"
    // AES in ECB with padding: "AES/ECB/PKCS5Padding"
    // AES in CBC with padding, "AES/CBC/PKCS5Padding"
    // AES in CTR without padding: "AES/CTR/NoPadding"

    public static void main(String[] args) throws Exception {
        final String message = "I would like to keep this text confidential Bob. Kind regards, Alice.";
        System.out.println("[MESSAGE] " + message);

        // STEP 1: Alice and Bob agree upon a cipher and a shared secret key
        final Key key = KeyGenerator.getInstance("RC4").generateKey();

        final byte[] clearText = message.getBytes();
        System.out.println("[PT] " + Agent.hex(clearText));

        //  STEP 2: Create a cipher, encrypt the PT and, optionally, extract cipher parameters (such as IV)
        final Cipher encryption = Cipher.getInstance("RC4");
        encryption.init(Cipher.ENCRYPT_MODE, key);
        final byte[] cipherText = encryption.doFinal(clearText);

        // STEP 3: Print out cipher text (in HEX) [this is what an attacker would see]
        System.out.println("[CT] " + Agent.hex(cipherText));

        /*
         * STEP 4.
         * The receiver creates a Cipher object, defines the algorithm, the secret key and
         * possibly additional parameters (such as IV), and then decrypts the cipher text
         */
        final Cipher decryption = Cipher.getInstance("RC4");
        Key key2 = KeyGenerator.getInstance("RC4").generateKey();
        decryption.init(Cipher.DECRYPT_MODE, key);
        final byte[] decryptedText = decryption.doFinal(cipherText);
        System.out.println("[PT] " + Agent.hex(decryptedText));

        decryption.init(Cipher.DECRYPT_MODE, key2);
        final byte[] decryptedText2 = decryption.doFinal(cipherText);
        System.out.println("[PT2] " + Agent.hex(decryptedText2));

        // Todo: What happens if the key is incorrect? (Try with RC4 or AES in CTR mode)

        // STEP 5: Create a string from a byte array
        System.out.println("[MESSAGE] " + new String(decryptedText));
        System.out.println("[MESSAGE2] " + new String(decryptedText2));
    }
}
