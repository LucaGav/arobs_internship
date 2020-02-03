package synchdemo;

public class TransactionThread extends Thread {
    private FinancialTransaction trans;
    public TransactionThread(FinancialTransaction trans, String name){
        super(name);
        this.trans = trans;
    }

    public void run(){
        for(int i = 0; i < 100; i++){
            if(getName().equals("Deposit")){
                trans.update("Deposit",2000.0);
            }
            else trans.update("Withdrawal",250.0);
        }
    }
}
