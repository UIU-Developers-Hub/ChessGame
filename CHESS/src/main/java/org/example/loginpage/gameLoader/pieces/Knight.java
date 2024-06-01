package org.example.loginpage.gameLoader.pieces;

import org.example.loginpage.gameLoader.GamePanel;
import org.example.loginpage.gameLoader.Type;

public class Knight extends piece{
    public Knight(int color, int col, int row) {
        super(color, col, row);
        type= Type.KNIGHT;
        if (color == GamePanel.WHITE) {
            setImage("/piece/wKnight");
        } else {
            setImage("/piece/BKnight");
        }
    }
    public boolean possibleMove(int NextCol, int NextRow){
        if(inBoard(NextCol,NextRow)){
            if(Math.abs(NextCol - preCol)* Math.abs(NextRow - preRow)==2){
                if(isValidSquare(NextCol,NextRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
