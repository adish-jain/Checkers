package qirkat;

import static qirkat.PieceColor.*;
import static qirkat.Command.Type.*;

/** A Player that receives its moves from its Game's getMoveCmnd method.
 *  @author Adish Jain
 */
class Manual extends Player {

    /** A Player that will play MYCOLOR on GAME, taking its moves from
     *  GAME. */
    Manual(Game game, PieceColor myColor) {
        super(game, myColor);
        _prompt = myColor + ": ";
    }

    @Override
    Move myMove() {
        Command thisComm = super.game().getMoveCmnd(_prompt);
        if (this.game().getState() != Game.State.PLAYING) {
            return null;
        }
        Move mov = Move.parseMove(thisComm.operands()[0]);
        return mov;
    }

    /** Identifies the player serving as a source of input commands. */
    private String _prompt;
}

