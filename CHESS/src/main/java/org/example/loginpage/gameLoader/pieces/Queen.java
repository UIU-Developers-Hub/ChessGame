package org.example.loginpage.gameLoader.pieces;

import org.example.loginpage.gameLoader.GamePanel;
import org.example.loginpage.gameLoader.Type;

public class Queen extends piece{
    public Queen(int color, int col, int row) {
        super(color, col, row);
        type = Type.QUEEN;
        if (color == GamePanel.WHITE) {
            setImage("/piece/wQueen");
        } else {
            setImage("/piece/BQueen");
        }
    }
    public boolean possibleMove(int MoveCol, int MoveRow) {
        if (inBoard(MoveCol, MoveRow) && inSameGrid(MoveCol, MoveRow) == false) {
            if (MoveCol == preCol || MoveRow == preRow) {
                if (isValidSquare(MoveCol, MoveRow) && SkipPiecesOnLine(MoveCol, MoveRow) == false) {
                    return true;
                }
            }
            if (Math.abs(MoveCol - preCol) == Math.abs(MoveRow - preRow)) {
                if (isValidSquare(MoveCol, MoveRow) && SkipPiecesDiagonal(MoveCol, MoveRow) == false) {
                    return true;
                }
            }
        }
        return false;
    }
}