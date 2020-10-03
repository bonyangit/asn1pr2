import org.bouncycastle.asn1.*;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class asn1dump {


    static String dumpAsString(String inpvar, DERObject derobj,String tagno,String value,asntag asntag,List<String> output,List<String> seqoutput) {
        //String seprator = System.getProperty("line.separator");
        String seprator = "";
        StringBuffer stringbfr;
        Enumeration seqEnum;
        String lasttagno = tagno;
        String strvar1;
        Object obj1;

        if (!(derobj instanceof ASN1Sequence)) {
            if (derobj instanceof DERTaggedObject) {
                stringbfr = new StringBuffer();
                stringbfr.append(inpvar);

                //stringbfr.append("(");
                DERTaggedObject derobj2 = (DERTaggedObject)derobj;
                lasttagno = tagno + "" + (Integer.toString(derobj2.getTagNo()));
                //stringbfr.append("("+lasttagno+")");
                //stringbfr.append(")");
                //stringbfr.append(",");
                stringbfr.append(seprator);
                if (derobj2.isEmpty()) {
                    //stringbfr.append(")");
                    //stringbfr.append(",");
                } else {
                    stringbfr.append(dumpAsString(inpvar, derobj2.getObject(),lasttagno,"",asntag,output,seqoutput));
                }
                return stringbfr.toString();
            } else {
                ASN1OctetString var9;

                String condition = asntag.checker(lasttagno);
                if(condition=="2") {
                    if (derobj instanceof DEROctetString) {
                        var9 = (ASN1OctetString) derobj;
                        //return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets()) + seprator;
                        seqoutput.add(dumpBinaryDataAsString(inpvar, var9.getOctets()));
                        return inpvar +dumpBinaryDataAsString(inpvar, var9.getOctets()) + "/";
                    }
                }else if(condition=="1"){
                    if (derobj instanceof DEROctetString) {
                        var9 = (ASN1OctetString) derobj;
                        //return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets()) + seprator;
                        output.add(dumpBinaryDataAsString(inpvar, var9.getOctets()));
                        return inpvar +dumpBinaryDataAsString(inpvar, var9.getOctets()) + "/";

                    }
                }else {
                    if (derobj instanceof DEROctetString) {
                        var9 = (ASN1OctetString) derobj;
                        //return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets()) + seprator;
                        return inpvar +dumpBinaryDataAsString(inpvar, var9.getOctets()) + "/";

                    }
                }





            }
        } else {
            stringbfr = new StringBuffer();
            seqEnum = ((ASN1Sequence)derobj).getObjects();
            strvar1 = inpvar + "";
            stringbfr.append(inpvar);
            stringbfr.append(seprator);


            while(true) {
                while(seqEnum.hasMoreElements()) {
                    obj1 = seqEnum.nextElement();
                    if (obj1 != null && !obj1.equals(new DERNull())) {

                            if (obj1 instanceof DERObject) {
                                stringbfr.append(dumpAsString(strvar1,  (DERObject)obj1,lasttagno,"",asntag,output,seqoutput));
                            } else {
                                stringbfr.append(dumpAsString(strvar1, ((DEREncodable)obj1).getDERObject(),lasttagno,"",asntag,output,seqoutput));
                            }

                    } else {
                        stringbfr.append(strvar1);
                        stringbfr.append("NULL");
                    }
                }

                return stringbfr.toString();
            }
        }
        return seprator;
    }

    private static String dumpBinaryDataAsString(String var0, byte[] var1) {
        StringBuffer var3 = new StringBuffer();
        for(int var4 = 0; var4 < var1.length; var4 += 32) {
            if (var1.length - var4 > 32) {
                var3.append(new String(Hex.encode(var1, var4, 32)));
                var3.append(calculateAscString(var1, var4, 32));
            } else {
                var3.append(var0);
                //var3.append(new String(Hex.encode(var1, var4, var1.length - var4)));
                var3.append(calculateAscString(var1, var4, var1.length - var4));

            }
        }
        return var3.toString();
    }

    private static String calculateAscString(byte[] var0, int var1, int var2) {
        StringBuffer var3 = new StringBuffer();
        for(int var4 = var1; var4 != var1 + var2; ++var4) {
            if (var0[var4] >= 32 && var0[var4] <= 126) {
                var3.append((char)var0[var4]);
            }
        }
        return var3.toString();
    }

    static List<String> listCombiner(List<String> seq , List<String> oth){
        List <String> out = new ArrayList<String>();
        for(int i = 0; i<seq.size();i++){
            StringBuffer mybfer = new StringBuffer();

            for(int j = 0; j<oth.size();j++){
                mybfer.append(oth.get(j));
                mybfer.append(",");
            }
            mybfer.append(seq.get(i));
            out.add(mybfer.toString());
        }
        return out;
    }
}