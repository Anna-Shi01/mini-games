// Name: ZIRAN SHI
// USC NetID: ziranshi
// CSCI 455 PA2
// Spring 2019


import java.util.ArrayList;
import java.util.Scanner;
/**
    This program is the simulator of Solitaire Board.
    It has three game modes. When user input -u in the command that userConfig becomes true, and
    needs user to inout the array, and the computer deal with the array directly.
    When user input -s in the command that singleStep becomes true, the program will generate an array randomly,
    and user needs to enter return to continue
    when neither -u or -s was inputed,  default mode will work, the program will generate the array and deal with it
    with sequence
 */
public class BulgarianSolitaireSimulator {

    public static void main(String[] args) {


        boolean singleStep = false;
        boolean userConfig = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-u")) {
                userConfig = true;
            } else if (args[i].equals("-s")) {
                singleStep = true;
            }
        }

        Scanner in = new Scanner(System.in); //


        if (userConfig) {       //when "-u"called, userConfig is true, and call the doUserStep method
            doUserStep(in);
        }else if(singleStep){   //when "-s"called, singleStep is true, and call the doSingleStep method
            doSingleStep(in);
        }else{                 //when neither "-s" or "-u" was called, and call the doDefault method
            doDefaultStep();
        }


    }

     /**
      the user step method, user need to input a sequence of number, which the total is CARD_TOTAL, the
      number can't be negative or letter，the judgment is also written in the following method.
      and set up a object to call the isDone，user needs to enter the return to call the playRound
      method
     */
    private  static void doUserStep(Scanner in){
        ArrayList<Integer> arrayNum = new ArrayList<Integer>();  //create arraylist object named arrayNum
        testInput(arrayNum,in);
        SolitaireBoard userPlay = new SolitaireBoard(arrayNum);//create SolitaireBoard object named userPlay
        while (!userPlay.isDone()) {
            userPlay.playRound();
        }
        System.out.println("Done!");

    }

    /**
     the testInput method is to test whether numbers that user inputs meets our requirement
     our requirement: 1.sum of input is 45   2. each number is no less than 1 or larger than card_total
     3.the input type should be int
     */
    private static void testInput(ArrayList<Integer> arrayNum, Scanner in) {
        boolean isValid = false;
        System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL);
        System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
        System.out.print("Enter a space separated list of numbers:");
        int sum = 0;
        while (!isValid) {
            String reader = "";
            if (in.hasNext()) {
                reader = in.nextLine();
            }
            Scanner inputScanner = new Scanner(reader);
            while (inputScanner.hasNext()) {
                if (!inputScanner.hasNextInt()) {
                    arrayNum.clear();
                    break;
                }
                int number = inputScanner.nextInt();
                if (number <= 0 || number > 45) {
                    arrayNum.clear();
                    break;
                }
                sum += number;
                arrayNum.add(number);
            }
            if (sum != SolitaireBoard.CARD_TOTAL && !isValid) {
                System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
                System.out.println("Please enter a space-separated list of positive integers followed by newline:");
                sum = 0;
                arrayNum.clear();
                isValid = false;
            } else {
                isValid = true;
            }
        }
    }


      /**
      the single step  method, set up a object to call the isDone， generating a random configuration.，
      user needs to enter the return to call the playRound method
       */

    private static void doSingleStep(Scanner in){
        SolitaireBoard singlePlay = new SolitaireBoard();  //create SolitaireBoard object named singlePlay

        while(!singlePlay.isDone()){
            System.out.print("<Type return to continue>");
            in.nextLine();
            singlePlay.playRound();
        }
        System.out.println("Done!");

    }



      /**
      the default step method, set up a object to call the isDone，using the while loop to keep the computer
      solve the array until it is done
       */
    private static void doDefaultStep(){
        SolitaireBoard defaultPlay = new SolitaireBoard();//create SolitaireBoard object named defaultPlay
        while (!defaultPlay.isDone() ) {

            defaultPlay.playRound();

        }
        System.out.println("Done!");
    }
}






