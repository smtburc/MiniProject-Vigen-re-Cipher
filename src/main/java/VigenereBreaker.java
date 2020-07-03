import java.util.*;

import edu.duke.*;

public class VigenereBreaker {
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
        int[] key=tryKeyLength(text,5,"e".charAt(0));
        ICipher vigenereCipher=new VigenereCipher(key);
        String decyripted= vigenereCipher.decrypt(text);
        System.out.println(decyripted);
    }

}
