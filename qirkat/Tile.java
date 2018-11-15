package qirkat;

/**
 * The Tile Class.
 * @author Adish Jain
 */
public class Tile {

    /**
     *
     * @param pc PieceColor
     * @param brd Board
     * @param args list of booleans
     */
    public Tile(PieceColor pc, Board brd, boolean... args) {
        _rightSpace = args[0];
        _leftSpace = args[1];
        _topSpace = args[2];
        _bottomSpace = args[3];
        _diagonalLUSpace = args[4];
        _diagonalLDSpace = args[5];
        _diagonalRUSpace = args[6];
        _diagonalRDSpace = args[7];
        _twoRightSpace = args[8];
        _twoLeftSpace = args[9];
        _twoTopSpace = args[10];
        _twoBottomSpace = args[11];
        _twoDiagonalLUSpace = args[12];
        _twoDiagonalLDSpace = args[13];
        _twoDiagonalRUSpace = args[14];
        _twoDiagonalRDSpace = args[15];
        _board = brd;
        _tile = pc;
    }

    /**
     *
     * @param tileToCopy Tile
     */
    public Tile(Tile tileToCopy) {
        this._rightSpace = tileToCopy._rightSpace;
        this._leftSpace = tileToCopy._leftSpace;
        this._topSpace = tileToCopy._topSpace;
        this._bottomSpace = tileToCopy._bottomSpace;
        this._diagonalLUSpace = tileToCopy._diagonalLUSpace;
        this._diagonalLDSpace = tileToCopy._diagonalLDSpace;
        this._diagonalRUSpace = tileToCopy._diagonalRUSpace;
        this._diagonalRDSpace = tileToCopy._diagonalRDSpace;
        this._twoRightSpace = tileToCopy._twoRightSpace;
        this._twoLeftSpace = tileToCopy._twoLeftSpace;
        this._twoTopSpace = tileToCopy._twoTopSpace;
        this._twoBottomSpace = tileToCopy._twoBottomSpace;
        this._twoDiagonalLUSpace = tileToCopy._twoDiagonalLUSpace;
        this._twoDiagonalLDSpace = tileToCopy._twoDiagonalLDSpace;
        this._twoDiagonalRUSpace = tileToCopy._twoDiagonalRUSpace;
        this._twoDiagonalRDSpace = tileToCopy._twoDiagonalRDSpace;
        this._board = tileToCopy._board;
        if (tileToCopy._tile == PieceColor.WHITE) {
            this._tile = PieceColor.WHITE;
        } else if (tileToCopy._tile == PieceColor.BLACK) {
            this._tile = PieceColor.BLACK;
        } else {
            this._tile = PieceColor.EMPTY;
        }
    }

    /**
     *
     * @return the tile.
     */
    PieceColor getTile() {
        return _tile;
    }

    /**
     *
     * @param c a PieceColor
     */
    void setTile(PieceColor c) {
        _tile = c;
    }

    /**
     *
     * @return the two right space.
     */
    boolean hasTwoRightSpace() {
        return _twoRightSpace;
    }

    /**
     *
     * @return the two left space.
     */
    boolean hasTwoLeftSpace() {
        return _twoLeftSpace;
    }

    /**
     *
     * @return the two top space.
     */
    boolean hasTwoTopSpace() {
        return _twoTopSpace;
    }

    /**
     *
     * @return the two bottom space.
     */
    boolean hasTwoBottomSpace() {
        return _twoBottomSpace;
    }

    /**
     *
     * @return the two diagonal left upper space.
     */
    boolean hasTwoDiagonalLUSpace() {
        return _twoDiagonalLUSpace;
    }

    /**
     *
     * @return the two diagonal left lower space.
     */
    boolean hasTwoDiagonalLDSpace() {
        return _twoDiagonalLDSpace;
    }

    /**
     *
     * @return the two diagonal right upper space.
     */
    boolean hasTwoDiagonalRUSpace() {
        return _twoDiagonalRUSpace;
    }

    /**
     *
     * @return the two diagonal right lower space.
     */
    boolean hasTwoDiagonalRDSpace() {
        return _twoDiagonalRDSpace;
    }

    /**
     *
     * @return the right space.
     */
    boolean hasRightSpace() {
        return _rightSpace;
    }

    /**
     *
     * @return the left space.
     */
    boolean hasLeftSpace() {
        return _leftSpace;
    }

    /**
     *
     * @return the top space.
     */
    boolean hasTopSpace() {
        return _topSpace;
    }

    /**
     *
     * @return the bottom space.
     */
    boolean hasBottomSpace() {
        return _bottomSpace;
    }

    /**
     *
     * @return the diagonal left upper space.
     */
    boolean hasdiagonalLUSpace() {
        return _diagonalLUSpace;
    }

    /**
     *
     * @return the diagonal left lower space.
     */
    boolean hasdiagonalLDSpace() {
        return _diagonalLDSpace;
    }

    /**
     *
     * @return the diagonal right upper space.
     */
    boolean hasdiagonalRUSpace() {
        return _diagonalRUSpace;
    }

    /**
     *
     * @return the diagonal right lower space.
     */
    boolean hasdiagonalRDSpace() {
        return _diagonalRDSpace;
    }

    /**
     * PieceColor Tile.
     */
    private PieceColor _tile;

    /**
     * Board.
     */
    private Board _board;

    /**
     * Right space.
     */
    private boolean _rightSpace;

    /**
     * Left space.
     */
    private boolean _leftSpace;

    /**
     * Top space.
     */
    private boolean _topSpace;

    /**
     * Bottom space.
     */
    private boolean _bottomSpace;

    /**
     * Diagonal Left upper space.
     */
    private boolean _diagonalLUSpace;

    /**
     * diagonal left lower space.
     */
    private boolean _diagonalLDSpace;

    /**
     * diagonal right upper space.
     */
    private boolean _diagonalRUSpace;

    /**
     * diagonal right lower space.
     */
    private boolean _diagonalRDSpace;

    /**
     * two right space.
     */
    private boolean _twoRightSpace;

    /**
     * two left space.
     */
    private boolean _twoLeftSpace;

    /**
     * two top space.
     */
    private boolean _twoTopSpace;

    /**
     * two bottom space.
     */
    private boolean _twoBottomSpace;

    /**
     * two diagonal left upper space.
     */
    private boolean _twoDiagonalLUSpace;

    /**
     * two diagonal left lower space.
     */
    private boolean _twoDiagonalLDSpace;

    /**
     * two diagonal right upper space.
     */
    private boolean _twoDiagonalRUSpace;

    /**
     * two diagonal right lower space.
     */
    private boolean _twoDiagonalRDSpace;
}
