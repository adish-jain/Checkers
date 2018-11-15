package qirkat;

import static qirkat.PieceColor.*;

/** A Player that computes its own moves.
 *  @author Adish Jain
 */
class AI extends Player {

    /** Maximum minimax search depth before going to static evaluation. */
    private static final int MAX_DEPTH = 8;
    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI for GAME that will play MYCOLOR. */
    AI(Game game, PieceColor myColor) {
        super(game, myColor);
    }

    @Override
    Move myMove() {
        Main.startTiming();

        Move move = findMove();
        Main.endTiming();

        System.out.println(myColor().toString()
                + " moves " + move.toString() + ".");
        return move;
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        if (myColor() == WHITE) {
            findMove(b, MAX_DEPTH, true, 1, -INFTY, INFTY);
        } else {
            findMove(b, MAX_DEPTH, true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound.
     *  I referenced the Wikipedia alpha beta pruning article for this alg
     */
    private int findMove(Board board, int depth,
                         boolean saveMove, int sense,
                         int alpha, int beta) {
        System.out.println(depth);
        Move best = null;
        int score = 0;
        if (depth == 0) {
            return staticScore(board);
        } else {
            if (sense == 1) {
                int v = -INFTY;
                for (Move mov : board.getMoves()) {
                    Board newBoard = new Board(board);
                    newBoard._whoseMove = BLACK;
                    newBoard.makeQuickMove(mov);
                    v = Math.max(v, findMove(newBoard, depth - 1,
                            saveMove, -1, alpha, beta));
                    int vTemp = findMove(newBoard, depth - 1,
                            saveMove, -1, alpha, beta);
                    if (vTemp > v) {
                        v = vTemp;
                        best = mov;
                    }
                    alpha = Math.max(beta, v);
                    if (beta <= alpha) {
                        best = mov;
                        break;
                    }
                }
                score = v;
            } else {
                int v = INFTY;
                for (Move mov : board.getMoves()) {
                    Board newBoard = new Board(board);
                    newBoard._whoseMove = WHITE;
                    newBoard.makeQuickMove(mov);
                    v = Math.min(v, findMove(newBoard, depth - 1,
                            saveMove, 1, alpha, beta));
                    int vTemp = findMove(newBoard, depth - 1,
                            saveMove, -1, alpha, beta);
                    if (vTemp > v) {
                        v = vTemp;
                        best = mov;
                    }
                    beta = Math.min(alpha, v);
                    if (beta <= alpha) {
                        best = mov;
                        break;
                    }
                }
                score = v;
            }
        }
        if (saveMove) {
            _lastFoundMove = best;
        }
        return score;
    }

    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        int numWhites = 0;
        int numBlacks = 0;
        final int twentyFive = 25;
        for (int i = 0; i < twentyFive; i++) {
            if (board.getBoardRep().get(i).getTile() == WHITE) {
                numWhites += 1;
            } else if (board.getBoardRep().get(i).getTile() == BLACK) {
                numBlacks += 1;
            }
        }
        return numWhites - numBlacks;
    }

}
