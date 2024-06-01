package org.example.loginpage.gameLoader.pieces;

import org.example.loginpage.gameLoader.GamePanel;
import org.example.loginpage.gameLoader.Type;

public class King extends piece{
    public King(int color, int col, int row) {
        super(color, col, row);
        type = Type.KING;
        if (color == GamePanel.WHITE) {
            setImage("/piece/wking");
        } else {
            setImage("/piece/BKing");
        }
    }
    public boolean possibleMove(int MoveCol, int MoveRow){
        if(inBoard(MoveCol, MoveRow)){
            if(Math.abs(MoveCol - preCol)+ Math.abs( MoveRow -preRow)==1 ||
                    Math.abs(MoveCol- preCol)*Math.abs( MoveRow-preRow)==1){
                if(isValidSquare(MoveCol, MoveRow)){
                    return true;
                }
            }
            //castling
            if(!alreadyMove){
                //right castling
                if(MoveCol == preCol+2 && MoveRow == preRow && !SkipPiecesOnLine(MoveCol, MoveRow)) {
                    for (piece piecep : GamePanel.simPieces) {
                        if(piecep.col == preCol+3 && piecep.row == preRow && !piecep.alreadyMove){
                            GamePanel.CastlingPiece =piecep;
                            return true;
                        }
                    }
                }
                // left castling
                if(MoveCol == preCol -2 && MoveRow == preRow && !SkipPiecesOnLine(MoveCol, MoveRow)){
                    piece p[] = new piece[2];
                    for(piece piecep: GamePanel.simPieces){
                        if(piecep.col == preCol-3 && piecep.row == MoveRow){
                            p[0]= piecep;
                        }
                        if(piecep.col == preCol-4 && piecep.row == MoveRow){
                            p[1]=piecep;
                        }
                        if(p[0]== null && p[1]!=null && !p[1].alreadyMove){
                            GamePanel.CastlingPiece = p[1];
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }
}
