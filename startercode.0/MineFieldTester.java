public class MineFieldTester {
    public static void main(String[] args){
        MineField aaa = new MineField(4,4,4);
        aaa.populateMineField(2,2);
        for(int i = 0; i < aaa.numRows(); i++){
            for(int j = 0; j< aaa.numCols(); j++){
                System.out.print(aaa.hasMine(i,j) + "  ");
            }
            System.out.println(" ");
        }

        //System.out.println(aaa.numMines());
        boolean[][] tester=
                {{false, false, false, false},
                {true, false, false, false},
                {false, true, true, false},
                {false, true, false, true}};

        MineField bbb = new MineField(tester);
        for (int i = 0; i < aaa.numRows(); i++){
            for (int j = 0; j < aaa.numCols(); j++){
                if(aaa.hasMine(i,j)){
                    System.out.print("m ");
                    continue;
                }
                System.out.print(aaa.numAdjacentMines(i,j) + " ");
            }
            System.out.println(" ");
        }
    }
}
