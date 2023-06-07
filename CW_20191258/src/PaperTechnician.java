public class PaperTechnician extends Thread {

    private ServicePrinter servicePrinter;

    public PaperTechnician(ThreadGroup threadGroup, String name, ServicePrinter servicePrinter) {
        super(threadGroup, name);
        this.servicePrinter = servicePrinter;
    }


    @Override
    public void run() {
        int count = 0;
        int numberOfRefills = 3;
        for (int i = 0; i < numberOfRefills; i++) {
            servicePrinter.refillPaper();
            if (((LaserPrinter)servicePrinter).isPaperRefilled()){
                count++;
            }
            int num = ((int) (Math.random() * 5000 + 1));
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Paper Technician Finished, packs of paper used: " + count);

    }
}
