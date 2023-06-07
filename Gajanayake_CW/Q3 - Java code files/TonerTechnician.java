/**
 * defines the actions of a Toner Technician who refills the toner cartridge of thr printer
 *
 * this class is a thread
 *
 */
public class TonerTechnician extends Thread {

    private ServicePrinter servicePrinter;

    public TonerTechnician(ThreadGroup threadGroup, String name, ServicePrinter servicePrinter) {
        super(threadGroup, name);
        this.servicePrinter = servicePrinter;
    }

    @Override
    public void run() {
        int count = 0;
        int numberOfRefills = 3;
        for (int i = 0; i < numberOfRefills; i++) {
            servicePrinter.replaceTonerCartridge();
            if (((LaserPrinter)servicePrinter).isTonerReplaced()){
                count++;
            }
            int num = ((int) (Math.random() * 5000 + 1));
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Toner Technician Finished, cartridges replaced: " + count);
    }

}
