import java.util.ArrayList;

public class StateTable {
    private final int numVars;
    private final ArrayList<String> words = new ArrayList<String>();
    private ArrayList<String> chars = new ArrayList<String>();
    private ArrayList<int[]> results = new ArrayList<int[]>();
    private final int[][] segVals = {{1, 1, 1, 0, 1, 1, 1}, {0, 0, 1, 1, 1, 1, 1}, {1, 0, 0, 1, 1, 1, 0}, {0, 1, 1, 1, 1, 0, 1}, {1, 0, 0, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 1}, {1, 1, 1, 1, 0, 1, 1}, {0, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 1, 1, 0}, {0, 1, 1, 1, 1, 0, 0}, {0, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 1, 1, 1, 0}, {1, 0, 1, 0, 1, 0, 0}, {0, 0, 1, 0, 1, 0, 1}, {1, 1, 1, 1, 1, 1, 0}, {1, 1, 0, 0, 1, 1, 1}, {1, 1, 1, 0, 0, 1, 1}, {0, 0, 0, 0, 1, 0, 1}, {1, 0, 1, 1, 0, 1, 1}, {0, 0, 0, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 0}, {0, 0, 1, 1, 1, 0, 0}, {0, 1, 0, 1, 0, 1, 0}, {0, 1, 1, 0, 1, 1, 1}, {0, 1, 1, 1, 0, 1, 1}, {1, 1, 0, 1, 1, 0, 1}};
    private ArrayList<ArrayList<Object>> ckInputs = new ArrayList<>();

    public StateTable(ArrayList<String> words) {
        // sets word list
        for (String s : words) {
            this.words.add(s);
        }

        //sets chars list
        for (String s : words) {
            for (int i = 0; i < s.length(); i++) {
                String c = s.substring(i, i + 1);
                if (!this.chars.contains(c)) {
                    chars.add(c);
                }
            }
        }

        // sorts chars list in alphabetical order
        for (int i = 0; i < chars.size(); i++) {
            String c1 = chars.get(i);
            int newIndex = i;
            for (int j = i + 1; j < chars.size(); j++) {
                if (numVal(chars.get(j)) < numVal(chars.get(newIndex))) {
                    newIndex = j;
                }
            }
            if (newIndex != i) {
                chars.set(i, chars.get(newIndex));
                chars.set(newIndex, c1);
            }
        }

        // sets numVars
        if (Math.log(chars.size())/Math.log(2) > (int) (Math.log(chars.size())/Math.log(2))) numVars = (int) (Math.log(chars.size())/Math.log(2)) + 1;
        else numVars = (int) (Math.log(chars.size())/Math.log(2));

        // Creates a results list with all values equal to 2 because 2 will never be included in this list which makes it easier to identify which states go unused
        for (int i = 0; i < numVars; i++){
            results.add(new int[(int) Math.pow(2, numVars+1)]);
        }
        for (int i = 0; i < results.size(); i++) {
            for (int j = 0; j < results.get(i).length; j++){
                results.get(i)[j] = 2;
            }
        }
    }

    // finds the d input by converting the index of the letters in the char list into binary and then using that as the inputs for d
    public void dValues() {
        String w1 = words.get(0);
        String w2 = words.get(1);
        int length = results.get(0).length/2;
        // does it for when En = 0
        for (int i = 0; i < length; i++){
                int d;
                if (i >= chars.size() || w1.indexOf(chars.get(i)) == w1.length()-1 || !w1.contains(chars.get(i))){
                    d = chars.indexOf(w1.substring(0, 1));
                } else {
                    String c = chars.get(i);
                    d = chars.indexOf(w1.substring(w1.indexOf(c) + 1, w1.indexOf(c) + 2));
                }
                for (int k = 1; k <= numVars; k++) {
                    results.get(numVars - k)[i] = d % 2;
                    d = d/2;
                }
        }
        // Does values for when En = 1
        for (int i = 0; i < length; i++){
            int d;
            if (i >= chars.size() || w2.indexOf(chars.get(i)) == w2.length() - 1 || !w2.contains(chars.get(i))){
                d = chars.indexOf(w2.substring(0, 1));
            } else {
                String c = chars.get(i);
                d = chars.indexOf(w2.substring(w2.indexOf(c) + 1, w2.indexOf(c) + 2));
            }
            for (int k = 1; k <= numVars; k++) {
                results.get(numVars - k)[i + length] = d % 2;
                d = d/2;
            }
        }
    }

    // helps when sorting character list
    private int numVal(String c){
        return Character.getNumericValue(c.charAt(0)) - 9;
    }

    // Prints character list for user.
    public void printChars(){
        for (String s : chars) {
            System.out.print(s+" ");
        }
        System.out.println();
    }

