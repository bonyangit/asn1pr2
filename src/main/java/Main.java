import org.bouncycastle.asn1.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class Main {
/*
inp_schema = [3]4,5,2,3.1,7,3.2
inp = [3]4,5,2,7,3.1,3.2

1- init= object by tag no

*/

    public static void main(String[] args) {
        try {
            ASN1InputStream ais = new ASN1InputStream(
                    new FileInputStream(new File("/Users/khashayarnorouzi/IdeaProjects/asn1nifiprocessor/coded.dat")));

            AsnTags AsnTags = new AsnTags();
            while (ais.available() > 0) {
            //for (int i = 0; i < 5; i++) {
                List<String> output = new ArrayList<String>();
                List<String> seqOutput = new ArrayList<String>();
                DERObject obj = ais.readObject();
                //System.out.println(asn1dump.dumpAsString("",obj,"","",asntag,output,seqOutput));
                Asn1Dump.dumpAsString("", obj, "", "", AsnTags, output, seqOutput);

                List<String> newlist = AsnTags.listCombiner(seqOutput,output);
                //System.out.println(newlist.size());
                for (int m = 0; m < newlist.size(); m++) {
                    System.out.println(newlist.get(m) + ",");
                }
            }
            ais.close();


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
