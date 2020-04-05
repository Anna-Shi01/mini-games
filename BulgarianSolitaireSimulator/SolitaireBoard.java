// Name: ZIRAN SHI
// USC NetID: ziranshi
// CSCI 455 PA2
// Spring 2019


import java.util.ArrayList;
import java.util.Random;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
  by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.
  (See comments below next to named constant declarations for more details on this.)
*/


public class SolitaireBoard {



    public static final int NUM_FINAL_PILES = 9;
    // number of piles in a final configuration
    // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)

    public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
    // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
    // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
    // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

    // Note to students: you may not use an ArrayList -- see assgt description for details.

    /**
     Representation invariant:
     1. numPiles is the number of non-zero numbers in the array
     2. 0 <= numPile <= CARD_TOTAL
     3. total is the sum of the non-zero numbers in the array
     4. 0 <= total <= CARD_TOTAL
     5.for rest the of the array, the number should be o
     6. 0 <= nums.length <= CARD_TOTAL


     */
    private int[] nums;
    //generate the nums[] array to store the number,which size is CARD_TOTAL

    private int numPiles;
    // using numPiles to indicate the number of the non-zero numbers in the nums array

    private int total;
    // using total to indicate the total non-zero numbers of the nums array
    private int step;
    //  indicate how many steps you run the playRound


    /**
     Creates a solitaire board with the configuration specified in piles.
     piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
     PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
     */
    public SolitaireBoard(ArrayList<Integer> piles) {
        numPiles=0;
        total = 0;
        step = 0;
        nums= new int[CARD_TOTAL];
        for (int i = 0; i <piles.size(); i++){
             nums[i]=piles.get(i);
             numPiles++;
             total +=nums[i];
        }
        System.out.println("Initial configuration: " + configString());
        assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
    }


    /**
     Creates a solitaire board with a random initial configuration.
     */
    public SolitaireBoard() {
        Random generator = new Random();
        step= 0;
        numPiles = 0;
        total = 0;

        nums = new int[CARD_TOTAL];
        for (int i = 0; i < nums.length; i++) {

            nums[i]= generator.nextInt(46-total);
            while(nums[i]==0) {
                nums[i]= generator.nextInt(46-total);
            }
            numPiles++;
            total += nums[i];
            if(total==45) {
                break;
            }
        }
        System.out.println("Initial configuration: " + configString());
        assert isValidSolitaireBoard();
    }


    /**
     Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
     of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
     The old piles that are left will be in the same relative order as before,
     and the new pile will be at the end.
     */
    public void playRound() {
        int temp = numPiles;
        int i = 0;
        for (int j = 0; j < numPiles; j++) {
            nums[j] = nums[j] - 1;
            if (nums[j] != 0) {
                nums[i] = nums[j];
               i++;
            }
        }

        nums[i] = temp;
        numPiles = i + 1;

        for (int k = numPiles; k < nums.length; k++) {
            nums[k] = 0;
        }
        step++;
        System.out.println("[" + step + "] Current configuration: " + configString());
        assert isValidSolitaireBoard();
    }





    /**
     Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
     piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
     */

    public boolean isDone() {

        if (numPiles != NUM_FINAL_PILES ) {
           return false;
        }
        int[] testArray = new int[9];

        for (int i = 0; i < numPiles; i++) {
            if (nums[i]>9){
                return false;
            }
            testArray[nums[i] - 1] = 1;
        }
        for (int j = 0; j < testArray.length; j++) {
            if (testArray[j] != 1) {
                return false;
            }
        }
        assert isValidSolitaireBoard();
        return true;
    }



    /**
     Returns current board configuration as a string with the format of
     a space-separated list of numbers with no leading or trailing spaces.
     The numbers represent the number of cards in each non-empty pile.
     */
    public String configString() {
        String name = "";
        for(int i = 0; i< numPiles; i++) {
            name = name + nums[i] +" ";
        }
        assert isValidSolitaireBoard();
        return name;
    }


    /**
     Returns true iff the solitaire board data is in a valid state
     (See representation invariant comment for more details.)
     */
    private boolean isValidSolitaireBoard() {
        if (nums.length< 0||numPiles < 0 || numPiles > CARD_TOTAL) {
            return false;  // dummy code to get stub to compile

        }
        if(total> CARD_TOTAL|| nums.length > CARD_TOTAL){
            return false;
        }
        for (int i = numPiles; i < nums.length; i++){
           if (nums[i]!=0){
               return false;
           }
       }
        return true;
    }


    // <add any additional private methods here>


}


