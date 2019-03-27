
package console_nonoop_2players;
import java.util.Scanner;
public class Console_NonOOP_2players 
{
    //constant names de bieu dien noi dung cua 1 
    public static final int Empty = 0;
    public static final int X = 1;
    public static final int O = 2;
    
    //constamt names de bieu dien cac trang thai cua game
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int X_win = 2;
    public static final int O_win = 3;
    
    //board game
    public static int ROW=3;
    public static int COL=3;
    public static int[][] board;
    
    public static int currentState; //trang thai hien tai cua game (PLAYING, DRAW, X_win, O_win)
    public static int currentPlayer; //nguoi choi hien tai (X, O)
    public static int currentRow, currentCol; //vi tri hien tai cua cell
    
    public static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) 
    {
        do
        {
            System.out.println("Enter the board size: ");
            System.out.print("Row size(3<=row<=100): ");
            ROW = scanner.nextInt();
            System.out.print("Col size(3<=col<=100): ");
            COL = scanner.nextInt();
            board = new int[ROW][COL];
            initGame();
            printBoard();
            do{
                playerMove(currentPlayer); //update currentRow va currentCol
                updateGame(); //update currentState
                printBoard();
                //print message neu game over
                if(currentState==X_win)
                    System.out.println("Player X win, game over");
                else if(currentState==O_win)
                    System.out.println("Player O win, game over");
                else if(currentState==DRAW)
                    System.out.println("It's a draw game, game over");
                //doi luot choi
                currentPlayer = (currentPlayer==X)?O:X;
            }while(currentState==PLAYING);
            System.out.print("Play again(y|n): ");
            String q_string = scanner.next();
            char q = q_string.charAt(0);
            if((q_string.length()>1)||(q!='Y'&&q!='y'))
                System.exit(0);
            else
                System.out.println("\nNew game");
        }while(true);
    }
    public static void initGame()
    {
        for(int i=0; i<ROW; i++)
            for(int j=0; j<COL; j++)
                board[i][j] = Empty;
        currentState = PLAYING;
        currentPlayer = X;
    }
    //print board game
    public static void printBoard()
    {
        for(int i=0; i<ROW; i++)
        {
            for(int j=0; j<COL; j++)
            {
                printCell(board[i][j]);
                if(j<COL-1)
                    System.out.print("|");
                else
                    System.out.print("\n");
            }
            if(i<ROW-1)
            {
                for(int k=0; k<COL; k++)
                    System.out.print("__");
            }
            System.out.print("\n");
        }          
    }
    public static void printCell(int content)
    {
        if(content==Empty)
            System.out.print(" ");
        else if(content==X)
            System.out.print("X");
        else
            System.out.print("O");
    }
    //nguoi choi di mot nuoc cell(X, O)
    public static void playerMove(int currentPlayer)
    {
        int validMove=0;
        do
        {
            if(currentPlayer == X)
                System.out.println("Player X, enter your move (row/col): ");
            else
                System.out.println("Player O, enter your move (row/col): ");
            int r = scanner.nextInt() - 1;
            int c = scanner.nextInt() - 1;
            if(r>=0 && r<ROW && c>=0 && c<COL && board[r][c]==Empty)
            {
                currentRow = r;
                currentCol = c;
                board[currentRow][currentCol] = currentPlayer;
                validMove = 1;
            }
            else
            {
                System.out.println("This move is not valid, Try again");
            }
            
        }while(validMove==0);
    }
    //kiem tra co nguoi choi co thang sau moi luot choi khong 
    public static boolean hasWon()
    {
        int r=currentRow;
        int c=currentCol;
        //thang theo hang ngang
        if(c>=1 && c<=(COL-2) && board[r][c]==board[r][c-1] && board[r][c]==board[r][c+1])
            return true;
        if(c>=0 && c<=(COL-3) && board[r][c]==board[r][c+1] && board[r][c]==board[r][c+2])
            return true;
        if(c>=2 && c<=(COL-1) && board[r][c]==board[r][c-1] && board[r][c]==board[r][c-2])
            return true;
        //thang theo hang doc
        if(r>=1 && r<=(ROW-2) && board[r][c]==board[r-1][c] && board[r][c]==board[r+1][c])
            return true;
        if(r>=0 && r<=(ROW-3) && board[r][c]==board[r+1][c] && board[r][c]==board[r+2][c])
            return true;
        if(r>=2 && r<=(ROW-1) && board[r][c]==board[r-1][c] && board[r][c]==board[r-2][c])
            return true;
        //thang theo duong cheo 1
        if(r<=(ROW-3) && c<=(COL-3) && board[r][c]==board[r+1][c+1] && board[r][c]==board[r+2][c+2])
            return true;
        if(r>=1 && c>=1 && r<=(ROW-2) && c<=(COL-2) && board[r][c]==board[r-1][c-1] && board[r][c]==board[r+1][c+1])
            return true;
        if(r>=2 && c>=2 && board[r][c]==board[r-1][c-1] && board[r][c]==board[r-2][c-2])
            return true;
        //thang theo duong cheo 2
        if(c>=2 && r<=(ROW-3) && board[r][c]==board[r+1][c-1] && board[r][c]==board[r+2][c-2])
            return true;
        if(c<=(COL-2) && r>=1 && c>=1 && r<=(ROW-2)&& board[r][c]==board[r-1][c+1] && board[r][c]==board[r+1][c-1])
            return true;
        if(c<=(COL-3) && r>=2 && board[r][c]==board[r-1][c+1] && board[r][c]==board[r-2][c+2])
            return true;
        return false;
    }
    //hoa khi board da day ma chua co nguoi choi nao thang
    public static boolean isDraw()
    {
        for(int i=0; i<ROW; i++)
        {
            for(int j=0; j<COL; j++)
            {
                if(board[i][j]==Empty)
                    return false;
            }
        }
        return true;
    }
    //cap nhat trang thai game currentState sau moi luot choi
    public static void updateGame()
    {
        if(hasWon())
            currentState = (currentPlayer==X)?X_win : O_win;
        else if(isDraw())
            currentState = DRAW;
        else
            currentState = PLAYING;
    }
}
