
import java.util.*;


public class Generics {

    public static void main(String args[])throws Exception
    {
        HashMap<String,String> a= new HashMap<>();
        HashMap<String,String> b= new HashMap<>();

        try {
             a =  a.add("vikash", "Singh");
              a.add("Vickey", "Singh");

            System.out.println(a.get("vikash"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
