import java.util.*;
import java.util.stream.Stream;

public class Main {
        public static void main (String args[]){

            Map<Integer,Integer> map = new TreeMap<>();
            map.put(21,2);
            map.put(33,5);
            map.put(44,10);
            map.put(22,8);


            Stream<Map.Entry<Integer, Integer>> stream = map.entrySet().stream();
            stream.forEach((entry) -> {
               // if(entry.getValue() % 2 == 0){
                    System.out.println(entry);
               // }
            });

            System.out.println(map);

    }

}
