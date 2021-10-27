// This file contains a SUGGESTION for the structure of your program.  You
// may change any of it, or add additional files to this directory (package),
// as long as you conform to the project specification.

// Comments that start with "//" are intended to be removed from your
// solutions.
package jump61;

import org.checkerframework.checker.units.qual.A;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Formatter;
import java.util.ArrayList;
import java.util.function.Consumer;

import static jump61.Side.*;
import static jump61.Square.INITIAL;
import static jump61.Square.square;

/** Represents the state of a Jump61 game.  Squares are indexed either by
 *  row and column (between 1 and size()), or by square number, numbering
 *  squares by rows, with squares in row 1 numbered from 0 to size()-1, in
 *  row 2 numbered from size() to 2*size() - 1, etc. (i.e., row-major order).
 *
 *  A Board may be given a notifier---a Consumer<Board> whose
 *  .accept method is called whenever the Board's contents are changed.
 *
 *  @author Caedi Seim
 */
class Board {

    /** An uninitialized Board.  Only for use by subtypes. */
    protected Board() {
        _notifier = NOP;
    }

    /** An N x N board in initial configuration. */
    Board(int N) {
        this();
        // calls the Board constructor that does not take in any argument
        _N = N;
        _numRed = 0;
        _numBlue = 0;
        _numMoves = 1;
        _board = new ArrayList<>(_N * _N);
        for (int i = 0; i < size() * size(); i++) {
            _board.add(INITIAL);
        }
        // FIXME
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear, and whose notifier does nothing. */
    Board(Board board0) {
        this.copy(board0);
        _history = new ArrayDeque<>();
        _notifier = NOP;
        // FIXME
        _readonlyBoard = new ConstantBoard(this);
    }

    /** Returns a readonly version of this board. */
    Board readonlyBoard() {
        return _readonlyBoard;
    }

    /** (Re)initialize me to a cleared board with N squares on a side. Clears
     *  the undo history and sets the number of moves to 0. */
    void clear(int N) {
        _N = N;
        _numMoves = 1;
        _numRed = 0;
        _numBlue = 0;
        _board = new ArrayList<>(_N * _N);
        for (int i = 0; i < size() * size(); i++) {
            _board.add(INITIAL);
        }
        _history.clear();
        // FIXME
        announce();
    }

    /** Copy the contents of BOARD into me. */
    void copy(Board board) {
        _N = board.size();
        _board = new ArrayList<>(_N * _N);
        _numMoves = 1;
        _numBlue = board._numBlue;
        _numRed = board._numRed;
        for (int i = 0; i < size() * size(); i++) {
            _board.add(board.get(i));
        }
        _history.clear();
        // FIXME
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history. Assumes BOARD and I have the same size. */
    private void internalCopy(Board board) {
        assert size() == board.size();
        _N = board.size();
        _board = new ArrayList<>(_N * _N);
        _numBlue = board._numBlue;
        _numRed = board._numRed;
        for (int i = 0; i < size() * size(); i++) {
            _board.add(board.get(i));
        }
        // FIXME
    }

    /** Return the number of rows and of columns of THIS. */
    int size() {
        return _N; // FIXME
    }

    /** Returns the contents of the square at row R, column C
     *  1 <= R, C <= size (). */
    Square get(int r, int c) {
        return get(sqNum(r, c));
    }

    /** Returns the contents of square #N, numbering squares by rows, with
     *  squares in row 1 number 0 - size()-1, in row 2 numbered
     *  size() - 2*size() - 1, etc. */
    Square get(int n) {
        if (n >= size() * size()) {
            throw new GameException("Get out of bounds");
        }
        else {
            return _board.get(n);
        }
    }
    /*
        You can number squares
        0 1 2 3 4
        5 6 7 8 9
        - up until size - 1
     */

    /** Returns the total number of spots on the board. */
    int numPieces() {
        int count = 0;
        for (int i = 0; i < size() * size(); i++) {
            count += _board.get(i).getSpots();
        }

        return count; // FIXME
    }

    /** Returns the Side of the player who would be next to move.  If the
     *  game is won, this will return the loser (assuming legal position). */
    Side whoseMove() {

        return ((numPieces() + size()) & 1) == 0 ? RED : BLUE;
    }

    /** Return true iff row R and column C denotes a valid square. */
    final boolean exists(int r, int c) {
        return 1 <= r && r <= size() && 1 <= c && c <= size();
    }

    /** Return true iff S is a valid square number. */
    final boolean exists(int s) {
        int N = size();
        return 0 <= s && s < N * N;
    }

    /** Return the row number for square #N. */
    final int row(int n) {
        return n / size() + 1;
    }

    /** Return the column number for square #N. */
    final int col(int n) {
        return n % size() + 1;
    }

    /** Return the square number of row R, column C. */
    final int sqNum(int r, int c) {
        return (c - 1) + (r - 1) * size();
    }
    //added in the -1

    /** Return a string denoting move (ROW, COL)N. */
    String moveString(int row, int col) {
        return String.format("%d %d", row, col);
    }

    /** Return a string denoting move N. */
    String moveString(int n) {
        return String.format("%d %d", row(n), col(n));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
        to square at row R, column C. */
    boolean isLegal(Side player, int r, int c) {
        return isLegal(player, sqNum(r, c));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
     *  to square #N. */
    boolean isLegal(Side player, int n) {
        if (exists(n)) {
            Side s = this.get(n).getSide();
            //numberBlue();
            return player.playableSquare(s);
        }
        return false; // FIXME
    }

    /** Returns true iff PLAYER is allowed to move at this point. */
    boolean isLegal(Side player) {
        return true; // FIXME
    }

    /** Returns the winner of the current position, if the game is over,
     *  and otherwise null. */
    final Side getWinner() {
        numberBlue();
        if (_numRed == _board.size()) {
            return RED;
        } else if (_numBlue == _board.size()) {
            return BLUE;
        }
        return null;  // FIXME
    }

    int numberBlue(){
        _numBlue = 0;
        _numRed = 0;
        _legalRed.clear();
        _legalBlue.clear();
        for (int i = 0; i < _board.size(); i++) {
            if (_board.get(i).getSide().equals(BLUE)) {
                _legalBlue.add(i);
                _numBlue++;
            }
            else if (_board.get(i).getSide().equals(RED)) {
                _legalRed.add(i);
                _numRed++;
            }
            else {
                _legalBlue.add(i);
                _legalRed.add(i);
            }

        }
        return _numBlue;
    }

    /** Return the number of squares of given SIDE. */
    int numOfSide(Side side) {
        numberBlue();
        if (side == RED) {
            return _numRed;
        }
        else if (side == BLUE) {
            return _numBlue;
        }
        else {
            return size() * size() - _numBlue - _numRed;
        }
         // FIXME
    }

    /** Add a spot from PLAYER at row R, column C.  Assumes
     *  isLegal(PLAYER, R, C). */
    void addSpot(Side player, int r, int c) {
        addSpot(player, sqNum(r, c));
        //simpleadd
        //a call to addspot represents a move
        // FIXME
    }

    int get_NumSpots(int r, int c) {
        return _board.get(sqNum(r, c)).getSpots();
    }



    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white). */
    void set(int r, int c, int num, Side player) {
        internalSet(r, c, num, player);
        announce();
        /*
        informs the rest of the program that it's made a move
        but what about the moves you want to test, but not announce
        for our AI
         */
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white).  Does not announce
     *  changes. */
    private void internalSet(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), num, player);
    }

    /** Set the square #N to NUM spots (0 <= NUM), and give it color PLAYER
     *  if NUM > 0 (otherwise, white). Does not announce changes. */
    private void internalSet(int n, int num, Side player) {
        if (0 <= num) {
            if (player == RED) {
                _numRed++;
            }
            else {
                _numBlue++;
            }
            _board.set(n, square(player, num));
        }
        // FIXME
    }

    // There are two obvious ways to conduct a game-tree search in the AI.
    //
    // First, you can explore the consequences of a possible move from
    // position A by making a copy of the Board in position A, and then
    // modifying that copy. Since you retain position A, you can return to
    // it to try other moves from that position.
    //
    // Second, you can explore the consequences of a possible move from
    // position A by making that move on your Board and then, when your
    // analysis of the move is complete, undoing the move to return you to
    // position A. This method is more complicated to implement, but has
    // the advantage that it can be considerably faster than making copies
    // of the Board (you will need one copy per move tried, which will very
    // quickly be thrown away).

    /** Undo the effects of one move (that is, one addSpot command).  One
     *  can only undo back to the last point at which the undo history
     *  was cleared, or the construction of this Board. */
    void undo() {
        if (_history.isEmpty()) {
            this.clear(_N);
        }
        else {
            if (this.equals(_history.getLast())) {
                _history.removeLast();
                if  (!_history.isEmpty()) {
                    this.internalCopy(_history.getLast());
                }
                else {
                    this.clear(_N);
                }
            }
        }
        // FIXME
    }
    /*
       mainly for the AI
       make a move, see if its a winning state, then walk that back and try something
       new
       - want to have something that stores the undo history, we want the previous
       states and current states
    */

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        // FIXME
    }
    /*
        state that you want to save and put it in the undo history
     */

