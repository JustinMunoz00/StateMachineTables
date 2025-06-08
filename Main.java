import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> arr = new ArrayList<String>();
        System.out.println("What 2 words are you trying to represent?");
        arr.add(sc.nextLine());
        arr.add(sc.nextLine());
        StateTable t = new StateTable(arr)
        t.printChars();
        t.dValues();
        t.results();
        }
    }