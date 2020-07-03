/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 *
 * @author Samet Burç
 */

import edu.duke.FileResource;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class AllTest {

    ICipher caesarCipher;
    CaesarCracker caesarCracker;

    ICipher vigenereCipher;
    VigenereBreaker vigenereBreaker;

    String path;

    @Before
    public void initialize() {
        caesarCipher=new CaesarCipher(3);
        caesarCracker=new CaesarCracker();
        vigenereCipher=new VigenereCipher(new int[]{17, 14, 12, 4});
        vigenereBreaker=new VigenereBreaker();
        path=System.getProperty("user.dir") + "\\src\\main\\resources\\data\\";
   }

    @Test
    public void testEncryptAndDecryptCaesarCipher(){

        FileResource fr = new FileResource(path+"titus-small.txt");

        System.out.println("testing encryptAndDecryptCaesarCipher...");
        System.out.println();
        String encrypted=caesarCipher.encrypt(fr.asString());
        System.out.println(encrypted);
        System.out.println("");
        String decrypted=caesarCipher.decrypt(encrypted);
        System.out.println(decrypted);
    }


    @Test
    public void testDecryptCaesarCrackerEnglish(){

        FileResource fr = new FileResource(path+"titus-small_key5.txt");

        System.out.println("testing decryptCaesarCrackerEnglish...");
        System.out.println();
        String encrypted=caesarCracker.decrypt(fr.asString());
        System.out.println(encrypted);

    }

    @Test
    public void testDecryptCaesarCrackerPortuguese(){

        System.out.println("testing decryptCaesarCrackerPortuguese...");
        System.out.println();
        FileResource fr = new FileResource(path+"oslusiadas_key17.txt");

        caesarCracker=new CaesarCracker("a".charAt(0));

        String encrypted=caesarCracker.decrypt(fr.asString());
        System.out.println(encrypted);
    }


    @Test
    public void testEncryptVigenereCipher(){
        FileResource fr = new FileResource(path+"titus-small.txt");

        System.out.println("testing encryptVigenereCipher...");
        System.out.println();

        String encrypted=vigenereCipher.encrypt(fr.asString());
        System.out.println(encrypted);
    }

    @Test
    public void testSliceString(){

        System.out.println("testing sliceString...");
        System.out.println();

        String result=vigenereBreaker.sliceString("abcdefghijklm",0,3);
        System.out.println(result);
    }

    @Test
    public void testTryKeyLength(){

        System.out.println("testing tryKeyLength...");
        System.out.println();

        FileResource fr = new FileResource(path+"athens_keyflute.txt");

        int[] result=vigenereBreaker.tryKeyLength(fr.asString(),5,"a".charAt(0));
        for(int i=0;i<result.length;i++){
            System.out.print(result[i]+" ");

        }
    }

    @Test
    public void testBreakVigenere(){

        System.out.println("testing breakVigenere...");
        System.out.println();

        vigenereBreaker.breakVigenere();
    }

}
