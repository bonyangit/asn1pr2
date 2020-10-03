import org.bouncycastle.asn1.*;
import org.bouncycastle.util.encoders.Hex;

import java.util.Enumeration;
import java.util.List;

public class Asn1Dump {

    static AsnTags asntg;

    static String dumpAsString(String inpvar, DERObject derobj, String tagno, String value, AsnTags AsnTags, List<String> output, List<String> seqoutput) {
        //String seprator = System.getProperty("line.separator");
        String seprator = "";
        StringBuffer stringbfr;
        Enumeration seqEnum;
        String lasttagno = tagno;
        String strvar1;
        Object obj1;
        asntg = AsnTags;

        if (!(derobj instanceof ASN1Sequence)) {
            if (derobj instanceof DERTaggedObject) {
                stringbfr = new StringBuffer();
                stringbfr.append(inpvar);

                DERTaggedObject derobj2 = (DERTaggedObject) derobj;
                lasttagno = tagno + "" + (Integer.toString(derobj2.getTagNo()));

                stringbfr.append(seprator);
                if (derobj2.isEmpty()) {
                    //stringbfr.append(")");
                    //stringbfr.append(",");
                } else {
                    stringbfr.append(dumpAsString(inpvar, derobj2.getObject(), lasttagno, "", AsnTags, output, seqoutput));
                }
                return stringbfr.toString();
            } else {
                ASN1OctetString var9;

                String condition = AsnTags.checker(lasttagno);
                if (condition == "2") {
                    if (derobj instanceof DEROctetString) {
                        var9 = (ASN1OctetString) derobj;
                        //return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets()) + seprator;
                        seqoutput.add(dumpBinaryDataAsString(inpvar, var9.getOctets(), lasttagno));
                        return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets(), lasttagno) + "/";
                    }
                } else if (condition == "1") {
                    if (derobj instanceof DEROctetString) {
                        var9 = (ASN1OctetString) derobj;
                        //return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets()) + seprator;
                        output.add(dumpBinaryDataAsString(inpvar, var9.getOctets(), lasttagno));
                        return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets(), lasttagno) + "/";

                    }
                } else {
                    if (derobj instanceof DEROctetString) {
                        var9 = (ASN1OctetString) derobj;
                        //return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets()) + seprator;
                        return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets(), lasttagno) + "/";

                    }
                }

            }
        } else {
            stringbfr = new StringBuffer();
            seqEnum = ((ASN1Sequence) derobj).getObjects();
            strvar1 = inpvar + "";
            stringbfr.append(inpvar);
            stringbfr.append(seprator);


            while (true) {
                while (seqEnum.hasMoreElements()) {
                    obj1 = seqEnum.nextElement();
                    if (obj1 != null && !obj1.equals(new DERNull())) {

                        if (obj1 instanceof DERObject) {
                            stringbfr.append(dumpAsString(strvar1, (DERObject) obj1, lasttagno, "", AsnTags, output, seqoutput));
                        } else {
                            stringbfr.append(dumpAsString(strvar1, ((DEREncodable) obj1).getDERObject(), lasttagno, "", AsnTags, output, seqoutput));
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

    private static String dumpBinaryDataAsString(String var0, byte[] var1, String lasttagno) {
        StringBuffer var3 = new StringBuffer();
        for (int var4 = 0; var4 < var1.length; var4 += 32) {
            var3.append(asntg.decoder(lasttagno, new String(Hex.encode(var1, var4, var1.length - var4))));
        }
        return var3.toString();
    }

}