import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Cannibals {

    public static void main(String[] args) {

        System.out.println("Dose ton arithmo ton kanivalon kai ton ierapostolon: ");
        Scanner scannerInt = new Scanner(System.in);
        int N = scannerInt.nextInt();

        System.out.println("Dose thn megisth xoritikotita varkas: ");
        int M = scannerInt.nextInt();

        System.out.println("Dose ton megisto epitrepomeno arithmo diasxiseon toy potamoy: ");
        int K = scannerInt.nextInt();

        State initialState = new State(N,N,Position.LEFT,0,0,M,0);  //initialised state

        Searcher searcher = new Searcher();
        long start = System.currentTimeMillis();
        State terminalState = searcher.Astar(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null){
            System.out.println("Could not find Solution");
        }else{
            // print the path from beggining to start.
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
            path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            // reverse the path and print.
            if(path.size()>K){
                System.out.println("Solution exceeds maximum number of crossings allowed\n");
                System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");
                System.exit(0);
            }
            Collections.reverse(path);
            for(State item: path)
            {
                item.print();
            }
            System.out.println();
            System.out.println("Solution Found!");
            System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");
        }
    }
}

