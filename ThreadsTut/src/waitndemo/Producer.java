package waitndemo;

public class Producer extends Thread {
    private Shared s;

    public Producer(Shared s){
        this.s = s;
    }

    public void run (){
        for(char ch = 'A'; ch <= 'Z'; ch++){
            try{
                Thread.sleep((int) (Math.random() * 4000));
            }
            catch (InterruptedException e) {
            }

            s.setSharedChar(ch);
            System.out.println(ch + " produced by producer");
        }
    }
}
