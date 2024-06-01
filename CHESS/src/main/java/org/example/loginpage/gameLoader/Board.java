package org.example.loginpage.gameLoader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {
    final int Max_col = 8;
    final int Max_row = 8;
    public static final int Square_size = 100;
    public static final int Half_Square_size= Square_size/2;
    public void draw(GraphicsContext gc) {
        int c = 0;

        for (int row = 0; row < Max_row; row++) {
            for (int col = 0; col < Max_col; col++) {
                if (c == 0) {
                    gc.setFill(Color.rgb(210, 165, 125));
                   // gc.setFill(Color.rgb(0, 0, 0));
                    c = 1;
                } else {
                    gc.setFill(Color.rgb(175, 115, 70));
                    //gc.setFill(Color.rgb(255, 255, 255));
                    c = 0;
                }
                gc.fillRect(col * Square_size, row * Square_size, Square_size, Square_size);
            }

            c = (c == 0) ? 1 : 0;
        }
    }
}