    /** Add DELTASPOTS spots of side PLAYER to row R, column C,
     *  updating counts of numbers of squares of each color. */
    private void simpleAdd(Side player, int r, int c, int deltaSpots) {
        internalSet(r, c, deltaSpots + get(r, c).getSpots(), player);
    }

    /** Add DELTASPOTS spots of color PLAYER to square #N,
     *  updating counts of numbers of squares of each color. */
    private void simpleAdd(Side player, int n, int deltaSpots) {

        internalSet(n, deltaSpots + get(n).getSpots(), player);
    }

    /** Used in jump to keep track of squares needing processing.  Allocated
     *  here to cut down on allocations. */
    private final ArrayDeque<Integer> _workQueue = new ArrayDeque<>();

    /** Add a spot from PLAYER at square #N.  Assumes isLegal(PLAYER, N). */
    void addSpot(Side player, int n) {

        //_board.set(n, square(player, _board.get(n).getSpots() + 1));
        simpleAdd(player, n, 1);
        if (getWinner() == null) {
            int numSpots = _board.get(n).getSpots();
            if (numSpots > neighbors(n)) {
                _board.set(n, square(player, 1));
                jump(n);
            }
        }
        if (_moveHelper == 0) {
            _numMoves++;
            Board newB = new Board();
            newB.copy(this);
            _history.add(newB);

        }


        // FIXME
    }

