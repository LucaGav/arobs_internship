package timerdemo;

public class InputThread extends Thread {//cancel timer when user presses key

    public void run(){
        try{
            System.in.read();
        }
        catch(java.io.IOException e){
            //e.printStackTrace();
        }
    }
}
