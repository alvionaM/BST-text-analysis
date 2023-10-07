public class Demo {

    public static void main(String[] args) {
       
        BST textfile = new BST();

        textfile.addStopWord("the");
        textfile.addStopWord("an");
        textfile.addStopWord("a");
        textfile.addStopWord("eh");
        textfile.removeStopWord("eh");

        textfile.load(args[0]);

        textfile.printTreeAlphabetically(System.out);
        System.out.println();

        System.out.println("Distinct Words: "+textfile.getDistinctWords());
        System.out.println("Total Words: "+textfile.getTotalWords());

        System.out.println("Mean frequency: "+String.format("%.3f", textfile.getMeanFrequency()));
        System.out.println("Word with max frequency: "+textfile.getMaximumFrequency());

        System.out.println("Frequency of 'one' : "+textfile.getFrequency("one"));
        System.out.println("Frequency of 'girl' : "+textfile.getFrequency("girl"));

        System.out.println();
        textfile.printTreeByFrequency(System.out);
        
        System.out.println();
        System.out.println("---------------------------------------\n");

        System.out.println("Frequency of 'Scrooge' : "+textfile.getFrequency("scrooge"));

        System.out.println("Removing word 'Scrooge'...\n");
        textfile.remove("scrooge");

        textfile.printTreeAlphabetically(System.out);
        System.out.println();

        System.out.println("Distinct Words: "+textfile.getDistinctWords());
        System.out.println("Total Words: "+textfile.getTotalWords());

        System.out.println();
        System.out.println("---------------------------------------");

        System.out.println("Searching word 'fellow': ...");
        System.out.println("Found: "+textfile.search("fellow"));

        System.out.println();

        System.out.println("Searching word 'turkey': ...");
        System.out.println("Found: "+textfile.search("turkey"));

        System.out.println();

        textfile.printTreeByFrequency(System.out);

    }

}
