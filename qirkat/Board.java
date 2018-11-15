package qirkat;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Observable;
import java.util.Observer;
import java.util.HashMap;
import java.util.Stack;
import java.util.Set;
import java.util.Collection;
import static qirkat.PieceColor.*;
import static qirkat.Move.*;

/** A Qirkat board.   The squares are labeled by column (a char value between
 *  'a' and 'e') and row (a char value between '1' and '5'.
 *
 *  For some purposes, it is useful to refer to squares using a single
 *  integer, which we call its "linearized index".  This is simply the
 *  number of the square in row-major order (with row 0 being the bottom row)
 *  counting from 0).
 *
 *  Moves on this board are denoted by Moves.
 *  @author Adish Jain.
 */
class Board extends Observable {

    /** A new, cleared board at the start of the game. */
    Board() {
        clear();
        boardStates.push(new Board(this));
    }

    /** A copy of B. */
    Board(Board b) {
        internalCopy(b);
    }

    /** Return a constant view of me (allows any access method, but no
     *  method that modifies it). */
    Board constantView() {
        return this.new ConstantBoard();
    }

    /** Clear me to my starting state, with pieces in their initial
     *  positions. */
    void clear() {
        _whoseMove = WHITE;
        _gameOver = false;
        blocks = new Integer[]
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        board.put(0, new Tile(WHITE, this, true, false, true,
                false, false, false, true, false,
                true, false, true, false,
                false, false, true, false));
        board.put(1, new Tile(WHITE, this, true, true,
                true, false, false, false,
                false, false, true, false, true,
                false, false, false, false, false));
        board.put(2, new Tile(WHITE, this, true, true,
                true, false, true, false, true, false,
                true, true, true, false,
                true, false, true, false));
        board.put(3, new Tile(WHITE, this, true, true,
                true, false, false, false, false, false,
                 false, true, true, false, false,
                false, false, false));
        board.put(4, new Tile(WHITE, this, false, true,
                true, false, true, false, false, false,
                false, true, true, false, true,
                false, false, false));
        board.put(5, new Tile(WHITE, this, true, false,
                true, true, false, false, false,
                false, true, false,  true,  false,
                false, false, false, false));
        board.put(6, new Tile(WHITE, this, true, true,
                true, true, true, true, true, true,
                true, false, true, false,
                false, false, true, false));
        board.put(7, new Tile(WHITE, this, true, true,
                true, true, false, false, false, false,
                true, true, true, false, false,
                false, false, false));
        board.put(8, new Tile(WHITE, this, true, true,
                true, true, true, true, true, true,
                false, true, true, false,
                true, false, false, false));
        board.put(9, new Tile(WHITE, this, false, true,
                true, true, false, false, false, false,
                false, true, true, false,
                false, false, false, false));
        board.put(10, new Tile(BLACK, this, true, false,
                true, true, false, false,
                true,  true, true,  false,  true,
                true, false, false, true, true));
        board.put(11, new Tile(BLACK, this, true, true,
                true, true, false, false, false, false,
                true, false, true, true,
                false, false, false, false));
        board.put(12, new Tile(EMPTY, this, true, true,
                true, true, true, true, true, true,
                true, true, true, true,
                true, true, true, true));
        firstPut();
    }

