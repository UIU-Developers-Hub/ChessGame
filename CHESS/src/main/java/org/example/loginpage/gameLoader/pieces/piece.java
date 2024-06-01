package org.example.loginpage.gameLoader.pieces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.loginpage.gameLoader.Board;
import org.example.loginpage.gameLoader.GamePanel;
import org.example.loginpage.gameLoader.Type;

public class piece {
  public Image image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Type type;
    public boolean alreadyMove, twoStepMove;
    public piece overwritePieces;

    public piece(int color, int col, int row) {
        this.color = color;
        this.col = col;
        this.row = row;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    public void setImage(String imagePath) {
        try {
            this.image = new Image(getClass().getResourceAsStream(imagePath + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getX(int col) {
        return col * Board.Square_size;
    }

    public int getY(int row) {
        return row * Board.Square_size;
    }

    public int getCol(int x){
        return (x+ Board.Half_Square_size)/Board.Square_size;
    }
    public int getRow(int y){
        return (y+ Board.Half_Square_size)/Board.Square_size;
    }
    public void updatePosition(){
        // check En Passant
        if(type == Type.PAWN){
            if(Math.abs(row - preRow)== 2){
                twoStepMove = true;
            }
        }
        x= getX(col);
        y=getY(row);
        preCol =getCol(x);
        preRow = getRow(y);
        alreadyMove =true;
    }
    public void resetPosition(){
        col = preCol;
        row = preRow;
        x= getX(col);
        y= getY(row);
    }
    public piece CheckOverWrite(int MoveCol, int  MoveRow){
        for(piece piece:GamePanel.simPieces){
            if(piece.col == MoveCol && piece.row ==  MoveRow && piece!=this ){
                return piece;
            }
        }
        return null;
    }
    public boolean isValidSquare(int MoveCol, int  MoveRow){
        overwritePieces = CheckOverWrite(MoveCol, MoveRow);
        if(overwritePieces == null){
            return true;
        }
        else {
            if(overwritePieces.color != this.color){
                return true;
            }
            else {
                overwritePieces =null;
            }
        }
        return false;
    }
    public boolean inSameGrid(int moveCol, int MoveRow){
        if(moveCol == preCol && MoveRow == preRow){
            return true;
        }
        return false;
    }
    public boolean possibleMove(int targetCol, int  MoveRow){
        return false;
    }
    public boolean inBoard(int MoveCol, int  MoveRow){
        if(MoveCol >=0 && MoveCol<=7 &&  MoveRow >=0 &&  MoveRow <=7){
            return true;
        }
        return false;
    }
    public boolean SkipPiecesOnLine(int MoveCol, int MoveRow){
        // move to left Rook, Queen
        for(int c=preCol-1; c > MoveCol; c--){
            for(piece pieces : GamePanel.simPieces){
                if(pieces.col == c && pieces.row == MoveRow){
                    overwritePieces = pieces;
                    return true;
                }
            }
        }
        // move right
        for(int c = preCol+1; c < MoveCol; c++){
            for(piece piece :GamePanel.simPieces){
                if(piece.col == c && piece.row == MoveRow){
                    overwritePieces =piece;
                    return true;
                }
            }
        }
        //move up
        for(int c=preRow-1; c > MoveRow; c--){
            for(piece pieces :GamePanel.simPieces){
                if(pieces.col == MoveCol && pieces.row == c){
                    overwritePieces =pieces;
                    return true;
                }
            }
        }
        //move down
        for(int c=preRow+1; c < MoveRow; c++){
            for(piece piece :GamePanel.simPieces){
                if(piece.col == MoveCol && piece.row == c){
                    overwritePieces =piece;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean SkipPiecesDiagonal(int MoveCol , int MoveRow){
        if(MoveRow <preRow){
            //UP left pieces check
            for(int x= preCol-1; x > MoveCol; x--){
                int difference= Math.abs(x-preCol);
                for(piece piece: GamePanel.simPieces){
                    if(piece.col == x && piece.row == preRow-difference){
                        overwritePieces = piece;
                        return true;
                    }
                }
            }
            // UP right Check
            for(int x= preCol+1; x < MoveCol; x++){
                int difference= Math.abs(x-preCol);
                for(piece piece: GamePanel.simPieces){
                    if(piece.col == x && piece.row == preRow-difference){
                        overwritePieces = piece;
                        return true;
                    }
                }
            }
        }
        if(MoveRow >preRow){
            // down left
            for(int x= preCol-1; x > MoveCol; x--){
                int difference= Math.abs(x-preCol);
                for(piece piece: GamePanel.simPieces){
                    if(piece.col == x && piece.row == preRow + difference){
                        overwritePieces = piece;
                        return true;
                    }
                }
            }
            //down right
            for(int x= preCol+1; x < MoveCol; x++){
                int difference= Math.abs(x-preCol);
                for(piece piece: GamePanel.simPieces){
                    if(piece.col == x && piece.row == preRow + difference){
                        overwritePieces = piece;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public int getIndex(){
        for(int index =0; index < GamePanel.simPieces.size(); index++){
            if(GamePanel.simPieces.get(index)== this){
                return index;
            }
        }
        return 0;
    }
    public void draw(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, x, y, Board.Square_size, Board.Square_size);
        }
    }
}
