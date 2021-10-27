// This file contains definitions for an OPTIONAL part of your project.  If you
// choose not to do the optional point, you can delete this file from your
// project.

// This file contains a SUGGESTION for the structure of your program.  You
// may change any of it, or add additional files to this directory (package),
// as long as you conform to the project specification.

// Comments that start with "//" are intended to be removed from your
// solutions.
package jump61;

import java.util.ArrayList;
import java.util.Random;

import static jump61.Side.*;

/** An automated Player.
 *  @author P. N. Hilfinger
 */
class AI extends Player {

    /** A new player of GAME initially COLOR that chooses moves automatically.
     *  SEED provides a random-number seed used for choosing moves.
     */
    AI(Game game, Side color, long seed) {
        super(game, color);
        _random = new Random(seed);
    }

    @Override
    String getMove() {
        Board board = getGame().getBoard();
        assert getSide() == board.whoseMove();
        int choice = searchForMove();
        getGame().reportMove(board.row(choice), board.col(choice));
        return String.format("%d %d", board.row(choice), board.col(choice));
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private int searchForMove() {
        //return value should be the index of the square here
        Board work = new Board(getBoard());
        assert getSide() == work.whoseMove();
        _foundMove = -1;
        int value = 0;
        if (getSide() == RED) {
            Random r = new Random();
            _foundMove = work.legalRed().get(r.nextInt(work.legalRed().size()));
            if (work.isLegal(getSide(), _foundMove)) {
                return _foundMove;
            }// FIXME
        } else {
            Random r = new Random();
            _foundMove = work.legalBlue().get(r.nextInt(work.legalBlue().size()));
            if (work.isLegal(getSide(), _foundMove)) {
                return _foundMove;
            } // FIXME
        }
        return _foundMove;
    }




    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int minMax(Board board, int depth, boolean saveMove,
                       int sense, int alpha, int beta) {

        //if depth == 0 or game over in board position, return static eval of position
        if (depth == 0 || board.getWinner() != null) {
            if (board.getWinner().equals(RED)) {
                return staticEval(board, Integer.MAX_VALUE);
            }
            return staticEval(board, Integer.MIN_VALUE);
        }

        if (sense == 1) {
            int bestSoFar = Integer.MIN_VALUE;
            for (int i = 0; i < board.legalRed().size(); i++) {
                board.addSpot(RED, board.legalRed().get(i));
                //add the spot there, then check the heuristic
                int response = minMax(board, depth - 1,
                        saveMove, sense, alpha, beta);
                if (response < bestSoFar) {
                    bestSoFar = response;
                    alpha = Math.max(alpha, bestSoFar);
                    if (alpha >= beta) {
                        return bestSoFar;
                    }
                }

            }
            return bestSoFar;
        }
        else {
            int bestSoFar = Integer.MAX_VALUE;
            for (int i = 0; i < board.legalBlue().size(); i++) {
                board.addSpot(BLUE, board.legalBlue().get(i));
                int response = minMax(board, depth - 1,
                        saveMove, sense, alpha, beta);
                if (response > bestSoFar) {
                    bestSoFar = response;
                    alpha = Math.min(alpha, bestSoFar);
                    if (alpha <= beta) {
                        return bestSoFar;
                    }
                    return alpha;
                }
            }
            return bestSoFar;
        }
        //save move happens on the first recursion and is set to false after
        //so when we set depth to 4 and save the move to found move,
        //we would want to save a move in the _foundMove if save move is true
        //save move is true when
        //found move should be assinged to the best move to make at the current
        //depth, so maybe savemove when we are at the current depth
    }
    /*
    Sense is 1 if red -1 if blue
    feeding legal moves to both the maximizer and minimizer which
    recursively evaluates the heuristic of that move
     */

    /** Return a heuristic estimate of the value of board position B.
     *  Use WINNINGVALUE to indicate a win for Red and -WINNINGVALUE to
     *  indicate a win for Blue. */
    private int staticEval(Board b, int winningValue) {
        int value;
        if (b.getWinner() != null) {
            if (b.numOfSide(RED) > 0) {
                return winningValue;
            }
            return -winningValue;
        }
        if (b.whoseMove().equals(RED)) {
            value = b.numOfSide(b.whoseMove())
                    - b.numOfSide(b.whoseMove().opposite());
            return value;
        }
        else {
            //if move is blue then red - blue, if blue is in a better position
            //it should return winning value
            value = b.numOfSide(b.whoseMove().opposite())
                    - b.numOfSide(b.whoseMove());
            return value;
        }
        /*
            if red wins, we return winningvalue, and -winningvalue if
            blue wins. But if neither wins, do we return a number of
            smaller magnitude? If things are better for red, then return
            a big value but not as large as winningvalue? And if it's better
            for blue, a large negative value but not as big as -winningvalue
         */

        /*
        possibly having more squares per num pieces
        if game is won return the number of sides
        The closer a board score is to negative infinity the better it is for blue
        The closer a board score is to positive infinity the better it is for red

         Therefore, blue wants to search for the move that will produce
         the “minimal”, or most negative board score - because it’s the
         best for them. However, remember that red is the other player in
         the game, and doesn’t want that. Red wants the board score to be
         better for them, or making it as close to positive infinity as they
         can.
         */

    }


    /** A random-number generator used for move selection. */
    private Random _random;

    /** Used to convey moves discovered by minMax. */
    private int _foundMove;
}
