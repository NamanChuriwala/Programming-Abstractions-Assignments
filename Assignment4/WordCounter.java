package com.company;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordCounter {
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get("C:\\Users\\Naman Churiwala\\Downloads\\sample-files");
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("C:\\Users\\Naman Churiwala\\Downloads\\output-1\\3.txt");
    public static final int  NUMBER_OF_THREADS = 3;
    public static String output = WORD_COUNT_TABLE_FILE+"";
    public static Hashtable<String, Integer> maintable = new Hashtable<String, Integer>();
    public static Hashtable<String, Hashtable<String, Integer>>  mainmap= new Hashtable<String, Hashtable<String, Integer>>();
    public static String longestword = "";
    public static int maxlength = 0;
    public static BufferedWriter fw = null;
    public static File fout = new File(output);
    public static FileOutputStream fos = null;

    public static class Readfile extends Thread{
        String filename;
        String f;

        public Readfile(String filename, String f){
            this.filename = filename;
            this.f = f;
        }

        public static synchronized void write(String filename, Hashtable<String, Integer> table){
            Set<String> keys = table.keySet();
            for(String key: keys){
                if(key.length() > longestword.length()){
                    maxlength = key.length();
                    longestword = key;
                }
                if(mainmap.containsKey(key)){
                    Hashtable<String, Integer> temp = mainmap.get(key);
                    temp.put(filename, table.get(key));
                    mainmap.put(key, temp);
                }
                else{
                    Hashtable<String, Integer> tmp = new Hashtable<String, Integer>();
                    tmp.put(filename, table.get(key));
                    mainmap.put(key, tmp);
                }
            }
        }

        public void run() {
            Hashtable<String, Integer> table = new Hashtable<String, Integer>();
            File file = new File(filename);
            Scanner sc2 = null;

            try {
                sc2 = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (sc2.hasNextLine()) {
                Scanner s2 = new Scanner(sc2.nextLine());
                while (s2.hasNext()) {
                    String s = s2.next().toLowerCase().replaceAll("\\W", "");;
                    maintable.put(s, 0);
                    if(table.containsKey(s)){
                        int num = ((int) table.get(s)) + 1;
                        table.put(s, num);
                    }
                    else{
                        table.put(s, 1);
                    }
                }
            }
            Readfile.write(f, table);
        }
    }

    public LinkedList<String> listFilesForFolder(final File folder) {
        LinkedList<String> files = new LinkedList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                files.add(fileEntry.getName());
            }
        }
        return files;
    }

    public static void main(String[] args) {
        WordCounter w = new WordCounter();
        final File folder = new File(FOLDER_OF_TEXT_FILES+"");
        int numFiles = new File(String.valueOf(FOLDER_OF_TEXT_FILES)).list().length;
        LinkedList<String> filenames = w.listFilesForFolder(folder);
        Collections.sort(filenames);
        String prefix = FOLDER_OF_TEXT_FILES + "\\";

        List<Readfile> rf = new ArrayList<>();
        for(int i=0; i<numFiles; i++){
            String filename = filenames.get(i);
            Readfile thread = new Readfile(prefix+filenames.get(i), filenames.get(i));
            rf.add(thread);
        }
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        rf.forEach(each -> executor.submit(each));
        executor.shutdown();

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        Set<String> keys = mainmap.keySet();

        for(String filename: filenames){
            for(String key: keys){
                Hashtable<String, Integer> tempo = mainmap.get(key);
                if(!tempo.containsKey(filename)){
                    tempo.put(filename, 0);
                    mainmap.put(key, tempo);
                }
            }
        }

        try {
            fos = new FileOutputStream(fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        fw = new BufferedWriter(new OutputStreamWriter(fos));
        List<String> unsortedkeys = new ArrayList<>(mainmap.keySet());
        List<Integer> columnlengths = new ArrayList<>();
        Collections.sort(unsortedkeys);
        int n = unsortedkeys.size();

        try {
            fw.write(String.join("", Collections.nCopies(maxlength+1, " ")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        columnlengths.add(maxlength+1);
        for(int i=0; i<filenames.size(); i++){
            columnlengths.add(filenames.get(i).length());
            try {
                int len = filenames.get(i).length();
                String sub = filenames.get(i).substring(0, len-4);
                fw.write(sub+"    ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fw.write("total");
            fw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String key:unsortedkeys){
            int i = 0;
            int total = 0;
            Hashtable<String, Integer> tmp = mainmap.get(key);
            try {
                fw.write(key);
                int diff = columnlengths.get(i) - key.length();
                fw.write(String.join("", Collections.nCopies(diff, " ")));
                i += 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(String curkey: filenames){
                total += tmp.get(curkey);
                String s = "" + tmp.get(curkey);
                try {
                    int diff = columnlengths.get(i) - s.length();
                    fw.write(s);
                    fw.write(String.join("", Collections.nCopies(diff, " ")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fw.write(total+"");
                fw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}