import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * The following class acts as a simulation of a cache storage type
 * The user selects the test type, cache size(s), and a text file to be 
 * examend word for word. 
 * @author bryce kratzer
 * @see Cache.java
 */
public class Test {
    public static void main(String[] args) {
        int testType1 = 1;
        int userTestType;
        try {
            userTestType = Integer.parseInt(args[0]);
            if(userTestType != 1 && userTestType != 2) {
                System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
                System.exit(1);
            }

            if((userTestType == 1 && args.length != 3)) {
                System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
                System.exit(1);
            }

            if((userTestType == 2 && args.length != 4)) {
                System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
                System.exit(1);
            }

            if(userTestType == testType1) {
                testType1(args);
            } else {
                testType2(args);
            }
        } catch (NumberFormatException e) {
            System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
            System.exit(1);
        }
    }
    /**
     * Is used when user inputs test type 1. 
     * Creates one level cache storage and checks each word
     * in file to see if it's in cache, if not it is added too top
     * @param userInput take in user arguments to be paresed
     */
    public static void testType1(String[] userInput) {
        try {
            Integer.parseInt(userInput[1]);
        } catch (NumberFormatException e) {
            System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
            System.exit(1);
        }
        int numOfRefer = 0;
        int numOfHits = 0;
        double numOfRatio;
        int cacheCapacity = Integer.parseInt(userInput[1]);
        Cache<String> cacheStorage = new Cache<String>(cacheCapacity);

        System.out.println("One level cache with " + cacheCapacity + " entries has been created.\n");

        String fileName = userInput[2];

        try {
            File userFile = new File(fileName);
            Scanner fileScanner = new Scanner(userFile);
            Scanner lineScan;
            while(fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                lineScan = new Scanner(currentLine);
                while(lineScan.hasNext()){
                    String currentWord = lineScan.next();
                    boolean inCache = cacheStorage.getObject(currentWord);//searches if cache contains word
                    numOfRefer++;
                    if(inCache == true) {//checks if in cache
                        numOfHits++;
                        cacheStorage.addObject(currentWord);
                    } else {
                        cacheStorage.addObject(currentWord);
                    }
                }
                lineScan.close();
            }   
            fileScanner.close();
            numOfRatio = (double)numOfHits/numOfRefer;
            System.out.println("The number of global references: " + numOfRefer);
            System.out.println("The number of global cache hits: " + numOfHits);
            System.out.println("The global hit ratio           : " + numOfRatio + "\n");  
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
        }

    }
    /**
     * Is used when user inputs test type 2. 
     * Creates two level cache storage and checks each word
     * in file to see if it's in caches, if not it is added too top. 
     * if in second level cache then added to first level cache as well
     * @param userInput user's command line arguments
     */
    public static void testType2(String[] userInput) {
        try {
            Integer.parseInt(userInput[1]);
            Integer.parseInt(userInput[2]);
        } catch (NumberFormatException e) {
            System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
        }
        int lvlOneCacheCapacty = Integer.parseInt(userInput[1]);
        int lvlTwoCacheCapacty = Integer.parseInt(userInput[2]);
        if (lvlOneCacheCapacty > lvlTwoCacheCapacty) {
            System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
            System.exit(1);
        }
        int frstLvlHit = 0;
        int scndLvlHit = 0;
        int frstLvlRefer = 0;
        int scndLvlRefer = 0;
        double globalHitRatio;
        int totalNumHits;
        double frstLvlHitRatio;
        double scndLvlHitRatio;

        Cache<String> lvlOneCache = new Cache<String>(lvlOneCacheCapacty);
        System.out.println("First level cache with " + lvlOneCacheCapacty + " entries has been created.");
        Cache<String> lvlTwoCache = new Cache<String>(lvlTwoCacheCapacty);
        System.out.println("Second level cache with " + lvlTwoCacheCapacty + " entries has been created." + "\n");

        String fileName = userInput[3];

        try {
            File userFile = new File(fileName);
            Scanner fileScanner = new Scanner(userFile);
            while(fileScanner.hasNextLine()){
                String currentLine = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(currentLine);
                while(lineScanner.hasNext()){
                    String currentWord = lineScanner.next();
                    boolean inFrstLvlCache = lvlOneCache.getObject(currentWord);//searches if cache contains word
                    boolean inScndLvlCache = lvlTwoCache.getObject(currentWord);
                    frstLvlRefer++;
                    if(inFrstLvlCache == true) {//checks if in cache
                        frstLvlHit++;
                        lvlOneCache.addObject(currentWord);
                        lvlTwoCache.addObject(currentWord);
                    } else { //if not in first level cache then checked in second level cache 
                        scndLvlRefer++;
                        if(inScndLvlCache == true) {
                                scndLvlHit++;
                                lvlTwoCache.addObject(currentWord);
                                lvlOneCache.addObject(currentWord);
                        } else {
                                lvlOneCache.addObject(currentWord);
                                lvlTwoCache.addObject(currentWord);
                        }
                    }
                }
                lineScanner.close();
            }
            fileScanner.close();
            totalNumHits = frstLvlHit + scndLvlHit;
            globalHitRatio = (double)totalNumHits/frstLvlRefer;
            System.out.println("The number of global references: " + frstLvlRefer);
            System.out.println("The number of global cache hits: " + totalNumHits);
            System.out.println("The global hit ratio           : " + globalHitRatio + "\n");

            frstLvlHitRatio = (double)frstLvlHit/frstLvlRefer;
            System.out.println("The number of 1st-level references: " + frstLvlRefer);
            System.out.println("The number of 1st-level cache hits: " + frstLvlHit);
            System.out.println("The 1st-level hit ratio           : " + frstLvlHitRatio + "\n");

            scndLvlHitRatio = (double)scndLvlHit/scndLvlRefer;
            System.out.println("The number of 2nd-level references: " + scndLvlRefer);
            System.out.println("The number of 2nd-level cache hits: " + scndLvlHit);
            System.out.println("The 2nd-level hit ratio           : " + scndLvlHitRatio + "\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.out.println("Usage: java [Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>],\n [Test 1 <cache size> <input textfile name>]");
        }   
    }
}

