package waitndemo;

public class Shared {
    private char c = '\u0000';
    private boolean writeable = true;//if this is true, producer executes, otherwise it waits. The other way around for the consumer.

    synchronized void setSharedChar(char c) {//producer works with this one
        while (!writeable) {
            try {
                wait();
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
        this.c = c;
        writeable = false;
        notify();///consumer sleeping -> consumer woken up
    }



    synchronized char getSharedChar() {//consumer works with this one
        while (writeable) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        writeable = true;
        notify();//notify producer when character is consumed
        return c;
    }
}
