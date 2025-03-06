package winnernotifier;

import auctioncreator.AuctionService;
import auctiontimer.TimerService;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import paymentprocessor.PaymentService;

public class Activator implements BundleActivator {
    private ServiceReference<AuctionService> auctionRef;
    private ServiceReference<TimerService> timerRef;
    private ServiceReference<PaymentService> paymentRef;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Winner Notifier Starting...");

        // Get service references
        auctionRef = context.getServiceReference(AuctionService.class);
        timerRef = context.getServiceReference(TimerService.class);
        paymentRef = context.getServiceReference(PaymentService.class);
        if (auctionRef == null || timerRef == null || paymentRef == null) {
            System.out.println("Required services not found!");
            return;
        }

        AuctionService auction = context.getService(auctionRef);
        TimerService timer = context.getService(timerRef);
        PaymentService payment = context.getService(paymentRef);

        // Wait for auction to close and payment to be confirmed
        while (!timer.isAuctionClosed() || !payment.getPaymentStatus()) {
            System.out.println("Waiting for auction closure and payment confirmation...");
            Thread.sleep(2000);  // Check every 2 seconds
        }

        // Terminal input to trigger notification
        Scanner scanner = new Scanner(System.in);
        System.out.println("Send winner notification? (yes/no): ");
        String response = scanner.nextLine();

        if ("yes".equalsIgnoreCase(response)) {
            String notification = String.format(
                "Auction Winner Notification:\n%s\nPayment Status: Confirmed",
                auction.getAuctionDetails()
            );
            
            System.out.println(notification);
        } else {
            System.out.println("Notification not sent.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Winner Notifier Stopping...");
        if (auctionRef != null) context.ungetService(auctionRef);
        if (timerRef != null) context.ungetService(timerRef);
        if (paymentRef != null) context.ungetService(paymentRef);
    }
}