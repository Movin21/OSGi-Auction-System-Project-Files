package paymentprocessor;

public class PaymentImpl implements PaymentService {
    private boolean paymentConfirmed;
    private double expectedAmount;

    public PaymentImpl(double expectedAmount) {
        this.expectedAmount = expectedAmount;  // Highest bid from auction
        this.paymentConfirmed = false;
    }

    @Override
    public boolean processPayment(double amount, String method) {
        if (amount >= expectedAmount && ("credit".equalsIgnoreCase(method) || "paypal".equalsIgnoreCase(method))) {
            paymentConfirmed = true;
            System.out.println("Payment of " + amount + " via " + method + " confirmed.");
            return true;
        } else {
            System.out.println("Payment rejected: Amount must be at least " + expectedAmount + " and method must be 'credit' or 'paypal'.");
            return false;
        }
    }

    @Override
    public boolean getPaymentStatus() {
        return paymentConfirmed;
    }
}