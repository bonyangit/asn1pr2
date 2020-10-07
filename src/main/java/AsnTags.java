import org.bouncycastle.util.encoders.Hex;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class AsnTags {
    List<String> tags;
    List<String> sequence;

    static String servedIMSI = "793";
    static String IPBinV4Address = "7940";
    static String dataVolumeGPRSUplink = "79123";
    static String dataVolumeGPRSDownlink = "79124";
    static String recordOpeningTime = "7913";
    static String duration = "7914";
    static String causeForRecClosing = "7915";
    static String nodeID = "7918";
    static String url = "791925";
    static String serviceCode = "791920";
    static String localSequenceNumber = "79344";
    static String servedMSISDN = "7922";
    static String servedIMEISV = "7929";
    static String downlinkVolume = "791922";
    static String uplinkVolume = "791921";
    static String chargingRuleBaseName = "79342";
    static String ratingGroup = "79341";


    public AsnTags() {
        tags = new ArrayList<String>();
        tags.add(servedIMSI);
        tags.add(IPBinV4Address);
        tags.add(dataVolumeGPRSUplink);
        tags.add(dataVolumeGPRSDownlink);
        tags.add(recordOpeningTime);
        tags.add(duration);
        tags.add(causeForRecClosing);
        tags.add(nodeID);
        //tags.add(url);
        tags.add(serviceCode);
        tags.add(localSequenceNumber);
        tags.add(servedMSISDN);
        tags.add(servedIMEISV);
        tags.add(downlinkVolume);
        tags.add(uplinkVolume);
        tags.add(chargingRuleBaseName);
        tags.add(ratingGroup);

        sequence = new ArrayList<String>();
        sequence.add(url);
    }

    String checker(String tagno) {
        if (sequence.contains(tagno)) {
            return "2";
        }
        if (tags.contains(tagno)) {
            return "1";
        } else {
            return "0";
        }
    }

    String decoder(String tagno, String hex) {
        if (servedIMSI.equals(tagno)) {
            return TBCDStringToString(hex);
        } else if (IPBinV4Address.equals(tagno)) {
            return HexToIp(hex);
        } else if (dataVolumeGPRSUplink.equals(tagno)) {
            return String.valueOf(Integer.parseInt(hex, 16));
        } else if (dataVolumeGPRSDownlink.equals(tagno)) {
            return String.valueOf(Integer.parseInt(hex, 16));
        } else if (recordOpeningTime.equals(tagno)) {
            return HexToTime(hex);
        } else if (duration.equals(tagno)) {
            return hex;
        } else if (causeForRecClosing.equals(tagno)) {
            return hex;
        } else if (nodeID.equals(tagno)) {
            return hexToAscii(hex);
        } else if (url.equals(tagno)) {
            return hexToAscii(hex);
        } else if (serviceCode.equals(tagno)) {
            return String.valueOf(Integer.parseInt(hex, 16));
        } else if (localSequenceNumber.equals(tagno)) {
            return String.valueOf(Integer.parseInt(hex, 16));
        } else if (servedMSISDN.equals(tagno)) {
            return TBCDStringToString(hex.substring(hex.length() - 12));
        } else if (servedIMEISV.equals(tagno)) {
            return TBCDStringToString(hex);
        } else if (downlinkVolume.equals(tagno)) {
            return String.valueOf(Integer.parseInt(hex, 16));
        } else if (uplinkVolume.equals(tagno)) {
            return String.valueOf(Integer.parseInt(hex, 16));
        } else if (chargingRuleBaseName.equals(tagno)) {
            return hexToAscii(hex);
        } else if (ratingGroup.equals(tagno)) {
            return String.valueOf(Integer.parseInt(hex, 16));
        }

        return "";
        //return "|Tag:"+tagno+"|hex:"+hex+"|Size:"+hex.length()+"\n";
        //return TBCDStringToString(hex);
    }

    public static String TBCDStringToString(String s) {
        if (s.length() % 2 != 0) {
            return s;
        } else {
            //System.out.println(s);
            char[] charArray = s.toCharArray();

            String result = "";
            for (int i = 0; i < charArray.length; i += 2) {
                result += String.valueOf(charArray[i + 1]) + String.valueOf(charArray[i]);
            }

            charArray = result.toCharArray();
            boolean firstNonDigitReached = false;
            result = "";

            for (int i = 0; i < charArray.length; i++) {

                if (!Character.isDigit(charArray[i]) && !firstNonDigitReached) {
                    firstNonDigitReached = true;
                }

                if (firstNonDigitReached) {
                    if (charArray[i] != 'F' && charArray[i] != 'f') {
                        //System.out.println(s+"input string is not in correct format");
                    }
                } else
                    //System.out.println(s+" | valid: "+result);
                    result += String.valueOf(charArray[i]);

            }

            return result;
        }
    }

    public static String HexToIp(String address) {
        InetAddress a = null;
        try {
            a = InetAddress.getByAddress(DatatypeConverter.parseHexBinary(address));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return a.toString();
    }

    public static String HexToTime(String hex) {
        //char[] charArray = hex.toCharArray();
        return "20" + hex.substring(0, 2) + "-" + hex.substring(2, 4) + "-" + hex.substring(4, 6) + " " + hex.substring(6, 8) + ":" + hex.substring(8, 10) + ":" + hex.substring(10, 12)
                + "+" + hex.substring(14, 16) + ":" + hex.substring(16, 18);
    }

    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    static List<String> listCombiner(HashMap<String, String> seq, HashMap<String, String> oth) {
        List<String> out = new ArrayList<String>();
        if (seq.size() == 0) {
            StringBuffer mybfer = new StringBuffer();

            for (int j = 0; j < oth.size(); j++) {
                //mybfer.append(oth.);
                mybfer.append(",");
            }
            out.add(mybfer.toString());
        } else {
            for (int i = 0; i < seq.size(); i++) {
                StringBuffer mybfer = new StringBuffer();


                Map<String, String> treeMap = new TreeMap<String, String>(oth);
                for (String str : treeMap.keySet()) {
                    System.out.println(str);
                }
                Iterator hmIterator = oth.entrySet().iterator();
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry) hmIterator.next();
                    String marks = (String) mapElement.getValue();
                    System.out.println(mapElement.getKey() + " : " + marks);
                }

                for (int j = 0; j < oth.size(); j++) {

                    mybfer.append(oth.get(j));
                    mybfer.append(",");
                }
                mybfer.append(seq.get(i));
                out.add(mybfer.toString());
            }
        }

        return out;
    }

    public HashMap<String, String> templateBuilder() {
        HashMap<String, String> map = new HashMap<>();

        map.put(servedIMSI, " ");
        map.put(IPBinV4Address, " ");
        map.put(dataVolumeGPRSUplink, " ");
        map.put(dataVolumeGPRSDownlink, " ");
        map.put(recordOpeningTime, " ");
        map.put(duration, " ");
        map.put(causeForRecClosing, " ");
        map.put(nodeID, " ");
        //map.put(url," ");
        map.put(serviceCode, " ");
        map.put(localSequenceNumber, " ");
        map.put(servedMSISDN, " ");
        map.put(servedIMEISV, " ");
        map.put(downlinkVolume, " ");
        map.put(uplinkVolume, " ");
        map.put(chargingRuleBaseName, " ");
        map.put(ratingGroup, " ");

        return map;

    }

    public String builder(HashMap<String, String> output, List<String> seq) {
        StringBuilder strb = new StringBuilder("");
        HashMap<String, String> template = templateBuilder();
        int m = 0;
        if (seq.size() == 0) {
            for (String str : output.keySet()) {
                if (template.get(str) != null) {
                    template.replace(str, output.get(str));
                }
            }

            Map<String, String> treeMap = new TreeMap<String, String>(template);

            for (String str : treeMap.keySet()) {
                strb.append(treeMap.get(str) + ",");
            }
            strb.append("\r\n");
        } else {
            for (String i : seq) {
                for (String str : output.keySet()) {
                    if (template.get(str) != null) {
                        template.replace(str, output.get(str));
                    }
                }

                Map<String, String> treeMap = new TreeMap<String, String>(template);
                for (String str : treeMap.keySet()) {
                    strb.append(treeMap.get(str) + ",");
                }

                strb.append(i + "\r\n");
            }
        }
        return strb.toString();

    }
}
