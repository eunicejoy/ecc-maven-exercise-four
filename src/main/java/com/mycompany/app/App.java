package com.mycompany.app;

import java.util.*;
import java.io.*;
import java.io.File;

import org.apache.commons.io.*;
import org.apache.commons.collections4.*;
/**
 * Hello world!
 *
 */
public class App
{
    public static int x = 0;
    public static int y = 0;
    public static int linesCount = 0;
    public static String[][] arrays;
    public static ArrayStack<String> arrayList = new ArrayStack<String>();
    public static boolean isModified;
    public static boolean tableIsPrinted;
    public static ArrayStack<String> indexList = new ArrayStack<String>();

    public static void main( String[] args ) throws Exception
    {
      int userChoice = 8;

      while(userChoice != 0){
        try{
          System.out.println("");

          System.out.print("Print\t\t[1]\nSearch\t\t[2]\nEdit\t\t[3]\nAdd New Row\t[4]\nSort\t\t[5]\nReset\t\t[6]\nExit\t\t[0]: ");

          Scanner scanInput = new Scanner(System.in);
          userChoice = scanInput.nextInt();

          switch(userChoice){
            case 1:
              print();
              break;
            case 2:
              search();
              break;
            case 3:
              edit();
              break;
            case 4:
              addNewRow();
              break;
            case 5:
              sortTable();
              break;
            case 6:
              reset();
              break;
            case 0:
              break;
            default:
              System.out.println("Invalid input.");
          }
        } catch(InputMismatchException e){
          System.out.println("Invalid input.");
        }

      }

    }

    public static void search(){
        int counter = 0;
        String indexes = "";

        System.out.println("");
        System.out.println("Enter strings to search: ");
        Scanner s = new Scanner(System.in);
        String searchString = s.nextLine();

        boolean notFound = true;


        for(String array: arrayList){

          if(array.contains(searchString)){
            String[] part = array.split(":");
            notFound = false;
            indexes = indexList.get(arrayList.indexOf(array));

            if(part[0].contains(searchString)){
              int n = part[0].length();
              int m = searchString.length();
              int index = part[0].indexOf(searchString);
              int i = index + 1;
              int count = (index >= 0 ) ? 1:0;

              while(index != -1 && i<=(n-m)){
                  index = part[0].substring(i, n).indexOf(searchString);
                  count = (index >= 0) ? count + 1 : count;
                  i = i + index + 1;
              }
              System.out.println("Found " + searchString + " on (" + indexes +") with "+ count +" instances on key field.");
            }

          if(part[1].contains(searchString)){
            int n = part[1].length();
            int m = searchString.length();
            int index = part[1].indexOf(searchString);
            int i = index + 1;
            int count = (index >= 0 ) ? 1:0;

            while(index != -1 && i<=(n-m)){
                index = part[1].substring(i, n).indexOf(searchString);
                count = (index >= 0) ? count + 1 : count;
                i = i + index + 1;
            }

            System.out.println("Found " + searchString + " on (" + indexes +") with "+ count +" instances on value field.");
          }
          }
        }

        System.out.println("");

        if(notFound == true){
          System.out.println("String(s) not found.");
        }
    }

    public static void edit(){
      int indexX = 0;
      int indexY = 0;
      int idx = 0;
      int ctr = 0;
      int i = 0;

      String editIndex = "";
      boolean indexNotFound = false;

        System.out.println("");
        System.out.println("Enter index you want to edit");
        System.out.print("x:");
        Scanner scanX = new Scanner(System.in);
        indexX = scanX.nextInt();
        System.out.print("y:");
        Scanner scanY = new Scanner(System.in);
        indexY = scanY.nextInt();
        editIndex = indexX +","+indexY;

            if(indexList.indexOf(editIndex) < 0){
              System.out.println("Index not found.");
            } else{
            for(String indexes : indexList){
              if(indexes.equals(editIndex)){
                i = indexList.indexOf(editIndex);
              }
            }

            System.out.println("Enter new characters: ");
            Scanner scanNewChars = new Scanner(System.in);
            String newChars = scanNewChars.nextLine();

            arrayList.set(i, newChars);

            int row = 0;

            // got the data from text File
            if(y == 0){
              row = linesCount;
            } else{
              row = y;
            }

            System.out.println("");
            for(String cellContent : arrayList){

               System.out.print(" " + cellContent +", \t");
               idx++;

               if((idx % row ) == 0){
                   System.out.println();
                   idx = 0;
                   ctr++;
               }
           }
          }
    }