    // prints Truth Table for the D Flip Flop inputs
    public void results(){
        for (int i = 0; i < numVars; i++) {
            System.out.print("D" + (numVars - i - 1) + " ");
        }
        System.out.println();
        for (int c = 0; c < (int) Math.pow(2, numVars + 1); c++) {
            for (int r = 0; r < results.size(); r++) {
                System.out.print(results.get(r)[c] + "  ");
            }
            System.out.println();
        }
    }

    // Prints truth table for the seven segment display inputs to print the letters correctly
    public void ckInputs(){
        int length = (int) Math.pow(2, numVars);
        for (int i = 0; i < 7; i++){
            ckInputs.add(new ArrayList<Object>());
        }
        for (int i = 0; i < length; i++){
            if (i < chars.size()){
                int[] in = segVals[numVal(chars.get(i)) - 1];
                for (int j = 0; j < 7; j++){
                    ckInputs.get(j).add(in[j]);
                }
            } else {
                for (int j = 0; j < 7; j++){
                    ckInputs.get(j).add("x");
                }
            }
        }
        for (int i = 0; i < length; i++){
            if (i < chars.size()){
                int[] in = segVals[numVal(chars.get(i)) - 1];
                for (int j = 0; j < 7; j++){
                    ckInputs.get(j).add(in[j]);
                }
            } else {
                for (int j = 0; j < 7; j++){
                    ckInputs.get(j).add("x");
                }
            }
        }
    }

    //prints the ck ssd inputs for the state machine
    public void printCkInputs(){
        System.out.println("a  b  c  d  e  f  g");
        for (int c = 0; c < (int) Math.pow(2, numVars + 1); c++) {
            for (int r = 0; r < ckInputs.size(); r++) {
                System.out.print(ckInputs.get(r).get(c) + "  ");
            }
            System.out.println();
        }
    }

    // puts D values and ssd segments into kmaps (only for state machines with 2 or 3 D values)
    public void kmaps(){
        switch (numVars){
            case 2:
                for (int i = 0; i < results.size(); i++){
                    int[] d = results.get(i);
                    System.out.println("D" + (numVars - i - 1) + "     Q0'  Q0\n" +
                            "En'Q1' " + d[0] + "    " + d[1] + "\n"
                            + "En'Q1  " + d[2] + "    " + d[3] + "\n"
                            + "En Q1  " + d[6] + "    " + d[7] + "\n"
                            + "En Q1' " + d[4] + "    " + d[5] + "\n");
                }
                for (int i = 0; i < ckInputs.size(); i++){
                    ArrayList<Object> s = ckInputs.get(i);
                    char seg = (char) (97 + i);
                    System.out.println(seg + "      Q0'  Q0\n" +
                            "En'Q1' " + s.get(0) + "    " + s.get(1) + "\n"
                            + "En'Q1  " + s.get(2) + "    " + s.get(3) + "\n"
                            + "En Q1  " + s.get(6) + "    " + s.get(7) + "\n"
                            + "En Q1' " + s.get(4) + "    " + s.get(5) + "\n");
                }
                break;
                case 3:
                    for (int i = 0; i < results.size(); i++){
                        int[] d = results.get(i);
                        System.out.println("D" + (numVars - i - 1) + "     Q1'Q0'  Q1'Q0   Q1 Q0   Q1 Q0'\n" +
                                "En'Q2' " + d[0] + "       " + d[1] + "       " + d[3] + "       " + d[2] + "\n"
                                + "En'Q2  " + d[4] + "       " + d[5] + "       " + d[7] + "       " + d[6] + "\n"
                                + "En Q2  " + d[12] + "       " + d[13] + "       " + d[15] + "       " + d[14] + "\n"
                                + "En Q2  " + d[8] + "       " + d[9] + "       " + d[11] + "       " + d[10] + "\n");
                    }
                    for (int i = 0; i < ckInputs.size(); i++) {
                        ArrayList<Object> s = ckInputs.get(i);
                        char seg = (char) (97 + i);
                        System.out.println(seg + "      Q1'Q0'  Q1'Q0   Q1 Q0   Q1 Q0'\n" +
                                "En'Q2' " + s.get(0) + "       " + s.get(1) + "       " + s.get(3) + "       " + s.get(2) + "\n"
                                + "En'Q2  " + s.get(4) + "       " + s.get(5) + "       " + s.get(7) + "       " + s.get(6) + "\n"
                                + "En Q2  " + s.get(12) + "       " + s.get(13) + "       " + s.get(15) + "       " + s.get(14) + "\n"
                                + "En Q2' " + s.get(8) + "       " + s.get(9) + "       " + s.get(11) + "       " + s.get(10) + "\n");
                    }
        }
    }
}