import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> arr = new ArrayList<String>();
        System.out.println("What 2 words are you trying to represent?");
        arr.add(sc.nextLine());
        arr.add(sc.nextLine());
        StateTable t = new StateTable(arr);
        t.printChars();
        t.dValues();
        t.ckInputs();
        t.results();
        System.out.println();
        t.printCkInputs();
        System.out.println();
        System.out.println("Do you want the kmap representations? (only available for state machines with more than 3 states and less than 9)");
        if (sc.nextLine().contains("y")){
            t.kmaps();
        } else sc.close();
        }
    }