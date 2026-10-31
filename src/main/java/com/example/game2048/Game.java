package com.example.game2048;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Serializable {
    private static final int N = 4;
    private final Random rnd = new Random();
    private int[][] board;
    private int score;
    private int best;
    private boolean over;
    private boolean won;

    public Game() { reset(); }

    public void reset() {
        board = new int[N][N];
        score = 0;
        over = false;
        won = false;
        spawn();
        spawn();
    }

    /** Returns true if the board changed. */
    public boolean move(String dir) {
        if (over) return false;
        boolean changed = false;
        for (int k = 0; k < N; k++) {
            int[] line = new int[N];
            for (int j = 0; j < N; j++) line[j] = get(dir, k, j);
            int[] out = slide(line);
            for (int j = 0; j < N; j++) {
                if (out[j] != line[j]) changed = true;
                set(dir, k, j, out[j]);
            }
        }
        if (changed) {
            spawn();
            if (score > best) best = score;
            if (!canMove()) over = true;
        }
        return changed;
    }

    private int[] slide(int[] line) {
        int[] out = new int[N];
        int idx = 0;
        int last = 0;
        for (int v : line) {
            if (v == 0) continue;
            if (last == v) {
                out[idx - 1] = v * 2;
                score += v * 2;
                if (v * 2 == 2048) won = true;
                last = 0;
            } else {
                out[idx++] = v;
                last = v;
            }
        }
        return out;
    }

    private int get(String d, int k, int j) {
        return switch (d) {
            case "left" -> board[k][j];
            case "right" -> board[k][N - 1 - j];
            case "up" -> board[j][k];
            case "down" -> board[N - 1 - j][k];
            default -> throw new IllegalArgumentException("Bad direction: " + d);
        };
    }

    private void set(String d, int k, int j, int v) {
        switch (d) {
            case "left" -> board[k][j] = v;
            case "right" -> board[k][N - 1 - j] = v;
            case "up" -> board[j][k] = v;
            case "down" -> board[N - 1 - j][k] = v;
            default -> throw new IllegalArgumentException("Bad direction: " + d);
        }
    }

    private void spawn() {
        List<int[]> empty = new ArrayList<>();
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++)
                if (board[r][c] == 0) empty.add(new int[]{r, c});
        if (empty.isEmpty()) return;
        int[] p = empty.get(rnd.nextInt(empty.size()));
        board[p[0]][p[1]] = rnd.nextInt(10) == 0 ? 4 : 2;
    }

    private boolean canMove() {
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++) {
                if (board[r][c] == 0) return true;
                if (c + 1 < N && board[r][c] == board[r][c + 1]) return true;
                if (r + 1 < N && board[r][c] == board[r + 1][c]) return true;
            }
        return false;
    }

    public int[][] getBoard() { return board; }
    public int getScore() { return score; }
    public int getBest() { return best; }
    public boolean isOver() { return over; }
    public boolean isWon() { return won; }
}
