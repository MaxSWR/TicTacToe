package com.softpunkgame.max.tictactoe.field;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;

public class Field {
    private final int FIELD_SIZE;
    private final int CELL_SIZE;
    private final byte CELLS = 5;
    private final byte[][] FIELD_CELLS = {
            {-1, -1, -1},
            {-1, -1, -1},
            {-1, -1, -1},
    };

    private final Paint fieldPaint, crossPaint, zeroPaint, winnerPaint;
    private enum players {CROSS, ZERO};
    private boolean cross, win;

    public Field(Display display) {
        int biggest = display.getWidth() > display.getHeight() ? display.getHeight() : display.getWidth();
        FIELD_SIZE = biggest;
        CELL_SIZE = this.FIELD_SIZE / CELLS;
        cross = true;
        win = false;

        fieldPaint = new Paint();
        fieldPaint.setColor(Color.GRAY);
        fieldPaint.setStrokeWidth(4);

        crossPaint = new Paint();
        crossPaint.setColor(Color.BLUE);
        crossPaint.setStrokeWidth(8);

        zeroPaint = new Paint();
        zeroPaint.setColor(Color.RED);
        zeroPaint.setStrokeWidth(8);
        zeroPaint.setStyle(Paint.Style.STROKE);

        winnerPaint = new Paint();
        winnerPaint.setColor(Color.GREEN);
        winnerPaint.setStrokeWidth(12);
    }

    public void click(int x, int y) {
        if (win) {
            newGame();
            return;
        }

        if (x < CELL_SIZE || y < CELL_SIZE || x > (CELLS - 1) * CELL_SIZE || y > (CELLS - 1) * CELL_SIZE) {
            return;
        }

        int c_x = (int)(((float)x / (float)FIELD_SIZE) * CELLS) - 1;
        int c_y = (int)(((float)y / (float)FIELD_SIZE) * CELLS) - 1;

        if (FIELD_CELLS[c_x][c_y] != -1)
            return;

        FIELD_CELLS[c_x][c_y] = cross ? (byte)1 : (byte)0;
        cross = !cross;
    }

    public void draw(Canvas c) {
        drawField(c);
        drawPlayer(c);
        drawGame(c);
        testWinner(c);
    }

    private void drawField(Canvas c) {
        c.drawLine(2*CELL_SIZE, CELL_SIZE, 2*CELL_SIZE, 4*CELL_SIZE, fieldPaint);
        c.drawLine(3*CELL_SIZE, CELL_SIZE, 3*CELL_SIZE, 4*CELL_SIZE, fieldPaint);
        c.drawLine(CELL_SIZE, 2*CELL_SIZE, 4*CELL_SIZE, 2*CELL_SIZE, fieldPaint);
        c.drawLine(CELL_SIZE, 3*CELL_SIZE, 4*CELL_SIZE, 3*CELL_SIZE, fieldPaint);
    }

    private void drawPlayer(Canvas c) {
        if (cross)
            drawCross(c, 2*CELL_SIZE, 0);
        else
            drawZero(c, 2*CELL_SIZE, 0);
    }

    private void drawCross(Canvas c, int x, int y) {
        c.drawLine(x, y, x + CELL_SIZE, y + CELL_SIZE, crossPaint);
        c.drawLine(x + CELL_SIZE, y, x, y + CELL_SIZE, crossPaint);
    }

    private void drawZero(Canvas c, int x, int y) {
        c.drawCircle(x + CELL_SIZE/2, y + CELL_SIZE/2, CELL_SIZE/2, zeroPaint);
    }

    private void drawGame(Canvas c) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (FIELD_CELLS[i][j]) {
                    case 1:
                        drawCross(c, (i + 1)*CELL_SIZE, (j + 1)*CELL_SIZE);
                        break;

                    case 0:
                        drawZero(c, (i + 1)*CELL_SIZE, (j + 1)*CELL_SIZE);
                        break;
                }
            }
        }
    }

    private void testWinner(Canvas c) {
        for (int i = 0; i < 3; i++) {
            if (FIELD_CELLS[0][i] == FIELD_CELLS[1][i] && FIELD_CELLS[1][i] == FIELD_CELLS[2][i] && FIELD_CELLS[0][i] != -1){
                win = true;
                drawHorizontalWinnerLine(c, i + 1);
                return ;
            }

            if (FIELD_CELLS[i][0] == FIELD_CELLS[i][1] && FIELD_CELLS[i][1] == FIELD_CELLS[i][2] && FIELD_CELLS[i][0] != -1){
                win = true;
                drawVerticalWinnerLine(c, i + 1);
                return ;
            }
        }

        if (FIELD_CELLS[0][0] == FIELD_CELLS[1][1] && FIELD_CELLS[1][1] == FIELD_CELLS[2][2] && FIELD_CELLS[0][0] != -1){
            win = true;
            drawDiagonalWinnerLine(c, 1);
            return ;
        }

        if (FIELD_CELLS[2][0] == FIELD_CELLS[1][1] && FIELD_CELLS[1][1] == FIELD_CELLS[0][2] && FIELD_CELLS[2][0] != -1){
            win = true;
            drawDiagonalWinnerLine(c, 2);
            return ;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (FIELD_CELLS[i][j] == -1) {
                    return;
                }
            }
        }

        win = true;
    }

    private void drawHorizontalWinnerLine(Canvas c, int y_cell) {
        c.drawLine(4*CELL_SIZE/3, CELL_SIZE/2 + CELL_SIZE*y_cell, (CELLS - 1)*CELL_SIZE - CELL_SIZE/3, CELL_SIZE/2 + CELL_SIZE*y_cell, winnerPaint);
    }

    private void drawVerticalWinnerLine(Canvas c, int x_cell) {
        c.drawLine(CELL_SIZE/2 + CELL_SIZE*x_cell, 4*CELL_SIZE/3, CELL_SIZE/2 + CELL_SIZE*x_cell, (CELLS - 1)*CELL_SIZE - CELL_SIZE/3, winnerPaint);
    }

    private void drawDiagonalWinnerLine(Canvas c, int x_cell) {
        switch (x_cell) {
            case 1:
                c.drawLine(4*CELL_SIZE/3, 4*CELL_SIZE/3, (CELLS - 1)*CELL_SIZE - CELL_SIZE/3, (CELLS - 1)*CELL_SIZE - CELL_SIZE/3, winnerPaint);
                break;

            case 2:
                c.drawLine((CELLS - 1)*CELL_SIZE - CELL_SIZE/3, 4*CELL_SIZE/3, 4*CELL_SIZE/3, (CELLS - 1)*CELL_SIZE - CELL_SIZE/3, winnerPaint);
                break;

        }
    }

    private void newGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                FIELD_CELLS[i][j] = -1;
            }
        }

        cross = true;
        win = false;
    }
}
