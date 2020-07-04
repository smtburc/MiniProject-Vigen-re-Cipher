import java.util.*;

import edu.duke.*;
import lombok.Data;
import lombok.Setter;

public class VigenereBreaker {


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

        HashMap<String,HashSet<String>> dictionary=new HashMap<>();

        ArrayList<String> langList=new ArrayList<>( Arrays.asList("English", "Danish", "Dutch", "French", "German", "Italian", "Portuguese", "Spanish"));
        for(String lang:langList){
            dictionary.put(lang,readDictionary(new FileResource(dictionariesPath+lang)));
        }

        breakForAllLangs(text,dictionary);
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
            int[] key=tryKeyLength(encrypted,i,mostCommonCharIn(dictionary));
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

    public Character mostCommonCharIn(HashSet<String> dictionary){

        Map<Character,Integer> chars=new HashMap<>();
        for(String word:dictionary){
            for(int i=0;i<word.length();i++){
                char c=word.charAt(i);
                if(!chars.keySet().contains(c)){
                    chars.put(c,1);
                }else{
                    chars.put(c,chars.get(c)+1);
                }
            }
        }

        int max=0;
        Character mostCommonChar=null;
        for(Character c:chars.keySet()){
            if(chars.get(c)>max){
                max=chars.get(c);
                mostCommonChar=c;
            }
        }
        return mostCommonChar;

    }

    public void breakForAllLangs(String encyripted, HashMap<String,HashSet<String>> languages){

        int max=0;
        String realDecyripted=null;
        String realLanguage=null;
        for(String lang:languages.keySet()){
            String decyripted =breakForLanguage(encyripted,languages.get(lang));
            int countWords=countWords(decyripted,languages.get(lang));
            if(countWords>max){
                max=countWords;
                realDecyripted=decyripted;
                realLanguage=lang;
            }
        }
        System.out.println(realLanguage);
        System.out.println(realDecyripted);
    }

}
