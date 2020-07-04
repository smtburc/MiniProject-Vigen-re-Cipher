import java.util.*;

import edu.duke.*;
import lombok.Data;
import lombok.Setter;

public class VigenereBreaker {

    private @Setter int klenght=5;
    private @Setter char mostCommon='e';

    public String sliceString(String message, int whichSlice, int totalSlices) {
        String result = "";
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            result += message.charAt(i);
        }
        return result;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker caesarCracker = new CaesarCracker();
        for (int i = 0; i < klength; i++) {
            key[i]=caesarCracker.getKey(sliceString(encrypted,i,klength));
        }
        return key;
    }

    public void breakVigenere() {
        FileResource fileResource=new FileResource();
        String text=fileResource.asString();
        int[] key=tryKeyLength(text,klenght,mostCommon);
        ICipher vigenereCipher=new VigenereCipher(key);
        String decyripted= vigenereCipher.decrypt(text);
        System.out.println(decyripted);
    }

}