    /** Do all jumping on this board, assuming that initially, S is the only
     *  square that might be over-full. */
    private void jump(int S) {
        // FIXME
        //use board.equals to
        //remember that objects might point to the same things
        //board b - new board();
        //board b2 = b
        //making changes to b2 will change b
        //know whether you are referring to a copy or not
        //use simple add in the jump method
        //a square will jump if it has > neighbots
        //
        if (getWinner() == null) {
            addLocations(S);
            _moveHelper++;
            while (_location.size() + 1 != 1) {
                int located = _location.pop();
                addSpot(_board.get(S).getSide(), located);

            }
        }
        Board newB = new Board();
        newB.copy(this);
        _history.add(newB);
        _numMoves++;
    }
    /*
        when you get a cell with too many spots you need to jump, usually this is a
        chain reaction, jumping only happens when adding spots
        - avoid infinite loops, end when board is one color!
        -
     */

    /** Add the locations of the neighbors to the queue. */
    void addLocations(int n) {
        if (neighbors(n) == 2) {
            if (n == 0) {
                _location.add(n + 1);
                _location.add(n + _N);
            }
            else if (n == _N - 1) {
                _location.add((n - 1));
                _location.add((n + _N));
            }
            else if (n == _N * (_N - 1)) {
                _location.add(n - _N);
                _location.add(n + 1);
            }
            else if (n == _N * _N - 1) {
                _location.add(n - _N);
                _location.add(n - 1);
            }
        }
        else if (neighbors(n) == 3) {
            if (row(n) == 1) {
                _location.add(n + _N);
                _location.add(n + 1);
                _location.add(n - 1);
            }
            else if (row(n) == _N) {
                _location.add(n + 1);
                _location.add(n - 1);
                _location.add(n - _N);
            }
            else if (col(n) == 1) {
                _location.add(n + _N);
                _location.add(n + 1);
                _location.add(n - _N);
            }
            else if (col(n) == _N) {
                _location.add(n + _N);
                _location.add(n - 1);
                _location.add(n - _N);
            }
        }
        else {
            _location.add(n - _N);
            _location.add(n + 1);
            _location.add(n - 1);
            _location.add(n + _N);
        }
    }




