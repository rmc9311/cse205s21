/*********************************************************************************************************
 * CLASS: Main (Main.java)
 *
 * DESCRIPTION
 * Main class for project 1 - Reads input from file and writes output to file as well
 *
 * COURSE AND PROJECT INFORMATION
 * CSE205 Object Oriented Programming and Data Structures, Spring 2021
 * Project Number: 1
 *
 * GROUP INFORMATION
 * AUTHOR 1: Michael Foy, mcfoy, mcfoy@asu.edu
 * AUTHOR 2: Rebekah Collett, rcollett2, rcollett2@asu.edu
 * AUTHOR 3: Kevin Warch, kkevintw, kkevintw@asu.edu
 ********************************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    private static final int RUNS_UP = 1, RUNS_DN = -1;

    public static void main(String[] pArgs) {
        new Main().run();
    }

    private void run() {
        ArrayList<Integer> list = null;

        try
        {
            list = readInputFile("p1-in.txt");
        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("Oops, could not open 'p1-in.txt' for reading. The program is ending.");
            System.exit(-100);
        }

        ArrayList<Integer> listRunsUpCount = findRuns(list, RUNS_UP);
        ArrayList<Integer> listRunsDnCount = findRuns(list, RUNS_DN);

        final ArrayList<Integer> listRunsCount = mergeLists(listRunsUpCount, listRunsDnCount);

        try
        {
            writeOutputFile("p1-runs.txt", listRunsCount);
        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("Oops, could not open 'p1-runs.txt' for writing. The program is ending.");
            System.exit(-200);
        }
    }

    private ArrayList<Integer> findRuns(ArrayList<Integer> pList, final int pDir)
    {
        ArrayList<Integer> listRunsCount= arrayListCreate(pList.size(), 0);
        int i = 0, k = 0;

        while(i < pList.size() - 1)
        {
            if(pDir == RUNS_UP && pList.get(i) <= pList.get(i + 1)) k++;
            else if(pDir == RUNS_DN && pList.get(i) >= pList.get(i + 1)) k++;
            else
            {
                if(k != 0) listRunsCount.set(k, listRunsCount.get(k) + 1);
                k = 0;
            }

            i++;
        }

        if(k != 0) listRunsCount.set(k, listRunsCount.get(k) + 1);

        return listRunsCount;
    }

    private ArrayList<Integer> mergeLists(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDnCount)
    {
        ArrayList<Integer> listRunsCount = arrayListCreate(pListRunsUpCount.size(), 0);
        for(int i = 0;i < pListRunsUpCount.size();i++)
        {
            listRunsCount.set(i, pListRunsDnCount.get(i) + pListRunsUpCount.get(i));
        }

        return listRunsCount;
    }

    private ArrayList<Integer> arrayListCreate(int pSize, int pInitValue)
    {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0;i < pSize;i++) list.add(pInitValue);

        return list;
    }

    private void writeOutputFile(String filename, ArrayList<Integer> pListRuns) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(filename);
        out.println("runs_total: " + pListRuns.stream().mapToInt(Integer::valueOf).sum());
        for(Integer k : IntStream.range(1,pListRuns.size()).toArray())
        {
            out.println("runs_" + k + ": " + pListRuns.get(k));
        }

        out.close();
    }

    private ArrayList<Integer> readInputFile(String pFilename) throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(pFilename));
        ArrayList<Integer> list = new ArrayList<>();

        while(in.hasNextInt())  list.add(in.nextInt());
        in.close();

        return list;
    }
}