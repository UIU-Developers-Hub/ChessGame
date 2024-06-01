package org.example.loginpage.gameLoader.pieces;


import org.example.loginpage.gameLoader.GamePanel;
import org.example.loginpage.gameLoader.Type;

public class Pawn extends piece {
    public Pawn(int color, int col, int row) {
        super(color, col, row);
        type= Type.PAWN;
        if (color == GamePanel.WHITE) {
            setImage("/piece/wPawn");
        } else {
            setImage("/piece/BPawn");
        }
    }
    public boolean possibleMove(int MoveCol, int MoveRow){
        int movieValue;
        if(color == GamePanel.WHITE){
            movieValue=-1;
        }
        else {
            movieValue=1;
        }
        overwritePieces = CheckOverWrite(MoveCol,MoveRow);
        // for move forward;
        if(MoveCol == preCol && MoveRow ==preRow+movieValue && overwritePieces == null){
            return true;
        }
        // for move 2 square;
        if(MoveCol == preCol && MoveRow == preRow + movieValue*2 && overwritePieces==null && !alreadyMove &&
                !SkipPiecesOnLine(MoveCol, MoveRow)){
            return true;
        }
        // Capture diagonal move and remove pieces
        if(Math.abs(MoveCol - preCol)== 1 && MoveRow == preRow + movieValue && overwritePieces != null && overwritePieces.color != color){
            return true;
        }
        // En-Passant
        if(Math.abs(MoveCol - preCol)== 1 && MoveRow == preRow + movieValue){
            for(piece p : GamePanel.simPieces){
                if(p.col == MoveCol && p.row == preRow && p.twoStepMove){
                    overwritePieces = p;
                    return true;
                }
            }
        }
        return false;
    }
}
