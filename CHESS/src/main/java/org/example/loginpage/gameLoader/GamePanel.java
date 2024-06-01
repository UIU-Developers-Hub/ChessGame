package org.example.loginpage.gameLoader;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import org.example.loginpage.gameLoader.pieces.*;

import java.io.IOException;
import java.util.ArrayList;

public class GamePanel {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    private final int FPS = 60;
    private Canvas canvas;
    private ArrayList<piece> PiecesA = new ArrayList<>();
    public static ArrayList<piece> simPieces = new ArrayList<>();
    ArrayList<piece> promoteAr= new ArrayList<>();
    piece activeP,CheckKingP;
    public static piece CastlingPiece;
    private Board board = new Board();
    MouseController mouseHandler = new MouseController();
    boolean canMove, validSquare,promotion,gameOver;
    private AnimationTimer gameLoop;
    public static  final int WHITE =0;public static  final int BLACK =1;
    int currentColor = WHITE;
    private  void  switchPlayer(){
        if(currentColor == WHITE){
            currentColor=BLACK;
            //reset Black Two step move status
            for(piece p : PiecesA){
                if(p.color == BLACK){
                    p.twoStepMove = false;
                }
            }
        }
        else {
            currentColor = WHITE;
            //reset Black Two step move status
            for(piece p : PiecesA){
                if(p.color == WHITE){
                    p.twoStepMove = false;
                }
            }
        }
        activeP=null;
    }
    public GamePanel() {
        canvas = new Canvas(WIDTH, HEIGHT);

        canvas.setOnMousePressed(mouseHandler::mousePressed);
        canvas.setOnMouseReleased(mouseHandler::mouseReleased);
        canvas.setOnMouseDragged(mouseHandler::mouseDragged);
        canvas.setOnMouseMoved(mouseHandler::mouseMoved);

        setPieces();
        copyPieces(PiecesA, simPieces);
    }
    public void launchGame(){
        gameLoop = new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000 / FPS) {
                    update();
                    draw();
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }
    private void setPieces() {

        PiecesA.add(new Pawn(WHITE,0,6));
        PiecesA.add(new Pawn(WHITE,1,6));
        PiecesA.add(new Pawn(WHITE,2,6));
        PiecesA.add(new Pawn(WHITE,3,6));
        PiecesA.add(new Pawn(WHITE,4,6));
        PiecesA.add(new Pawn(WHITE,5,6));
        PiecesA.add(new Pawn(WHITE,6,6));
        PiecesA.add(new Pawn(WHITE,7,6));

        PiecesA.add(new Rook(WHITE,7,7));
        PiecesA.add(new Rook(WHITE,0,7));
        PiecesA.add(new Knight(WHITE,1,7));
        PiecesA.add(new Knight(WHITE,6,7));
        PiecesA.add(new Bishop(WHITE,2,7));
        PiecesA.add(new Bishop(WHITE,5,7));
        PiecesA.add(new Queen(WHITE,3,7));
        PiecesA.add(new King(WHITE,4,7));
//Black;
        PiecesA.add(new Pawn(BLACK,0,1));
        PiecesA.add(new Pawn(BLACK,1,1));
        PiecesA.add(new Pawn(BLACK,2,1));
        PiecesA.add(new Pawn(BLACK,3,1));
        PiecesA.add(new Pawn(BLACK,4,1));
        PiecesA.add(new Pawn(BLACK,5,1));
        PiecesA.add(new Pawn(BLACK,6,1));
        PiecesA.add(new Pawn(BLACK,7,1));

        PiecesA.add(new Rook(BLACK,0,0));
        PiecesA.add(new Rook(BLACK,7,0));
        PiecesA.add(new Knight(BLACK,1,0));
        PiecesA.add(new Knight(BLACK,6,0));
        PiecesA.add(new Bishop(BLACK,2,0));
        PiecesA.add(new Bishop(BLACK,5,0));
        PiecesA.add(new Queen(BLACK,3,0));
        PiecesA.add(new King(BLACK,4,0));
    }
    private void copyPieces(ArrayList<piece> source, ArrayList<piece> target) {
        target.clear();
        target.addAll(source);
    }
    private void update() {
        if(promotion){
            promoting();
        }
        else if(!gameOver){
            // mouse pressed
            if (mouseHandler.pressed) {
                if (activeP == null) {
                    for (piece pieces : simPieces) {
                        if (pieces.color == currentColor && pieces.col == mouseHandler.x / Board.Square_size &&
                                pieces.row == mouseHandler.y / Board.Square_size) {
                            activeP = pieces;
                        }
                    }
                } else {
                    simulate();
                }
            }
            //mouse released
            if (!mouseHandler.pressed) {
                ///

                ///
                if (activeP != null) {
                    if (validSquare) {
                        //move confirm

                        //after removed pieces
                        copyPieces(simPieces,PiecesA);
                        activeP.updatePosition();

                        // update castling position
                        if(CastlingPiece !=null){
                            CastlingPiece.updatePosition();
                        }
                        //CheckMate Check
                        if(KingInCheck() && checkmate()){
                            gameOver = true;
                        }
                        else {
                            // promotion check
                            if( Promote()){
                                promotion= true;
                            }
                            else {
                                switchPlayer();
                            }
                        }
//
                    } else {
                        copyPieces(PiecesA,simPieces);
                        activeP.resetPosition();
                        activeP = null;
                    }
                }
            }
        }
    }
    private void simulate () {
        canMove = false;
        validSquare = false;
        copyPieces(PiecesA,simPieces);

        // reset castling position
        if(CastlingPiece != null){
            CastlingPiece.col = CastlingPiece.preCol;
            CastlingPiece.x = CastlingPiece.getX(CastlingPiece.col);
            CastlingPiece=null;
        }

        activeP.x = mouseHandler.x - Board.Half_Square_size;
        activeP.y = mouseHandler.y - Board.Half_Square_size;
        activeP.col = activeP.getCol(activeP.x);
        activeP.row = activeP.getRow(activeP.y);

        if (activeP.possibleMove(activeP.col, activeP.row)) {
            canMove = true;

            //removed pieces
            if(activeP.overwritePieces != null){
                simPieces.remove(activeP.overwritePieces.getIndex());

            }

            CheckCastling();
            if(!KingIllegalMove(activeP) && !OpponentCanCheckKING()){
                validSquare = true;
            }
        }
    }
    private boolean KingInCheck(){
        piece king = getCheckKingP(true);
        if(activeP.possibleMove(king.col, king.row)){
            CheckKingP = activeP;
            return true;
        }
        else {
            CheckKingP = null;
        }
        return false;
    }
    private  piece getCheckKingP(boolean opponent){
        piece king = null;

        for(piece piece: simPieces) {
            if (opponent) {
                if (piece.type == Type.KING && piece.color != currentColor) {
                    king = piece;
                }
            }
            else {
                if (piece.type == Type.KING && piece.color == currentColor) {
                    king = piece;
                }
            }
        }
        return king;
    }

