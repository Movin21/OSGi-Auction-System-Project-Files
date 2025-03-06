package paymentprocessor;

public interface PaymentService {
	boolean processPayment(double amount, String method);  // Processes the payment
    boolean getPaymentStatus();  // Returns true if payment is confirmed
}
