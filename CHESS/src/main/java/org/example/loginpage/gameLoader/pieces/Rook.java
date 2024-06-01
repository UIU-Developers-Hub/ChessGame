package org.example.loginpage.gameLoader.pieces;
import org.example.loginpage.gameLoader.GamePanel;
import org.example.loginpage.gameLoader.Type;

public class Rook extends piece{
    public Rook(int color, int col, int row) {
        super(color, col, row);
        type = Type.ROOK;
        if (color == GamePanel.WHITE) {
            setImage("/piece/wRook");
        } else {
            setImage("/piece/BRook ");
        }

    }
    public boolean possibleMove(int MoveCol, int MoveRow){
        if(inBoard(MoveCol, MoveRow) && inSameGrid(MoveCol,  MoveRow) == false){
            if(MoveCol == preCol ||  MoveRow == preRow){
               if(isValidSquare(MoveCol, MoveRow) && SkipPiecesOnLine(MoveCol, MoveRow)==false){
                   return true;
               }
            }
        }
        return false;
    }
}