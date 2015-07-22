package Day3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by test on 7/22/2015.
 */
//Read a big list of words and write all anagrmas in the same line
public class AnagramsMatcher {
    static String inputFilePath;
    static String outputFilePath;
    InputStream inputStream;
    BufferedInputStream bufferedInputStream;
    BufferedWriter bufferedWriter;
    HashMap<String, ArrayList<String>> map;
    public AnagramsMatcher(String inputFilePath, String outputFilePath){
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        try {
            inputStream = new FileInputStream(this.inputFilePath);
            bufferedWriter = new BufferedWriter(new FileWriter(this.outputFilePath));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        bufferedInputStream = new BufferedInputStream(inputStream);
        map = new HashMap<String, ArrayList<String>>();
    }
    public void addToHashMap(String currWord){

        char charArray[] = currWord.toCharArray();
        Arrays.sort(charArray);
        String sortedString = new String(charArray);

        if(map.containsKey(sortedString)){
            ArrayList<String> anagramList = map.get(sortedString);
            anagramList.add(currWord);
        }else {
            ArrayList<String> anagramList = new ArrayList<String>();
            anagramList.add(currWord);
            map.put(sortedString, anagramList);
        }
    }
    public int scanFile(){
        int words_read_count = 0;
        String currWord = "";
        try {
            while (bufferedInputStream.available() > 0){
                char currChar = (char)bufferedInputStream.read();
                if(currChar == '\r'){
                    currChar = (char)bufferedInputStream.read();
                    addToHashMap(currWord);
                    currWord = "";
                    words_read_count++;
                }else {
                    currWord += currChar;
                }
            }
            //System.out.println(currWord);
            addToHashMap(currWord);
            words_read_count++;

            inputStream.close();
            bufferedInputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return words_read_count;
    }
    public void writeToFile(){

        try {
            for(ArrayList list : map.values()){
                if(list.size() > 1){

                    for (int i = 0; i < list.size(); i++) {
                        //System.out.print(list.get(i) + ", ");
                        bufferedWriter.write(list.get(i).toString());
                        bufferedWriter.write(" ");
                    }
                    bufferedWriter.write("\r\n");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void printAnagrams(){

        for(ArrayList list : map.values()){
            if(list.size() > 1){
                System.out.print("[ ");
                for (int i = 0; i < list.size(); i++) {
                    System.out.print(list.get(i) + ", ");
                }
                System.out.println("]");
            }
        }
    }
    public static void main(String args[]){

        long start = System.currentTimeMillis();
        AnagramsMatcher anagramsMatcher = new AnagramsMatcher(
                "C:\\Users\\test\\Documents\\BootCamp\\word-list.txt",
                "C:\\Users\\test\\Documents\\BootCamp\\anagrams.txt");
        int ScannedWords = anagramsMatcher.scanFile();
        anagramsMatcher.writeToFile();
        long end = System.currentTimeMillis();
        double time = (end - start) / 1000;
        System.out.println("Job completed in " + time + "seconds");
    }
}
