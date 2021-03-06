package org.maxim.symmetriccryptography;

import org.maxim.symmetriccryptography.service.Encoder;
import org.maxim.symmetriccryptography.service.impl.PlayfairEncoder;
import org.maxim.symmetriccryptography.service.impl.PolynomialEncoder;
import org.maxim.symmetriccryptography.service.impl.RotaryGrilleEncoder;

public class App {

    private static final String DEFAULT_MESSAGE = "Hide the gold in the tree stump!";

    public static void main(String[] args) {
        Encoder[] encoders = {
                new PlayfairEncoder(),
                new PolynomialEncoder(),
                new RotaryGrilleEncoder()
        };

        String message = args.length == 0 ? DEFAULT_MESSAGE : args[0];
        for (Encoder encoder : encoders) {
            testEncoder(encoder, message);
        }
    }

    private static void testEncoder(Encoder encoder, String message) {
        System.out.println("\n---------------------------------\n");

        System.out.println("Testing: " + encoder);
        System.out.println("Original message: " + message);

        String encrypted = encoder.encrypt(message);
        System.out.println("Encrypted message: " + encrypted);

        String decrypted = encoder.decrypt(encrypted);
        System.out.println("Decrypted message: " + decrypted);

        System.out.println("\n---------------------------------\n");
    }

}
