import org.bouncycastle.asn1.*;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.Enumeration;

public class asn1dump {

    static String dumpAsString(String inpvar, DERObject derobj,String tagno) {
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
                //stringbfr.append(lasttagno);
                //stringbfr.append(")");
                stringbfr.append(",");
                stringbfr.append(seprator);
                if (derobj2.isEmpty()) {
                    //stringbfr.append(")");
                    stringbfr.append(",");
                } else {
                    stringbfr.append(dumpAsString(inpvar, derobj2.getObject(),lasttagno));
                }
                return stringbfr.toString();
            } else {
                ASN1OctetString var9;
                if (derobj instanceof DEROctetString) {
                    var9 = (ASN1OctetString)derobj;
                    return inpvar + dumpBinaryDataAsString(inpvar, var9.getOctets()) + seprator ;

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
                            stringbfr.append(dumpAsString(strvar1,  (DERObject)obj1,lasttagno));
                        } else {
                            stringbfr.append(dumpAsString(strvar1, ((DEREncodable)obj1).getDERObject(),lasttagno));
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
}