    /**
     * finish Clear().
     */
    void firstPut() {
        final int seventeen = 17;
        final int eighteen = 18;
        final int nineteen = 19;
        final int twenty = 20;
        final int twentyOne = 21;
        final int twentyTwo = 22;
        final int twentyThree = 23;
        final int twentyFour = 24;
        board.put(13, new Tile(WHITE, this, true, true,
                true, true, false, false, false, false,
                false, true, true, true,
                false, false, false, false));
        board.put(14, new Tile(WHITE, this, false, true,
                true, true, true, true, false, false,
                false, true, true, true,
                true, true, false, false));
        board.put(15, new Tile(BLACK, this, true, false, true,
                true, false, false, false, false,
                true, false, false, true,
                false, false, false, false));
        board.put(16, new Tile(BLACK, this, true, true,
                true, true, true, true, true, true,
                true, false, false, true,
                false, false, false, true));
        board.put(seventeen,  new Tile(BLACK, this,  true,  true,
                true, true, false, false, false, false,
                true, true, false, true,
                false, false, false, false));
        board.put(eighteen,  new Tile(BLACK, this,  true,  true,
                true, true, true, true, true, true,
                false, true, false, true,
                false, true, false, false));
        board.put(nineteen,  new Tile(BLACK, this,  false,  true,
                true, true, false, false, false, false,
                false, true, false, true,
                false, false, false, false));
        board.put(twenty, new Tile(BLACK, this, true, false,
                false, true, false, false, false, true,
                true, false, false, true,
                false, false, false, true));
        board.put(twentyOne, new Tile(BLACK, this, true, true,
                false, true, false, false, false, false,
                true, false, false, true,
                false, false, false, false));
        board.put(twentyTwo, new Tile(BLACK, this, true, true,
                false, true, false, true, false, true,
                true, true, false, true,
                false, true, false, true));
        board.put(twentyThree, new Tile(BLACK, this, true, true,
                false, true, false, false, false, false,
                false, true, false, true,
                false, false, false, false));
        board.put(twentyFour, new Tile(BLACK, this, false, true,
                false, true, false, true, false, false,
                false, true, false, true,
                false, true, false, false));
        setChanged();
        notifyObservers();
    }

    /** Copy B into me. */
    void copy(Board b) {
        internalCopy(b);
    }

