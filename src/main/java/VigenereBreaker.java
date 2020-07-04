import java.util.*;

import edu.duke.*;
import lombok.Data;
import lombok.Setter;

public class VigenereBreaker {

    private @Setter int klenght=5;
    private @Setter char mostCommon='e';

    String dictionariesPath=System.getProperty("user.dir") + "\\src\\main\\resources\\dictionaries\\";


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

        FileResource fileResourceDictionary = new FileResource(dictionariesPath+"English");
        String decyripted = breakForLanguage(text,readDictionary(fileResourceDictionary));
        System.out.println(decyripted);
    }

    public HashSet  readDictionary(FileResource fr){
        HashSet<String> hashSet=new HashSet<String>();
        for(String s:fr.lines()){
                hashSet.add(s.toLowerCase());
        }
        return hashSet;
    }

    public Integer countWords(String message, HashSet<String> dictionary){
        int count=0;
        for(String s:message.split("\\W+")){
            if(dictionary.contains(s.toLowerCase())){
                count++;
            }
        }
        return count;
    }

    public String breakForLanguage(String encrypted,HashSet<String> dictionary ){
        int max=0;
        String realDecyripted=null;
        for(int i=1;i<100;i++){
            int[] key=tryKeyLength(encrypted,i,mostCommon);
            ICipher vigenereCipher=new VigenereCipher(key);
            String decyripted= vigenereCipher.decrypt(encrypted);
            int countRealWords=countWords(decyripted,dictionary);
            if(countRealWords>max){
                max=countRealWords;
                realDecyripted=decyripted;
            }
        }
        return realDecyripted;
    }

}
