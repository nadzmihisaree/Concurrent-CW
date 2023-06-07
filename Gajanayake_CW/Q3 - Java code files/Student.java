/**
 * describes the actions of a student who prints documents using a printer.
 *
 */
public class Student extends Thread {

    private Printer printer;

    public Student(String name, ThreadGroup threadGroup, Printer printer) {
        super(threadGroup, name);
        this.printer = printer;
    }

    @Override
    public void run() {
        Document[] document = new Document[5];

        document[0] = new Document(Thread.currentThread().getName(), "English" ,2);
        document[1] = new Document(Thread.currentThread().getName(), "Maths" ,10);
        document[2] = new Document(Thread.currentThread().getName(), "Science" ,5);
        document[3] = new Document(Thread.currentThread().getName(), "ICT" ,8);
        document[4] = new Document(Thread.currentThread().getName(), "History" ,10);

        int noOfPages =0;
        for (Document printDoc: document){
            printer.printDocument(printDoc);
            noOfPages += printDoc.getNumberOfPages();
            int num = ((int) (Math.random() * 5000 + 1));
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\t" + Thread.currentThread().getName() + " Finished Printing: 5 Documents, " + noOfPages + " pages");

    }
}
