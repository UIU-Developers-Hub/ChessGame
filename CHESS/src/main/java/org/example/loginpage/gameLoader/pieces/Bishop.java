package org.example.loginpage.gameLoader.pieces;

import org.example.loginpage.gameLoader.GamePanel;
import org.example.loginpage.gameLoader.Type;

public class Bishop extends piece{
    public Bishop(int color, int col, int row) {
        super(color, col, row);
        type = Type.BISHOP;
        if (color == GamePanel.WHITE) {
            setImage("/piece/wbishop");
        } else {
            setImage("/piece/Bbishop");
        }
    }
    public boolean possibleMove(int MoveCol, int MoveRow){
        if(inBoard(MoveCol, MoveRow) && inSameGrid(MoveCol,  MoveRow) == false){
            if(Math.abs(MoveCol - preCol) ==  Math.abs(MoveRow-preRow)){
                if(isValidSquare(MoveCol, MoveRow) && SkipPiecesDiagonal(MoveCol, MoveRow)==false){
                    return true;
                }
            }
        }
        return false;
    }
}
