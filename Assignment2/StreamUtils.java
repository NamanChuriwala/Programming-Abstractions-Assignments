import java.util.*;
import java.util.stream.Collectors;

public class StreamUtils {

    public static List<String> capitalized(Collection<String> strings){
        return strings.stream().filter(s -> Character.isUpperCase(s.charAt(0))).collect(Collectors.toList());
    }

    public static String longest(Collection<String> strings, boolean from_start){
        return strings.stream().reduce("", (s1,s2) -> {
            if(s1.length() > s2.length()){return s1;}
            else if (s1.length()<s2.length()){return s2;}
            else {
                if(from_start) {return s1;}
                else {return s2;}
            }
        });
    }

    public static <T extends Comparable<T>> T least(Collection<T> items, boolean from_start){
        return items.stream().reduce(items.iterator().next(), (e1,e2) -> {
            if(e1.compareTo(e2)<0){return e1;}
            else if (e1.compareTo(e2)>0){return e2;}
            else {
                if(from_start) {return e1;}
                else {return e2;}
            }
        });
    }
    public static <K, V> List<String> flatten(Map<K, V> aMap) {
        return aMap.entrySet().stream().map(e->e.getKey().toString().concat(" -> ").concat(e.getValue().toString())).collect(Collectors.toList());
    }

    public static void main(String[] args) {

        Collection<String> l = new ArrayList<>();
        System.out.println(capitalized(l));
    }

}