    private void CheckCastling(){
        if(CastlingPiece != null){
            if(CastlingPiece.col ==0){
                CastlingPiece.col +=3;
            }
            else if(CastlingPiece.col == 7){
                CastlingPiece.col -= 2;
            }
            CastlingPiece.x = CastlingPiece.getX(CastlingPiece.col);
        }
    }
    //promotion
    private boolean Promote(){
        if(activeP.type == Type.PAWN){
            if(currentColor == WHITE && activeP.row == 0 || currentColor == BLACK && activeP.row ==7){
                promoteAr.clear();
                promoteAr.add(new Rook(currentColor,9,2));
                promoteAr.add(new Knight(currentColor,9,3));
                promoteAr.add(new Bishop(currentColor,9,4));
                promoteAr.add(new Queen(currentColor,9,5));
                return true;
            }
        }
        return false;
    }
    private void promoting(){
        if(mouseHandler.pressed){
            for(piece p : promoteAr){
                if(p.col == mouseHandler.x/Board.Square_size && p.row == mouseHandler.y/Board.Square_size){
                    switch (p.type){
                        case ROOK -> {
                            simPieces.add(new Rook(currentColor,activeP.col,activeP.row));
                            break;
                        }
                        case KNIGHT ->{
                            simPieces.add(new Knight(currentColor,activeP.col,activeP.row));
                            break;
                        }
                        case BISHOP ->{
                            simPieces.add(new Bishop(currentColor,activeP.col,activeP.row));
                            break;
                        }
                        case QUEEN ->{
                            simPieces.add(new Queen(currentColor,activeP.col,activeP.row));
                            break;
                        }
                        default -> {
                            break;
                        }
                    }
                    simPieces.remove(activeP.getIndex());
                    copyPieces(simPieces,PiecesA);
                    activeP =null;
                    promotion =false;
                    switchPlayer();
                }
            }
        }
    }
    // king illegal move
    private boolean KingIllegalMove(piece King){
        if(King.type == Type.KING){
            for(piece p :simPieces){
                if(p!= King && p.color != King.color && p.possibleMove(King.col, King.row)){
                    return true;
                }
            }
        }
        return false;
    }
    // king illegal move
    private boolean OpponentCanCheckKING(){
        piece king = getCheckKingP(false);

        for(piece piece :simPieces){
            if(piece.color != king.color && piece.possibleMove(king.col, king.row)){
                return true;
            }
        }
        return false;
    }
    private void draw () {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        board.draw(gc);

        for (piece p : simPieces) {
            p.draw(gc);
        }
        // status message

        if (activeP != null) {
            if (canMove) {
                if(KingIllegalMove(activeP) || OpponentCanCheckKING() ){
                    gc.setFill(Color.rgb(201, 19, 19, 0.7)); // Set color with opacity
                    gc.fillRect(activeP.col * Board.Square_size, activeP.row * Board.Square_size, Board.Square_size, Board.Square_size);
                    gc.setGlobalAlpha(1.0);
                }

                else {
                    gc.setFill(Color.rgb(255, 255, 255, 0.7));// Set color with opacity
                    gc.fillRect(activeP.col * Board.Square_size, activeP.row * Board.Square_size, Board.Square_size, Board.Square_size);
                    gc.setGlobalAlpha(1.0);
                }

            }
            activeP.draw(gc);
        }
        //status
        gc.setFontSmoothingType(FontSmoothingType.LCD);
        gc.setFont(Font.font("Book Antiqua", 30));
        gc.setFill(Color.BLACK);

        if(promotion){
            gc.setFill(Color.rgb(0, 0, 0, 0.7));
            gc.fillText("PROMOTE T0: ", 840,150);
            for(piece p : promoteAr){
                gc.drawImage(p.image,p.getX(p.col),p.getY(p.row),Board.Square_size,Board.Square_size);
            }

        }
        else {
            if(currentColor == WHITE){
                gc.fillText("White Turn", 840, 550);
                if(CheckKingP != null && CheckKingP.color==BLACK){
                    gc.setFill(Color.RED);
                    gc.fillText("The King is Check",840, 650);
                }
            }
            else {
                gc.fillText("Black Turn", 840, 150);
                if(CheckKingP != null && CheckKingP.color==WHITE){
                    gc.setFill(Color.RED);
                    gc.fillText("The King is Check",840, 650);
                }
            }
        }
        if(gameOver){
            String s ="";
            if(currentColor == WHITE){
                s="White Wins";
            }
            else {
                s ="Black Wins";
            }
            gc.setFont(Font.font("Arial", 60));
            gc.setFill(Color.RED);
            gc.fillText(s,200,400);
        }
    }
    public Canvas getCanvas () {
        return canvas;
    }
    //
    //Checking check mate
    //check king can move
    private boolean checkmate(){
        piece king = getCheckKingP(true);

        if(KingCanMove(king)){
            return false;
        }
        else {
            int colDiff= Math.abs(CheckKingP.col - king.col);
            int rowDiff = Math.abs(CheckKingP.row - king.row);

            if(colDiff ==0){
                // attack vertically check
                if(CheckKingP.row < king.row){
                    for(int row = CheckKingP.row; row < king.row; row++){
                        for(piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.possibleMove(CheckKingP.col, row)){
                                return false;
                            }
                        }
                    }
                }
                if(CheckKingP.row > king.row){
                    for(int row = CheckKingP.row; row > king.row; row--){
                        for(piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.possibleMove(CheckKingP.col, row)){
                                return false;
                            }
                        }
                    }
                }
            }
            else if (rowDiff == 0){
                // attack horizontally check
                if(CheckKingP.col < king.col){
                    for(int col = CheckKingP.col; col < king.col; col++){
                        for(piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.possibleMove(col,CheckKingP.row)){
                                return false;
                            }
                        }
                    }
                }
                if(CheckKingP.col > king.col){
                    for(int col = CheckKingP.col; col > king.col; col--){
                        for(piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.possibleMove(col,CheckKingP.row)){
                                return false;
                            }
                        }
                    }
                }
            }
            else if (colDiff == rowDiff) {
                // attacking diagonal
                if(CheckKingP.row < king.row){
                    //upper left
                    if(CheckKingP.col <king.col){
                        for(int col = CheckKingP.col , row = CheckKingP.row; col < king.col ; col++, row++){
                            for(piece piece : simPieces){
                                if(piece!=king && piece.color!= currentColor && piece.possibleMove(col,row)){
                                    return false;

                                }
                            }
                        }
                    }
                    // upper right
                    if(CheckKingP.col> king.col){
                        for(int col = CheckKingP.col , row = CheckKingP.row; col > king.col ; col--, row++){
                            for(piece piece : simPieces){
                                if(piece!=king && piece.color!= currentColor && piece.possibleMove(col,row)){
                                    return false;

                                }
                            }
                        }
                    }

                }
                if(CheckKingP.row >king.row){
                    //lower left
                    if(CheckKingP.col <king.col){
                        for(int col = CheckKingP.col , row = CheckKingP.row; col < king.col ; col++, row--){
                            for(piece piece : simPieces){
                                if(piece!=king && piece.color!= currentColor && piece.possibleMove(col,row)){
                                    return false;

                                }
                            }
                        }
                    }
                    // lower right
                    if(CheckKingP.col> king.col){
                        for(int col = CheckKingP.col , row = CheckKingP.row; col > king.col ; col--, row--){
                            for(piece piece : simPieces){
                                if(piece!=king && piece.color!= currentColor && piece.possibleMove(col,row)){
                                    return false;

                                }
                            }
                        }
                    }
                }
            }
            else {

            }
        }
        return true;
    }
    private boolean KingCanMove(piece king){
        if(iaValidMove(king,-1,-1)){return true;}
        if(iaValidMove(king,-1,0)){return true;}
        if(iaValidMove(king,0,1)){return true;}
        if(iaValidMove(king,0,-1)){return true;}
        if(iaValidMove(king,-1,1)){return true;}
        if(iaValidMove(king,1,-1)){return true;}
        if(iaValidMove(king,1,0)){return true;}
        if(iaValidMove(king,1,1)){return true;}
        return false;
    }
    private boolean iaValidMove (piece king, int colPlus, int rowPlus) {
        boolean isValidMove = false;

        // Update the king's position for a second
        king.col += colPlus;
        king.row += rowPlus;

        if (king.possibleMove(king.col, king.row)) {

            if(king.overwritePieces!= null) {
                simPieces.remove(king.overwritePieces.getIndex());
            }
            if(KingIllegalMove(king) == false){
                isValidMove= true;
            }
        }
        king.resetPosition();
        copyPieces(PiecesA,simPieces);
        return isValidMove;
    }
}
