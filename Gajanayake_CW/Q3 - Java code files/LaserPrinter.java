public class LaserPrinter implements ServicePrinter {

    /**
     * specifies the attributes and methods for the functions of a Laser Printer
     *
     */
    private String printerName;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int printedDocumentCount;
    private boolean paperRefilled = false;
    private boolean tonerReplaced = false;

    public LaserPrinter(String printerName, int currentPaperLevel, int currentTonerLevel, int printedDocumentCount) {
        this.printerName = printerName;
        this.currentPaperLevel = currentPaperLevel;
        this.currentTonerLevel = currentTonerLevel;
        this.printedDocumentCount = printedDocumentCount;
    }

    //method to print a document
    @Override
    public synchronized void printDocument(Document document) {
        while ((document.getNumberOfPages() > currentPaperLevel ||
                document.getNumberOfPages() > currentTonerLevel ||
                MINIMUM_TONER_LEVEL > currentTonerLevel)) {

            if ((document.getNumberOfPages() > currentPaperLevel) && (document.getNumberOfPages() > currentTonerLevel)){
                System.out.println("Insufficient paper level or toner level. Current paper level is " + currentPaperLevel +"and current toner level is "+ currentTonerLevel +".");
            }
            else if (document.getNumberOfPages() > currentPaperLevel ){
                System.out.println("Insufficient paper level. Current paper level is " + currentPaperLevel);
            }
            else if (document.getNumberOfPages() > currentTonerLevel ){
                System.out.println("Insufficient toner level. Current toner level is " + currentTonerLevel);
            }else {
                System.out.println("Minimum toner level reached. Current toner level is " + currentTonerLevel);
            }

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentPaperLevel -= document.getNumberOfPages();
        currentTonerLevel -= document.getNumberOfPages();
        printedDocumentCount++;
        System.out.println("[-----Successfully printed the document. Current toner level : " + currentTonerLevel + " Current Paper Level : " + currentPaperLevel + "-----]");
        notifyAll();
    }

    //method to replace a toner cartridge
    @Override
    public synchronized void replaceTonerCartridge() {
        this.tonerReplaced = false;
        while (currentTonerLevel >= MINIMUM_TONER_LEVEL){
            System.out.println("No need to replace the toner cartridge now. Current toner level is " + currentTonerLevel);
            try {
                wait(5000);
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("* * * Need to replace the toner cartridge. Current toner level is " + currentTonerLevel + ". Replacing the toner cartridge... * * *");
        currentTonerLevel = FULL_TONER_LEVEL;
        this.tonerReplaced =true;
        System.out.println("* * * Successfully replaced the toner cartridge. Current toner level after replacing the toner cartridge is " + currentTonerLevel + " * * * ");
        notifyAll();
    }

    //method to refill paper tray of the printer
    @Override
    public synchronized void refillPaper() {
        this.paperRefilled = false;
        while (currentPaperLevel > (FULL_PAPER_TRAY -SHEETS_PER_PACK)){
            System.out.println("Can not refill the paper tray at the moment because it will exceeds maximum paper level. " +
                    "Current paper level is " + currentPaperLevel + " and Maximum paper level is " + FULL_PAPER_TRAY);
            try {
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("+ + + Need to refill the printer with a sheet pack. + + +");
        currentPaperLevel += SHEETS_PER_PACK;
        this.paperRefilled = true;
        System.out.println("+ + + Successfully added a sheet pack. Current paper level is " + currentPaperLevel + " + + +");
        notifyAll();
    }

    public boolean isPaperRefilled() {
        return paperRefilled;
    }

    public boolean isTonerReplaced() {
        return tonerReplaced;
    }

    //returns a string representation of the printer's current state
    @Override
    public String toString() {
        return "LaserPrinter : " +
                "\n * Printer Name = " + printerName +
                "\n * Current Paper Level = " + currentPaperLevel +
                "\n * Current Toner Level = " + currentTonerLevel +
                "\n * Printed Document Count = " + printedDocumentCount +
                '}';
    }
}