    /** Copy B into me. */
    private void internalCopy(Board b) {
        if (b._gameOver) {
            this._gameOver = true;
        } else {
            this._gameOver = false;
        }
        if (b._whoseMove == WHITE) {
            this._whoseMove = WHITE;
        } else {
            this._whoseMove = BLACK;
        }
        final int twentyFive = 25;
        System.arraycopy(b.blocks, 0, this.blocks, 0, twentyFive);
        Set<Integer> keys = b.board.keySet();
        Object[] keysObjArr = keys.toArray();
        Integer[] keysIntArr = new Integer[keysObjArr.length];
        Integer[] newKeys = new Integer[keysObjArr.length];

        Collection<Tile> values = b.board.values();
        Object[] valuesObjArr = values.toArray();
        Tile[] valuesArr = new Tile[valuesObjArr.length];
        Tile[] newValues = new Tile[values.size()];

        for (int k = 0; k < keysObjArr.length; k++) {
            newKeys[k] = new Integer((Integer) keysObjArr[k]);
        }
        for (int v = 0; v < values.size(); v++) {
            newValues[v] = new Tile((Tile) valuesObjArr[v]);
        }

        for (int i = 0; i < b.board.size(); i++) {
            this.board.put(newKeys[i], newValues[i]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Board) {
            Board b = (Board) o;
            return (b.toString().equals(toString())
                    && b.whoseMove() == _whoseMove
                    && b._gameOver == _gameOver);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    /** Set my contents as defined by STR.  STR consists of 25 characters,
     *  each of which is b, w, or -, optionally interspersed with whitespace.
     *  These give the contents of the Board in row-major order, starting
     *  with the bottom row (row 1) and left column (column a). All squares
     *  are initialized to allow horizontal movement in either direction.
     *  NEXTMOVE indicates whose move it is.
     */
    void setPieces(String str, PieceColor nextMove) {
        if (nextMove == EMPTY || nextMove == null) {
            throw new IllegalArgumentException("bad player color");
        }
        str = str.replaceAll("\\s", "");
        if (!str.matches("[bw-]{25}")) {
            throw new IllegalArgumentException("bad board description");
        }

        for (int k = 0; k < str.length(); k += 1) {
            switch (str.charAt(k)) {
            case '-':
                set(k, EMPTY);
                break;
            case 'b': case 'B':
                set(k, BLACK);
                break;
            case 'w': case 'W':
                set(k, WHITE);
                break;
            default:
                break;
            }
        }

        _whoseMove = nextMove;

        setChanged();
        notifyObservers();
    }

    /** Return true iff the game is over: i.e., if the current player has
     *  no moves. */
    boolean gameOver() {
        return _gameOver;
    }

    /** Return the current contents of square C R, where 'a' <= C <= 'e',
     *  and '1' <= R <= '5'.  */
    PieceColor get(char c, char r) {
        assert validSquare(c, r);
        return get(index(c, r));
    }

    /** Return the current contents of the square at linearized index K. */
    PieceColor get(int k) {
        assert validSquare(k);
        return board.get(k).getTile();
    }

    /** Set get(C, R) to V, where 'a' <= C <= 'e', and
     *  '1' <= R <= '5'. */
    private void set(char c, char r, PieceColor v) {
        assert validSquare(c, r);
        set(index(c, r), v);
    }

    /** Set get(K) to V, where K is the linearized index of a square. */
    private void set(int k, PieceColor v) {
        assert validSquare(k);
        board.get(k).setTile(v);
    }

    /** Return true iff MOV is legal on the current board. */
    boolean legalMove(Move mov) {
        ArrayList<Move> possibleMoves = getMoves();
        for (Move thisMove : possibleMoves) {
            if (mov.toString().equals(thisMove.toString())) {
                return true;
            }
        }
        return false;
    }

    /** Return a list of all legal moves from the current position. */
    ArrayList<Move> getMoves() {
        ArrayList<Move> result = new ArrayList<>();
        getMoves(result);
        return result;
    }

    /** Add all legal moves from the current position to MOVES. */
    void getMoves(ArrayList<Move> moves) {
        if (gameOver()) {
            return;
        }
        if (jumpPossible()) {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getJumps(moves, k);
            }
        } else {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getMoves(moves, k);
            }
        }
    }

    /** Add all legal non-capturing moves from the position
     *  with linearized index K to MOVES. */
    private void getMoves(ArrayList<Move> moves, int k) {
        Tile tileObj = board.get(k);
        if (tileObj.getTile() == EMPTY
                || tileObj.getTile() == whoseMove().opposite()) {
            return;
        }
        final int twenty = 20;
        if (k >= twenty && tileObj.getTile() == WHITE) {
            return;
        }
        if (k < 5 && tileObj.getTile() == BLACK) {
            return;
        }
        if (tileObj.hasBottomSpace() && tileObj.getTile() != WHITE) {
            if (board.get(k - 5).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k - 5);
                char row1 = Move.row(k - 5);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
        if (tileObj.hasTopSpace() && tileObj.getTile() != BLACK) {
            if (board.get(k + 5).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k + 5);
                char row1 = Move.row(k + 5);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
        if (tileObj.hasRightSpace() && blocks[k] != 1) {
            if (board.get(k + 1).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k + 1);
                char row1 = Move.row(k + 1);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
        if (tileObj.hasLeftSpace() && blocks[k] != -1) {
            if (board.get(k - 1).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k - 1);
                char row1 = Move.row(k - 1);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
        finishGettingMoves(moves, k);
    }

    /** Add all legal non-capturing moves from the position
     *  with linearized index K to MOVES. */
    private void finishGettingMoves(ArrayList<Move> moves, int k) {
        Tile tileObj = board.get(k);
        if (tileObj.hasdiagonalLUSpace() && tileObj.getTile() != BLACK) {
            if (board.get(k + 4).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k + 4);
                char row1 = Move.row(k + 4);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
        if (tileObj.hasdiagonalRUSpace() && tileObj.getTile() != BLACK) {
            if (board.get(k + 6).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k + 6);
                char row1 = Move.row(k + 6);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
        if (tileObj.hasdiagonalLDSpace() && tileObj.getTile() != WHITE) {
            if (board.get(k - 6).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k - 6);
                char row1 = Move.row(k - 6);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
        if (tileObj.hasdiagonalRDSpace() && tileObj.getTile() != WHITE) {
            if (board.get(k - 4).getTile() == EMPTY) {
                char col0 = Move.col(k);
                char row0 = Move.row(k);
                char col1 = Move.col(k - 4);
                char row1 = Move.row(k - 4);
                Move moveToMake = Move.move(col0, row0, col1, row1);
                moves.add(moveToMake);
            }
        }
    }

    /** Add all legal captures from the position with linearized index K
     *  to MOVES. */
    private void getJumps(ArrayList<Move> moves, int k) {
        Tile tileObj = board.get(k);
        PieceColor thisTile = tileObj.getTile();
        if (thisTile == EMPTY || tileObj.getTile() == whoseMove().opposite()) {
            return;
        }
        if (tileObj.hasBottomSpace() && tileObj.hasTwoBottomSpace()) {
            if (board.get(k - 5).getTile() == thisTile.opposite()) {
                Tile secBottomTile = board.get(k - 10);
                if (secBottomTile.getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k - 10);
                    char row1 = Move.row(k - 10);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
        if (tileObj.hasTopSpace() && tileObj.hasTwoTopSpace()) {
            if (board.get(k + 5).getTile() == thisTile.opposite()) {
                if (board.get(k + 10).getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k + 10);
                    char row1 = Move.row(k + 10);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
        continueGetJumpsOne(moves, k);
    }

    /**
     * continue getting jumps.
     * @param moves Move
     * @param k int
     */
    private void continueGetJumpsOne(ArrayList<Move> moves, int k) {
        Tile tileObj = board.get(k);
        PieceColor thisTile = tileObj.getTile();
        if (tileObj.hasRightSpace() && tileObj.hasTwoRightSpace()) {
            if (board.get(k + 1).getTile() == thisTile.opposite()) {
                Tile secRightTile = board.get(k + 2);
                if (secRightTile.getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k + 2);
                    char row1 = Move.row(k + 2);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
        if (tileObj.hasLeftSpace() && tileObj.hasTwoLeftSpace()) {
            if (board.get(k - 1).getTile() == thisTile.opposite()) {
                Tile secLeftTile = board.get(k - 2);
                if (secLeftTile.getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k - 2);
                    char row1 = Move.row(k - 2);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
        continueGetJumpsTwo(moves, k);
    }

    /** Continues adding all legal captures
     * from the position with linearized index K
     *  to MOVES. */
    private void continueGetJumpsTwo(ArrayList<Move> moves, int k) {
        Tile tileObj = board.get(k);
        PieceColor thisTile = tileObj.getTile();
        if (tileObj.hasdiagonalLUSpace() && tileObj.hasTwoDiagonalLUSpace()) {
            Tile leftUpper = board.get(k + 4);
            if (leftUpper.getTile() == thisTile.opposite()) {
                Tile secLUSpace = board.get(k + 8);
                if (secLUSpace.getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k + 8);
                    char row1 = Move.row(k + 8);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
        if (tileObj.hasdiagonalLDSpace() && tileObj.hasTwoDiagonalLDSpace()) {
            Tile leftDown = board.get(k - 6);
            if (leftDown.getTile() == thisTile.opposite()) {
                Tile secLDSpace = board.get(k - 12);
                if (secLDSpace.getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k - 12);
                    char row1 = Move.row(k - 12);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
        continueGetJumpsThree(moves, k);
    }

    /**
     * continue getting jumps.
     * @param moves Move
     * @param k int
     */
    private void continueGetJumpsThree(ArrayList<Move> moves, int k) {
        Tile tileObj = board.get(k);
        PieceColor thisTile = tileObj.getTile();
        if (tileObj.hasdiagonalRUSpace() && tileObj.hasTwoDiagonalRUSpace()) {
            Tile rightUpper = board.get(k + 6);
            if (rightUpper.getTile() == thisTile.opposite()) {
                Tile secRUSpace = board.get(k + 12);
                if (secRUSpace.getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k + 12);
                    char row1 = Move.row(k + 12);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
        if (tileObj.hasdiagonalRDSpace() && tileObj.hasTwoDiagonalRDSpace()) {
            Tile rightDown = board.get(k - 4);
            if (rightDown.getTile() == thisTile.opposite()) {
                Tile secRDSpace = board.get(k - 8);
                if (secRDSpace.getTile() == EMPTY) {
                    char col0 = Move.col(k);
                    char row0 = Move.row(k);
                    char col1 = Move.col(k - 8);
                    char row1 = Move.row(k - 8);
                    Move moveToMake = Move.move(col0, row0, col1, row1);
                    ArrayList<Move> tails = getJumpsHelper(moveToMake);
                    if (tails.size() == 0) {
                        moves.add(moveToMake);
                    } else {
                        for (Move t: tails) {
                            Move actualJump = move(moveToMake, t);
                            moves.add(actualJump);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the jumpTails of the possible jumps.
     * @param mov Move
     * @return tails
     */
    private ArrayList<Move> getJumpsHelper(Move mov) {
        ArrayList<Move> tails = new ArrayList<>();
        this.makeQuickMove(mov);
        getJumps(tails, mov.toIndex());
        undo(mov);
        return tails;
    }

    /** Return true iff MOV is a valid jump sequence on the current board.
     *  MOV must be a jump or null.  If ALLOWPARTIAL, allow jumps that
     *  could be continued and are valid as far as they go.  */
    boolean checkJump(Move mov, boolean allowPartial) {
        if (mov == null) {
            return true;
        }
        char col0 = mov.col0();
        char row0 = mov.row0();
        char col1 = mov.col1();
        char row1 = mov.row1();
        int indexFrom = Move.index(col0, row0);
        int indexTo = Move.index(col1, row1);

        ArrayList<Move> possibleJumps = new ArrayList<Move>();
        getJumps(possibleJumps, indexFrom);

        for (Move iter : possibleJumps) {
            if (iter.toIndex() == indexTo) {
                if (mov.jumpTail() != null) {
                    checkJump(mov.jumpTail(), allowPartial);
                }
                if (!allowPartial) {
                    ArrayList<Move> newJumps = new ArrayList<Move>();
                    getJumps(newJumps, indexTo);
                    if (newJumps.size() > 0) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /** Return true iff a jump is possible for a piece at position C R. */
    boolean jumpPossible(char c, char r) {
        return jumpPossible(index(c, r));
    }

    /** Return true iff a jump is possible for a piece at position with
     *  linearized index K. */
    boolean jumpPossible(int k) {
        Tile tileObj = board.get(k);
        PieceColor thisTile = tileObj.getTile();
        if (thisTile == EMPTY || tileObj.getTile() == whoseMove().opposite()) {
            return false;
        }
        if (tileObj.hasBottomSpace() && tileObj.hasTwoBottomSpace()) {
            Tile bottomTile = board.get(k - 5);
            if (bottomTile.getTile() == thisTile.opposite()) {
                Tile secBottomTile = board.get(k - 10);
                if (secBottomTile.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        if (tileObj.hasTopSpace() && tileObj.hasTwoTopSpace()) {
            Tile topTile = board.get(k + 5);
            if (topTile.getTile() == thisTile.opposite()) {
                Tile secTopTile = board.get(k + 10);
                if (secTopTile.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        if (tileObj.hasRightSpace() && tileObj.hasTwoRightSpace()) {
            Tile rightTile = board.get(k + 1);
            if (rightTile.getTile() == thisTile.opposite()) {
                Tile secRightTile = board.get(k + 2);
                if (secRightTile.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        if (tileObj.hasLeftSpace() && tileObj.hasTwoLeftSpace()) {
            Tile leftTile = board.get(k - 1);
            if (leftTile.getTile() == thisTile.opposite()) {
                Tile secLeftTile = board.get(k - 2);
                if (secLeftTile.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        return continueJumpPossible(k);
    }

    /** Continues return true iff a jump is
     * possible for a piece at position with
     *  linearized index K. */
    boolean continueJumpPossible(int k) {
        Tile tileObj = board.get(k);
        PieceColor thisTile = tileObj.getTile();
        if (tileObj.hasdiagonalLUSpace() && tileObj.hasTwoDiagonalLUSpace()) {
            Tile leftUpper = board.get(k + 4);
            if (leftUpper.getTile() == thisTile.opposite()) {
                Tile secLUSpace = board.get(k + 8);
                if (secLUSpace.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        if (tileObj.hasdiagonalLDSpace() && tileObj.hasTwoDiagonalLDSpace()) {
            Tile leftDown = board.get(k - 6);
            if (leftDown.getTile() == thisTile.opposite()) {
                Tile secLDSpace = board.get(k - 12);
                if (secLDSpace.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        if (tileObj.hasdiagonalRUSpace() && tileObj.hasTwoDiagonalRUSpace()) {
            Tile rightUpper = board.get(k + 6);
            if (rightUpper.getTile() == thisTile.opposite()) {
                Tile secRUSpace = board.get(k + 12);
                if (secRUSpace.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        if (tileObj.hasdiagonalRDSpace() && tileObj.hasTwoDiagonalRDSpace()) {
            Tile rightDown = board.get(k - 4);
            if (rightDown.getTile() == thisTile.opposite()) {
                Tile secRDSpace = board.get(k - 8);
                if (secRDSpace.getTile() == EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
    /** Return true iff a jump is possible from the current board. */
    boolean jumpPossible() {
        for (int k = 0; k <= MAX_INDEX; k += 1) {
            if (jumpPossible(k)) {
                return true;
            }
        }
        return false;
    }

    /** Return the color of the player who has the next move.  The
     *  value is arbitrary if gameOver(). */
    PieceColor whoseMove() {
        return _whoseMove;
    }

    /** Perform the move C0R0-C1R1, or pass if C0 is '-'.  For moves
     *  other than pass, assumes that legalMove(C0, R0, C1, R1). */
    void makeMove(char c0, char r0, char c1, char r1) {
        makeMove(Move.move(c0, r0, c1, r1, null));
    }

    /** Make the multi-jump C0 R0-C1 R1..., where NEXT is C1R1....
     *  Assumes the result is legal. */
    void makeMove(char c0, char r0, char c1, char r1, Move next) {
        makeMove(Move.move(c0, r0, c1, r1, next));
    }

    /** Make the Move MOV on this Board, assuming it is legal. */
    void makeMove(Move mov) {
        if (!legalMove(mov)) {
            System.out.println("Not a legal move");
            return;
        }
        PieceColor pieceToMove = board.get(mov.fromIndex()).getTile();
        board.get(mov.toIndex()).setTile(pieceToMove);
        board.get(mov.fromIndex()).setTile(EMPTY);
        if (mov.isRightMove()) {
            blocks[mov.toIndex()] = -1;
        } else if (mov.isLeftMove()) {
            blocks[mov.toIndex()] = 1;
        } else {
            blocks[mov.toIndex()] = 0;
        }
        if (mov.isJump()) {
            board.get(index(mov.jumpedCol(), mov.jumpedRow())).setTile(EMPTY);
        }
        if (mov.jumpTail() == null) {
            _whoseMove = _whoseMove.opposite();
        } else {
            makeMove(mov.jumpTail());
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Does a move under the hood.
     * @param mov Move
     */
    void makeQuickMove(Move mov) {
        PieceColor pieceToMove = board.get(mov.fromIndex()).getTile();
        board.get(mov.toIndex()).setTile(pieceToMove);
        board.get(mov.fromIndex()).setTile(EMPTY);
        if (mov.isRightMove()) {
            blocks[mov.toIndex()] = -1;
        } else if (mov.isLeftMove()) {
            blocks[mov.toIndex()] = 1;
        } else {
            blocks[mov.toIndex()] = 0;
        }

        if (mov.isJump()) {
            board.get(index(mov.jumpedCol(), mov.jumpedRow())).setTile(EMPTY);
        }
        if (mov.jumpTail() != null) {
            makeQuickMove(mov.jumpTail());
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Undos a move.
     * @param mov Move
     */
    void undo(Move mov) {
        PieceColor pieceToMove = board.get(mov.toIndex()).getTile();
        board.get(mov.fromIndex()).setTile(pieceToMove);
        board.get(mov.toIndex()).setTile(EMPTY);
        if (mov.isJump()) {
            board.get(index(mov.jumpedCol(),
                    mov.jumpedRow())).setTile(pieceToMove.opposite());
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * returns the Hashmap represnting the board.
     * @return HashMap<Integer, Tile>
     */
    public HashMap<Integer, Tile> getBoardRep() {
        return board;
    }

    /** Return a text depiction of the board.  If LEGEND, supply row and
     *  column numbers around the edges. */
    String toString(boolean legend) {
        Formatter out = new Formatter();
        final int twenty = 20;
        final int fift = 15;
        final int twentyFive = 25;
        out.format(" ");
        for (int i = twenty; i < twentyFive; i++) {
            out.format(" " + board.get(i).getTile().shortName());
        }
        out.format("\n");
        out.format(" ");
        for (int i = fift; i < twenty; i++) {
            out.format(" " + board.get(i).getTile().shortName());
        }
        out.format("\n");
        out.format(" ");
        for (int i = 10; i < 15; i++) {
            out.format(" " + board.get(i).getTile().shortName());
        }
        out.format("\n");
        out.format(" ");
        for (int i = 5; i < 10; i++) {
            out.format(" " + board.get(i).getTile().shortName());
        }
        out.format("\n");
        out.format(" ");
        for (int i = 0; i < 5; i++) {
            out.format(" " + board.get(i).getTile().shortName());
        }
        return out.toString();
    }

    /** Return true iff there is a move for the current player. */
    private boolean isMove() {
        if (getMoves().size() != 0) {
            return true;
        }
        return false;
    }

    /** Player that is on move. */
    protected PieceColor _whoseMove;

    /** Set true when game ends. */
    private boolean _gameOver;

    /**
     * Board representation.
     */
    private HashMap<Integer, Tile> board = new HashMap<Integer, Tile>();

    /**
     * BoardStates.
     */
    private Stack boardStates = new Stack();

    /**
     * keeps track of horizontal limitations.
     */
    private Integer[] blocks = new Integer[]
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    /** Convenience value giving values of pieces at each ordinal position. */
    static final PieceColor[] PIECE_VALUES = PieceColor.values();

    /** One cannot create arrays of ArrayList<Move>, so we introduce
     *  a specialized private list type for this purpose. */
    private static class MoveList extends ArrayList<Move> {
    }

    /** A read-only view of a Board. */
    private class ConstantBoard extends Board implements Observer {
        /** A constant view of this Board. */
        ConstantBoard() {
            super(Board.this);
            Board.this.addObserver(this);
        }

        @Override
        void copy(Board b) {
            assert false;
        }

        @Override
        void clear() {
            assert false;
        }

        @Override
        void makeMove(Move move) {
            assert false;
        }

        /** Undo the last move. */
        void undo() {
            assert false;
        }

        @Override
        public void update(Observable obs, Object arg) {
            super.copy((Board) obs);
            setChanged();
            notifyObservers(arg);
        }
    }
}
