package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.HashMap;

import static org.junit.Assert.*;

import static enigma.TestUtils.*;


public class MachineTest {

    public void checkRotors() {

    }

    @Test
    public void setRotorsTest() {
        String set = "ABCDE";
        Alphabet a = new Alphabet(UPPER_STRING);
        Permutation p = new Permutation(NAVALA.get("I"), a);
        HashMap<String, Rotor> allRotors = new HashMap<>();

        /*
        The rotors that should actually be inserted
         */
        Reflector reflector = new Reflector("I", new Permutation(NAVALA.get("I"), a));
        MovingRotor movingRotor1 = new MovingRotor("II", new Permutation(NAVALA.get("II"), a), "A");
        MovingRotor movingRotor2 = new MovingRotor("III", new Permutation(NAVALA.get("III"), a), "A");
        MovingRotor movingRotor3 = new MovingRotor("IV", new Permutation(NAVALA.get("IV"), a), "A");
        MovingRotor movingRotor4 = new MovingRotor("V", new Permutation(NAVALA.get("V"), a), "A");
        FixedRotor fixedRotor1 = new FixedRotor("Beta", new Permutation(NAVALA.get("Beta"), a));

        /*
        The extra rotors that should be in the hashmap
         */
        Reflector reflector1 = new Reflector("VI", new Permutation(NAVALA.get("VI"), a));
        MovingRotor movingRotor5 =  new MovingRotor("VII", new Permutation(NAVALA.get("VII"), a), "A");
        MovingRotor movingRotor6 = new MovingRotor("VIII", new Permutation(NAVALA.get("VIII"), a), "A");

        allRotors.put("I", reflector);
        allRotors.put("II", movingRotor1);
        allRotors.put("III", movingRotor2);
        allRotors.put("IV", movingRotor3);
        allRotors.put("V", movingRotor4);
        allRotors.put("Beta", fixedRotor1);
        allRotors.put("VI", reflector1);
        allRotors.put("VII", movingRotor5);
        allRotors.put("VIII", movingRotor6);

        int numRotor = 6;
        int pawls = 4;
        /*
        String that contains the names of rotors that should happen
         */
        String[] realRotors = {"I", "Beta", "II", "III", "IV", "V"};
        Machine test1 = new Machine(a, 6, 4, allRotors);
        test1.insertRotors(realRotors);
        test1.setRotors(set);
        assertEquals(65, test1._inserted[1].setting());
    }

    /*
     NAVALA.put("I", "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)");
        NAVALA.put("II", "(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)");
        NAVALA.put("III", "(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)");
        NAVALA.put("IV", "(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)");
        NAVALA.put("V", "(AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)");
        NAVALA.put("VI", "(AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK) ");
        NAVALA.put("VII", "(ANOUPFRIMBZTLWKSVEGCJYDHXQ) ");
        NAVALA.put("VIII", "(AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)");
        NAVALA.put("Beta", "(ALBEVFCYODJWUGNMQTZSKPR) (HIX)");
        NAVALA.put("Gamma", "(AFNIRLBSQWVXGUZDKMTPCOYJHE)");
        NAVALA.put("B",
                  "(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) "
                  + "(MP) (RX) (SZ) (TV)");
        NAVALA.put("C",
                  "(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) "
                  + "(PW) (QZ) (SX) (UY)");
     */
    @Test
    public void insertRotorsTest() {
        Alphabet a = new Alphabet(UPPER_STRING);
        Permutation p = new Permutation(NAVALA.get("I"), a);
        HashMap<String, Rotor> allRotors = new HashMap<>();

        /*
        The rotors that should actually be inserted
         */
        Reflector reflector = new Reflector("I", new Permutation(NAVALA.get("I"), a));
        MovingRotor movingRotor1 = new MovingRotor("II", new Permutation(NAVALA.get("II"), a), "A");
        MovingRotor movingRotor2 = new MovingRotor("III", new Permutation(NAVALA.get("III"), a), "A");
        MovingRotor movingRotor3 = new MovingRotor("IV", new Permutation(NAVALA.get("IV"), a), "A");
        MovingRotor movingRotor4 = new MovingRotor("V", new Permutation(NAVALA.get("V"), a), "A");
        FixedRotor fixedRotor1 = new FixedRotor("Beta", new Permutation(NAVALA.get("Beta"), a));

        /*
        The extra rotors that should be in the hashmap
         */
        Reflector reflector1 = new Reflector("VI", new Permutation(NAVALA.get("VI"), a));
        MovingRotor movingRotor5 =  new MovingRotor("VII", new Permutation(NAVALA.get("VII"), a), "A");
        MovingRotor movingRotor6 = new MovingRotor("VIII", new Permutation(NAVALA.get("VIII"), a), "A");

        allRotors.put("I", reflector);
        allRotors.put("II", movingRotor1);
        allRotors.put("III", movingRotor2);
        allRotors.put("IV", movingRotor3);
        allRotors.put("V", movingRotor4);
        allRotors.put("Beta", fixedRotor1);
        allRotors.put("VI", reflector1);
        allRotors.put("VII", movingRotor5);
        allRotors.put("VIII", movingRotor6);

        int numRotor = 6;
        int pawls = 4;
        /*
        String that contains the names of rotors that should happen
         */
        String[] realRotors = {"I", "Beta", "II", "III", "IV", "V"};
        Machine test1 = new Machine(a, 6, 4, allRotors);
        assertEquals(6, test1.numRotors());
        assertEquals(4, test1.numPawls());

        test1.insertRotors(realRotors);
        assertArrayEquals(realRotors, test1.insert());
    }

}
