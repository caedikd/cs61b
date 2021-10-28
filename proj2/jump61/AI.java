
package jump61;

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
        Board work = new Board(getBoard());
        assert getSide() == work.whoseMove();
        if (getSide() == RED) {
            minMax(work, 6, true, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            minMax(work, 6, true, -1, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
        int best;
        if (depth == 0 || board.getWinner() != null) {
            return staticEval(board, Integer.MAX_VALUE);
        }

        if (sense == 1) {

            int bestSoFar = Integer.MIN_VALUE;

            for (int i = 0; i < board.legalRed().size(); i++) {
                int sense2 = -1;
                if (board.whoseMove() == RED) {
                    sense2 = 1;
                }
                int x = board.legalRed().get(i);
                board.addSpot(RED, board.legalRed().get(i));
                int response = minMax(board, depth - 1,
                        false, sense2, alpha, beta);

                if (response > bestSoFar && saveMove) {
                    _foundMove = x;
                }
                if (response > bestSoFar) {
                    bestSoFar = response;
                    alpha = Math.max(alpha, bestSoFar);
                    if (alpha >= beta) {
                        return bestSoFar;
                    }
                }
            }
            return bestSoFar;
        } else {
            int bestSoFar = Integer.MAX_VALUE;
            for (int i = 0; i < board.legalBlue().size(); i++) {
                int sense2 = -1;
                if (board.whoseMove() == RED) {
                    sense2 = 1;
                }
                int x = board.legalBlue().get(i);
                board.addSpot(BLUE, board.legalBlue().get(i));
                int response = minMax(board, depth - 1,
                        false, sense2, alpha, beta);
                if (response < bestSoFar && saveMove) {
                    _foundMove = x;
                }
                if (response < bestSoFar) {
                    bestSoFar = response;
                    beta = Math.min(beta, bestSoFar);
                    if (alpha >= beta) {
                        return bestSoFar;
                    }
                }
            }
            return bestSoFar;
        }
    }
    /*
    .Sense is 1 if red -1 if blue
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
        } else {
            value = b.numOfSide(b.whoseMove().opposite())
                    - b.numOfSide(b.whoseMove());
            return value;
        }

    }


    /** A random-number generator used for move selection. */
    private Random _random;

    /** Used to convey moves discovered by minMax. */
    private int _foundMove;
}