    /** Returns my dumped representation. */
    @Override
    public String toString() {
        //Formatter out = new Formatter();
        String output = "===";
        for (int i = 0; i < _N * _N; i++) {
            if (i % size() == 0) {
                output += "\n" + "    " + _board.get(i).toString();
            }
            else if ((i + 1) % _board.size() == 0){
                output += " " + _board.get(i).toString() + "\n";
            }
            else {
                output += " " + _board.get(i).toString();
            }
        }
        output += "===";
        return output;
        // FIXME
    }

    /** Returns an external rendition of me, suitable for human-readable
     *  textual display, with row and column numbers.  This is distinct
     *  from the dumped representation (returned by toString). */
    public String toDisplayString() {
        String[] lines = toString().trim().split("\\R");
        Formatter out = new Formatter();
        for (int i = 1; i + 1 < lines.length; i += 1) {
            out.format("%2d %s%n", i, lines[i].trim());
        }
        out.format("  ");
        for (int i = 1; i <= size(); i += 1) {
            out.format("%3d", i);
        }
        return out.toString();
    }

    /** Returns the number of neighbors of the square at row R, column C. */
    int neighbors(int r, int c) {
        int size = size();
        int n;
        n = 0;
        if (r > 1) {
            n += 1;
        }
        if (c > 1) {
            n += 1;
        }
        if (r < size) {
            n += 1;
        }
        if (c < size) {
            n += 1;
        }
        return n;
    }

    /** Returns the number of neighbors of square #N. */
    int neighbors(int n) {
        return neighbors(row(n), col(n));
    }

    ArrayList<Integer> legalRed() {
        numberBlue();
        return _legalRed;
    }

    ArrayList<Integer> legalBlue() {
        numberBlue();
        return _legalBlue;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) {
            return false;
        } else {
            Board B = (Board) obj;
            if (_N * _N == B._N * B._N) {
                for (int i = 0; i < _N * _N; i++) {
                    if (!_board.get(i).equals(B._board.get(i))) {
                        return false;
                    }
                }
            }
            return true;  // FIXME
        }
    }

    @Override
    public int hashCode() {
        return numPieces();
    }

    /** Set my notifier to NOTIFY. */
    public void setNotifier(Consumer<Board> notify) {
        _notifier = notify;
        announce();
    }

    /** Take any action that has been set for a change in my state. */
    private void announce() {
        _notifier.accept(this);
    }

    /** A notifier that does nothing. */
    private static final Consumer<Board> NOP = (s) -> { };

    /** A read-only version of this Board. */
    private ConstantBoard _readonlyBoard;

    /** Use _notifier.accept(B) to announce changes to this board. */
    private Consumer<Board> _notifier;

    /** Size of the board. */
    private int _N;

    /** Number of moves that have been made */
    public int _numMoves;

    /** Number of red cubes */
    private int _numRed;

    /** Number of blue cubes. */
    private int _numBlue;

    /** The board we are using. */
    private ArrayList<Square> _board;

    /** The history of past board states. */
    public ArrayDeque<Board> _history = new ArrayDeque<>();

    /** A queue of neighbor locations. */
    public ArrayDeque<Integer> _location = new ArrayDeque<>();

    /** A queue of the places of legal red moves. */
    public ArrayList<Integer> _legalRed = new ArrayList<>();

    /** A queue of the places of legal blue moves. */
    public ArrayList<Integer> _legalBlue = new ArrayList<>();

    private int _moveHelper = 0;
    // FIXME: other instance variables here.

}
