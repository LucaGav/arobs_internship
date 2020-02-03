import synchdemo.FinancialTransaction;
import synchdemo.TransactionThread;
import timerdemo.InputThread;
import waitndemo.Consumer;
import waitndemo.Producer;
import waitndemo.Shared;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String args[]) {
        FinancialTransaction ft = new FinancialTransaction();
        System.out.println("-----Synch Demo-----");
        TransactionThread t1 = new TransactionThread(ft,"Deposit");
        TransactionThread t2 = new TransactionThread(ft,"Withdrawal");
        t1.start();
        t2.start();

        System.out.println("-----TimerDemo");
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date().toString());
            }
        },25500,1000);

        InputThread it = new InputThread();
        it.start();
        try{
            it.join();
        }
        catch(InterruptedException e){}
        t.cancel();


        System.out.println("-----WaitNotify Demo-----");
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}