import java.util.ArrayList;
import java.util.List;

public class asntag {
    List<String> tags ;
    static String tag01 = "797";
    static String tag02 = "791925";
    static String tag03 = "79342";
    public asntag(){
        tags = new ArrayList<String>();
        tags.add(tag01);
        tags.add(tag02);
        tags.add(tag03);
    }
    boolean checker(String tagno){
        return tags.contains(tagno) ? true  : false;
    }
}
