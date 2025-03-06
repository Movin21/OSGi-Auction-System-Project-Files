package auctiontimer;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import auctioncreator.AuctionService;

public class Activator implements BundleActivator {
    private ServiceRegistration<TimerService> registration;
    private TimerImpl timer;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Auction Timer Starting...");

        // Get AuctionService to retrieve duration
        ServiceReference<AuctionService> auctionRef = context.getServiceReference(AuctionService.class);
        if (auctionRef == null) {
            System.out.println("No AuctionService found!");
            return;
        }
        AuctionService auction = context.getService(auctionRef);

        // Assume initial duration is passed via constructor (simplified here)
        String details = auction.getAuctionDetails();
        int initialDuration = Integer.parseInt(details.split("Duration: ")[1].split(" ")[0]);  // Parse from details

        // Create and register TimerService
        timer = new TimerImpl(auction, initialDuration);
        registration = context.registerService(TimerService.class, timer, null);
        System.out.println("Timer started with " + initialDuration + " seconds remaining.");

        // Terminal input for extension
        Scanner scanner = new Scanner(System.in);
        System.out.println("Extend auction duration? (yes/no): ");
        String response = scanner.nextLine();
        if ("yes".equalsIgnoreCase(response)) {
            System.out.println("Enter extension time (seconds): ");
            int extension = scanner.nextInt();
            if (extension > 0) {
                timer.extendDuration(extension);
                
            } else {
                System.out.println("Invalid extension time!");
            }
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Auction Timer Stopping...");
        if (timer != null) {
            timer.stopTimer();
        }
        if (registration != null) {
            registration.unregister();
        }
    }
}
