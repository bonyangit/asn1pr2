import java.util.ArrayList;
import java.util.List;

public class asntag {
    List<String> tags ;
    List<String> sequence ;
    static String tag01 = "797";
    static String tag02 = "791925";
    static String tag03 = "79342";
    public asntag(){
        tags = new ArrayList<String>();
        tags.add(tag01);
        tags.add(tag02);
        tags.add(tag03);

        sequence = new ArrayList<String>();
        sequence.add(tag02);
    }
    String checker(String tagno){
        if (sequence.contains(tagno)){
            return "2";
        }if (tags.contains(tagno)){
            return "1";
        }else {
            return "0";
        }
    }
}
