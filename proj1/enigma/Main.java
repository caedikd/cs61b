package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.lang.reflect.Array;
import java.util.*;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Caedi Seim
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine process = readConfig();
        String setting = _input.nextLine();
        while (_input.hasNextLine()) {
            setUp(process, setting);
            String word = _input.nextLine();
            while (word.isEmpty()) {
                word = _input.nextLine();
            }
            while (!word.contains("*")) {
                String result = process.convert(word.replaceAll("\\s", ""));
                printMessageLine(result);
                if (!_input.hasNextLine()) {

                    break;
                }
                else {
                    word = _input.nextLine();

                }
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            _alphabet = new Alphabet();
            int numRotors = 0;
            int numPawls = 0;
            HashMap<String, Rotor> alls = new HashMap<String, Rotor>();

            if (_config.hasNext()) {
                _alphabet = new Alphabet(_config.next());
            }
            if (_config.hasNext()) {
                numRotors = Integer.parseInt(_config.next());
            }
            if (_config.hasNext()) {
                numPawls = Integer.parseInt(_config.next());
            }
            _name = _config.next();
            while (_config.hasNext()) {
                alls.put(_name, readRotor());
                if (_config.hasNext()){
                    _name = _config.next();
                }
            }
            return new Machine(_alphabet, numRotors, numPawls, alls);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String type = _config.next().toUpperCase();
            String notches = type;
            String cycles = "";
            while (_config.hasNext("\\(.*")) {
                cycles += _config.next();
                cycles += " ";
            }

            Permutation p = new Permutation(cycles, _alphabet);
            if (type.charAt(0) == 'M') {
                notches = notches.substring(1);
                return new MovingRotor(_name, p, notches);
            }

            else if (type.charAt(0) == 'R') {
                return new Reflector(_name, p);
            }

            else {
                return new FixedRotor(_name, p);
            }

        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        /* create a new scanner out of settings
         read rotor names into array call insertrotors onto that then read next item
        and call set rotors and then read the rest of the line and create a new permutation
        and pass to set plugboard or call permutation on empty string */
        Scanner settingScan = new Scanner(settings);
        String[] scans = new String[M.numRotors()];

        if (settingScan.hasNext("[*]")) {
            settingScan.next();
            for (int i = 0; i < M.numRotors(); i++) {
                scans[i] = (settingScan.next());
            }
        }
        if (scans.length != M.numRotors()) {
            throw new EnigmaException("Wrong numbers of rotors in Config");
        }
        M.insertRotors(scans);
        String setChars = "";
        if (settingScan.hasNext()) {
            setChars = settingScan.next();
        }
        M.setRotors(setChars);
        String cycles = "";
        while (settingScan.hasNext()) {
            cycles += settingScan.next();
            cycles += " ";
        }
        Permutation p = new Permutation(cycles, _alphabet);
        M.setPlugboard(p);

    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        if (msg.length() == 0) {
            _output.println();
        }
        while (msg.length() > 0) {
            if (msg.length() <= 5) {
                _output.println(msg);
                msg = "";
            }
            else {
                _output.print(msg.substring(0, 5) + " ");
                msg = msg.substring (5, msg.length());
            }
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    private String _notches;

    private String temp;

    private String _name;

    private String perm;

    private String _notch;

}
