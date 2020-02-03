import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String args[]) {

        //Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> map = new TreeMap<>();
        map.put(21, 2);
        map.put(33, 5);
        map.put(44, 10);
        map.put(22, 8);
        Stream<Map.Entry<Integer, Integer>> stream = map.entrySet().stream();
        stream.forEach((entry) -> {
            // if(entry.getValue() % 2 == 0){
            System.out.println(entry);
            // }
        });
        System.out.println(map);
            /*MyThread mt = new MyThread();
            mt.start();
            //for (int i = 0; i < 50; i++)
            //  System.out.println ("i = " + i + ", i * i = " + i * i);
            try {
                Thread.sleep(2); // Sleep for 10 milliseconds
            } catch (InterruptedException e) {
            }
            System.out.println("pi = " + mt.pi);
             */
        MyThread mt = new MyThread();
        mt.start();
        try {
            mt.join();
        } catch (InterruptedException e) {
        }
        System.out.println("pi = " + mt.pi);
    }
}
class MyThread extends Thread
{
    boolean negative = true;
    double pi; // Initializes to 0.0, by default
    public void run ()
    {
        for (int i = 3; i < 100000; i += 2)
        {
            if (negative)
                pi -= (1.0 / i);
            else
                pi += (1.0 / i);
            negative = !negative;
        }
        pi += 1.0;
        pi *= 4.0;
        System.out.println ("Finished calculating PI");
    }
}
