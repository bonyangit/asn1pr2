import org.bouncycastle.asn1.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class Main {
/*
inp_schema = [3]4,5,2,3.1,7,3.2
inp = [3]4,5,2,7,3.1,3.2

1- init= object by tag no

*/

    public static void main(String[] args) {
        run(args);
        //test();
    }

    public static void run(String[] args){
        /*if (args.length < 4
                || !(args[0].equals("-i") || args[0].equals("-o"))
                || !(args[2].equals("-i") || args[2].equals("-o")) || args[0].equals(args[2])) {
            System.out.println(
                    "Example :  \n java -jar app.jar -i inputFilePath -o outputdirectory");
            return;
        }*/

        String inFile = "/Users/khashayarnorouzi/Desktop/G_GECRHA01_ERHpgwcdr20200915235545000007779_07786658.dat";
        String outDir = "/Users/khashayarnorouzi/Desktop";

        /*if (args[0].equals("-i")) {
            inFile = args[1];
            outDir = args[3];
        } else {
            inFile = args[3];
            outDir = args[1];
        }*/

        File f = new File(inFile);
        if (!f.exists()) {
            System.out.println("input file does not exist : " + inFile);
            return;
        }

        File dir = new File(outDir);
        if (!dir.exists()) {
            System.out.println("directory does not exist : " + outDir);
            return;
        }
        try {
            //ASN1InputStream ais = new ASN1InputStream(new FileInputStream(new File("/Users/khashayarnorouzi/IdeaProjects/asn1nifiprocessor/coded.dat")));
            ASN1InputStream ais = new ASN1InputStream(new FileInputStream(new File(inFile)));

            File ff = new File(outDir,f.getName()+".csv" );
            BufferedWriter writer = new BufferedWriter(new FileWriter(ff, false));

            AsnTags AsnTags = new AsnTags();
            while (ais.available() > 0) {
                //for (int i = 0; i < 5; i++) {
                List<String> output = new ArrayList<String>();
                List<String> seqOutput = new ArrayList<String>();
                DERObject obj = ais.readObject();
                //System.out.println(asn1dump.dumpAsString("",obj,"","",asntag,output,seqOutput));
                Asn1Dump.dumpAsString("", obj, "", "", AsnTags, output, seqOutput);

                List<String> newlist = AsnTags.listCombiner(seqOutput, output);
                //System.out.println(newlist.size());
                for (int m = 0; m < newlist.size(); m++) {
                    //System.out.println(newlist.get(m) + ",");
                    writer.append(newlist.get(m) + "\n");
                }

            }
            ais.close();
            writer.close();


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void test(){

        try {
            ASN1InputStream ais = new ASN1InputStream(new FileInputStream(new File("/Users/khashayarnorouzi/IdeaProjects/asn1nifiprocessor/coded.dat")));

            AsnTags AsnTags = new AsnTags();
            while (ais.available() > 0) {
                //for (int i = 0; i < 5; i++) {
                List<String> output = new ArrayList<String>();
                List<String> seqOutput = new ArrayList<String>();
                DERObject obj = ais.readObject();
                //System.out.println(asn1dump.dumpAsString("",obj,"","",asntag,output,seqOutput));
                Asn1Dump.dumpAsString("", obj, "", "", AsnTags, output, seqOutput);

                List<String> newlist = AsnTags.listCombiner(seqOutput, output);
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
