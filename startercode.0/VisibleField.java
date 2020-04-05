// Name:Ziran Shi
// USC NetID:ziranshi
// CS 455 PA3
// Spring 2019


/**
  VisibleField class
  This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
  user can see about the minefield), Client can call getStatus(row, col) for any square.
  It actually has data about the whole current state of the game, including  
  the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
  It also has mutators related to actions the player could do (resetGameDisplay(), cycleGuess(), uncover()),
  and changes the game state accordingly.
  
  It, along with the MineField (accessible in mineField instance variable), forms
  the Model for the game application, whereas GameBoardPanel is the View and Controller, in the MVC design pattern.
  It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from 
  outside this class via the getMineField accessor.  
 */
public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method 
   // getStatus(row, col)).
   
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------   
  
   // <put instance variables here>
   private int numCols;  //number of rows of the field
   private int numRows;  // number of columns of the field
   private int[][] visField;  // form a field that user can see
   private int totalMines;  // total mines of the field
   private MineField mineField;  // field that indicate whether a square has a mine or not
   private static final int UNCOVERED_NUMBER = 0; //a square with no mines has been covered




   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the mines covered up, no mines guessed, and the game
      not over.
      @param mineField  the minefield to use for for this VisibleField
    */

   public VisibleField(MineField mineField) {
      this.numRows = mineField.numRows();
      this.numCols = mineField.numCols();
      this.totalMines = mineField.numMines();
      this.mineField = mineField;
      this.visField = new int[numRows][numCols];
      resetGameDisplay();
   }
   
   
   /**
      Reset the object to its initial state (see constructor comments), using the same underlying
      MineField. 
   */     
   public void resetGameDisplay() {
      for(int i = 0; i< numRows; i++) {
         for(int j = 0; j < numCols; j++){
            visField[i][j] = COVERED;
         }
      }
      
   }

   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   public MineField getMineField() {

      return mineField;

   }
   
   
   /**
      Returns the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  See the public constants at the beginning of the class
      for the possible values that may be returned, and their meanings.
      PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {

      return visField[row][col];

   }

   
   /**
      Returns the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
      or not.  Just gives the user an indication of how many more mines the user might want to guess.  So the value can
      be negative, if they have guessed more than the number of mines in the minefield.     
      @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      int nums = 0;
      for( int i = 0; i< numRows; i++) {
         for( int j = 0; j< numCols; j++) {
            if (visField[i][j] == MINE_GUESS){
               nums++;
            }
         }
      }
      return totalMines - nums;

   }
 
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
      changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
      changes it to COVERED again; call on an uncovered square has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
    */
   public void cycleGuess(int row, int col) {
      if( visField[row][col] == COVERED) {
         visField[row][col] = MINE_GUESS;

      }else if (visField[row][col] == MINE_GUESS){
         visField[row][col] = QUESTION;

      }else if(visField[row][col] == QUESTION) {
         visField[row][col] = COVERED;
      }
      
   }

   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in 
      the neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form 
      (possibly along with parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
      or a loss (opened a mine).
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {
      if (mineField.hasMine(row, col)) {
         visField[row][col] = EXPLODED_MINE;
         return false;
      } else {
            search(row, col);
      }
      return true;
   }

   
   /**
      Returns whether the game is over.
      (Note: This is not a mutator.)
      @return whether game over
    */
   public boolean isGameOver() {
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            if (visField[i][j] == EXPLODED_MINE) {   //if having a EXPLODED_MINE, game failed
               showLoseResult();
               return true; // lose game， game over
            }
         }
      }

      for(int i = 0; i< numRows; i++){    //condition that game will not stops
         for( int j = 0; j< numCols; j++){
            if(!mineField.hasMine(i, j)){
               if(visField[i][j] == COVERED || visField[i][j] == MINE_GUESS ||  visField[i][j] == QUESTION){
                  return false;
               }
            }
         }
      }
      showWinResult();
      return true;  // win game， game over
   }



   /**
      Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
      vs. any one of the covered states).
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {

      if (visField[row][col] >= UNCOVERED_NUMBER) {
         return true;
      }
      return false;

   }



   /**
   helper function of uncover:
    base case: 1: skip a square if it is MINE_GUESS
               2. when number of adjacent mines of a square != 0, show the number
    if a square does not a adjacent mine, recursive until it stops
    */
   private void search( int row, int col) {
      if (visField[row][col] == MINE_GUESS || isUncovered(row, col)) {   // Does not uncover squares that have the status MINE_GUESS.
         return;
      }
      if (mineField.numAdjacentMines(row, col) != 0) { //boundary squares that show the number of mines
         visField[row][col] = mineField.numAdjacentMines(row, col);
         return;
      }
      visField[row][col] = UNCOVERED_NUMBER;

      for(int i = row -1; i <= row + 1; i++) {
         for (int j = col - 1; j <= col + 1; j++) {
            if (mineField.inRange(i, j) ) {
               search( i, j );
            }
         }
      }
   }

   /**
    when game is over (failed), the field will show the lose result
    when a square has a mine, and this square is COVERED or QUESTION, this result will change to MINE
    when a square doesn't has a mine, and it is MINE_GUESS, this result will change to INCORRECT_GUESS
    */

   private void showLoseResult() {
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            if(mineField.hasMine(i,j) && (visField[i][j] == QUESTION || visField[i][j] == COVERED)){
               visField[i][j] = MINE;

            } else if (!mineField.hasMine(i,j) && visField[i][j] == MINE_GUESS ) {
                  visField[i][j] = INCORRECT_GUESS;
            }
         }
      }
   }
   /**
    when game is over (win), the field will show the win result
    when a square has a mine, and this square is COVERED or QUESTION, this result will change to MINE
    */
   private void showWinResult() {
      for (int i = 0; i < numRows; i++) {    // show win result
         for (int j = 0; j < numCols; j++) {
            if (visField[i][j] == QUESTION || visField[i][j] == COVERED) {
               visField[i][j] = MINE_GUESS;
            }
         }
      }

   }





}

