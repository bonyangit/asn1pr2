import org.bouncycastle.asn1.*;

import java.io.File;
import java.io.FileInputStream;


public class main {
/*
inp_schema = [3]4,5,2,3.1,7,3.2
inp = [3]4,5,2,7,3.1,3.2

1- init= object by tag no
*/

    public static void main(String[] args) {
        try {
             ASN1InputStream ais = new ASN1InputStream(
                    new FileInputStream(new File("/Users/khashayarnorouzi/IdeaProjects/asn1nifiprocessor/coded.dat")));

            //while (ais.available() > 0) {
            for (int i=0;i<3;i++){
                DERObject obj = ais.readObject();
                System.out.println(asn1dump.dumpAsString("",obj,""));

            }
            ais.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
