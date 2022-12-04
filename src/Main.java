
import program.*;

public class Main {

    public static void main(String[] args) {
        /*
        System.out.println();
        System.out.println("P1");
        LexycalScanner p1_scanner = new LexycalScanner("src/resources/P1.txt", "src/resources/PIF1.txt", "src/resources/ST1.txt");
        p1_scanner.scan();

        System.out.println();
        System.out.println("P2");
        LexycalScanner p2_scanner = new LexycalScanner("src/resources/P2.txt", "src/resources/PIF2.txt", "src/resources/ST2.txt");
        p2_scanner.scan();


        System.out.println();
        System.out.println("P3");
        LexycalScanner p3_scanner = new LexycalScanner("src/resources/P3.txt", "src/resources/PIF3.txt", "src/resources/ST3.txt");
        p3_scanner.scan();

        System.out.println();
        System.out.println("P1ERR");
        LexycalScanner p1_scanner_err = new LexycalScanner("src/resources/P1err.txt", "src/resources/PIFerr.txt", "src/resources/STerr.txt");
        p1_scanner_err.scan();
        */

        /*

        FiniteAutomaton finiteAutomaton = new FiniteAutomaton("src/resources/FA.in");

        printMenu();
        printOption();
        Scanner menuScanner = new Scanner(System.in);
        int option = menuScanner.nextInt();

        while(option >= 1 && option <= 7) {
            switch (option) {
                case 1:
                    System.out.println("States: ");
                    System.out.println(finiteAutomaton.buildStates());
                    break;

                case 2:
                    System.out.println("Alphabet: ");
                    System.out.println(finiteAutomaton.buildAlphabet());
                    break;

                case 3:
                    System.out.println("Initial State: ");
                    System.out.println(finiteAutomaton.buildInitialState());
                    break;

                case 4:
                    System.out.println("Final States: ");
                    System.out.println(finiteAutomaton.buildFinalStates());
                    break;
                case 5:
                    System.out.println("Transitions: ");
                    System.out.println(finiteAutomaton.buildTransitions());
                    break;
                case 6:
                    System.out.println("Check Sequence: ");
                    Scanner sequenceScanner = new Scanner(System.in);
                    String sequence = sequenceScanner.nextLine();
                    System.out.println(finiteAutomaton.checkSequence(sequence));
                    break;
                case 7:
                    System.out.println("Check DFA: ");
                    System.out.println(finiteAutomaton.checkDFA());
                    break;
            }
            printMenu();
            printOption();
            option = menuScanner.nextInt();
        }

        */

        Grammar grammar = new Grammar("src/resources/g2.txt");

        System.out.println(grammar.printNonTerminals());
        System.out.println(grammar.printTerminals());
        System.out.println(grammar.printProductions());
        System.out.println(grammar.printProductionsForNonTerminal("if_stmt"));
    }

    public static void printMenu() {
        System.out.println("1) Print States");
        System.out.println("2) Print Alphabet");
        System.out.println("3) Print Initial State");
        System.out.println("4) Print Final States");
        System.out.println("5) Print Transitions");
        System.out.println("6) Check Sequence");
        System.out.println("7) Check DFA");
        System.out.println("8) Exit Program ");
    }

    public static void printOption() {
        System.out.println("Option: ");
    }

    public void scannerMenu() {

    }
}