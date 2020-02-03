package synchdemo;

public class FinancialTransaction {
    private String transaction;
    private double amount;

    synchronized void update(String transaction, double amount){
        this.transaction = transaction;
        this.amount = amount;
        System.out.println(this.transaction +  " " + this.amount);
    }
}
