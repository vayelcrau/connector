package com.vayelcrau.fourthought;

import com.vayelcrau.fourthought.game.Board;
import com.vayelcrau.fourthought.game.Game;
import lib.Values;

import java.util.Random;

public class Fourthought{
    private Game game;
    private Random rand;
    private final Values color = Values.RED;
    private int depth;

    private static Fourthought instance;

    public static Fourthought getInstance() {
        if (instance == null)
            instance = new Fourthought();
        return instance;
    }
    private Fourthought() {
        game = Game.getInstance();
        rand = new Random();
    }

    public void move() {
        depth = 0;
        Board currentBoard = game.getBoard().getCopyOfBoard();
        game.place(color, calculateBestMove(currentBoard));
    }

    public int calculateBestMove(Board currentBoard) {
        int bestMove = testAllMoves(currentBoard);
        if (bestMove != -1) return bestMove;

        for (int i = 0; i < 7; i++) {
            if (calculateMove(currentBoard.getCopyOfBoard(), 1)) {
                return i;
            };
        }

        return rand.nextInt(7);

    }

    public boolean calculateMove(Board board, int depth) {
        int bestMove;
        int currentDepth = depth;
        if (depth > 2) return false;

        for (int i = 0; i < 7; i++) {
            Board copy = board.getCopyOfBoard();
            copy.place(Values.BLUE, i);
            bestMove = testAllMoves(board);
            if (bestMove != -1) return true;
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Board copy = board.getCopyOfBoard();
                copy.place(Values.BLUE, i);
                copy.place(Values.RED, j);
                calculateMove(copy, depth+1);
            }
        }

        return false;
    }



    public int testAllMoves(Board board) {
        for (int i = 0; i < 7; i++) {
            if (board.testMove(color, i)) {
                System.out.println("Found winning move at "+ i+ "!");
                return i;
            }
        }
        return -1;
    }
}