    public static void print(){

        int idx = 0;
        int ctr = 0;

        String cell = "";
        String lines = "";

        System.out.println("");
        System.out.println("From text file\t[1]");
        System.out.print("Random\t\t[2]: ");
        Scanner userInput = new Scanner(System.in);

        int userChoice = userInput.nextInt();

        if(userChoice == 1){
          isModified = true;
          try{
            if(tableIsPrinted){
              System.out.println("");
              for(String value : arrayList){
                  System.out.print(" " + value + ", \t");
                  idx++;
                  if((idx % linesCount) == 0){
                      System.out.println();
                      idx = 0;
                      ctr++;
                  }
              }
            } else{
              arrayList.clear();
              indexList.clear();
              File file = FileUtils.getFile("C:\\Users\\Eunice\\Java\\arrays.txt");
              FileUtils.touch(file);
              if (file.exists()) {
                Scanner read = new Scanner(file);

                while(read.hasNextLine()){
                  String line = read.nextLine();
                  line = line.substring(0, line.length()-1);
                  parseData(line);
                  linesCount++;
                }

                System.out.println("");
                int row = 0;

                tableIsPrinted = true;
                // got the data from text File
                if(y == 0){
                  row = linesCount;
                } else{
                  row = y;
                }

                for(String cellContent : arrayList){

                   System.out.print(" " + cellContent +", \t");
                   indexList.add(ctr + "," + idx);
                   idx++;

                   if((idx % row ) == 0){
                       System.out.println();
                       idx = 0;
                       ctr++;
                   }
               }
              } else {
                System.out.println("The file does not exist");
              }


            }


          }catch(IOException e){
            System.out.println("Something went wrong.");
          }
        }

        else if(userChoice == 2){
          arrayList.clear();
          indexList.clear();
          //generate random dimensions
          Random randomizer = new Random();

          x = randomizer.nextInt(10) + 1;
          y = randomizer.nextInt(10) + 1;

          arrays = new String[x][y];

          System.out.println("");

          for(int i=0;i<x;i++){
            for(int j=0;j<y;j++) {
                arrays[i][j] = generateRandomCharacters() + ":" + generateRandomCharacters();
                arrayList.add(arrays[i][j]);
            }
          }

          // print table and save index to indexList

          int row = 0;


          // got the data from text File
          if(y == 0){
            row = linesCount;
          } else{
            row = y;
          }

          for(String cellContent : arrayList){

             System.out.print(" " + cellContent +", \t");
             indexList.add(ctr + "," + idx);
             idx++;

             if((idx % row ) == 0){
                 System.out.println();
                 idx = 0;
                 ctr++;
             }
         }

        } else{
          System.out.println("Invalid input.");
        }
    }

    public static void addNewRow(){
      int idx = 0;
      System.out.println();
      int row = 0;
      int ctr = 0;

      // got the data from text File
      if(y == 0){
        row = linesCount;
      } else{
        row = y;
      }

      isModified = true;

      for(int i = 0;i < row ; i++){
        arrayList.add(generateRandomCharacters() + ":" +generateRandomCharacters());
      }

      indexList.clear();

      for(String cell : arrayList){
          System.out.print(" " + cell +", \t");
          indexList.add(ctr + "," + idx);
          idx++;
          if((idx % row ) == 0){
              System.out.println();
              idx = 0;
              ctr++;
          }
      }
    }

    public static void sortTable(){
      int idx = 0;
      int row = 0;
      int ctr = 0;
      System.out.println();


      if(y == 0){
        row = linesCount;
      } else{
        row = y;
      }

      Collections.sort(arrayList);
      indexList.clear();

        for(String cell : arrayList){

           System.out.print(" " + cell +", \t");
           indexList.add(ctr + "," + idx);
           idx++;

           if((idx % row ) == 0){
               System.out.println();
               idx = 0;
                ctr++;
           }
       }
    }

    public static void reset(){
      print();
      isModified = false;
    }

    public static String generateRandomCharacters(){
    //  String characters = "!\"#$%&\'()*+-./0123456789;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
      String characters = "abcdefghijklmnopqrstuvwxyz";
      String randomCharacters = "";
      int size = characters.length();

      Random random = new Random();

      for(int i = 1; i<4; i++){

        String character = Character.toString(characters.charAt(random.nextInt(size)));
        randomCharacters += character;
      }

      return randomCharacters;
    }

    public static void parseData(String line){
      Scanner scanLine = new Scanner(line);
      scanLine.useDelimiter(",\t");
      while(scanLine.hasNext()){
        arrayList.add(scanLine.next().trim());
      }
    }

}
