package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import java.util.HashSet;
import java.util.HashMap;

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
        assertEquals(0, test1._inserted[1].setting());
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

    /*
    I MQ      (AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)
 II ME     (FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)
 III MV    (ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)
 IV MJ     (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)
 V MZ      (AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)
 VI MZM    (AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)
 VII MZM   (ANOUPFRIMBZTLWKSVEGCJYDHXQ)
 VIII MZM  (AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)
 Beta N    (ALBEVFCYODJWUGNMQTZSKPR) (HIX)
 Gamma N   (AFNIRLBSQWVXGUZDKMTPCOYJHE)
 B R       (AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP)
           (RX) (SZ) (TV)
 C R       (AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW)

     */
    @Test
    public void checkConvert() {
        Alphabet a = new Alphabet(UPPER_STRING);
        HashMap<String, Rotor> test = new HashMap<>();
        String cycles1 = String.valueOf(NAVALA.get("I"));
        String cycles2 = String.valueOf(NAVALA.get("II"));
        String cycles3 = String.valueOf(NAVALA.get("III"));
        String cycles4 = String.valueOf(NAVALA.get("IV"));
        String cycles5 = String.valueOf(NAVALA.get("V"));
        String cycles6 = String.valueOf(NAVALA.get("VI"));
        String cycles7 = String.valueOf(NAVALA.get("VII"));
        String cycles8 = String.valueOf(NAVALA.get("VIII"));
        String cycles9 = String.valueOf(NAVALA.get("Beta"));
        String cycles10 = String.valueOf(NAVALA.get("Gamma"));
        String cycles11 = String.valueOf(NAVALA.get("B"));
        String cycles12 = String.valueOf(NAVALA.get("C"));

        test.put("I", new MovingRotor("I", new Permutation(cycles1, a), "Q"));
        test.put("II", new MovingRotor("II", new Permutation(cycles2, a), "E"));
        test.put("III", new MovingRotor("III", new Permutation(cycles3, a), "V"));
        test.put("IV", new MovingRotor("IV", new Permutation(cycles4, a), "J"));
        test.put("V", new MovingRotor("V", new Permutation(cycles5, a), "Z"));
        test.put("VI", new MovingRotor("VI", new Permutation(cycles6, a), "ZM"));
        test.put("VII", new MovingRotor("VII", new Permutation(cycles7, a), "ZM"));
        test.put("VIII", new MovingRotor("VIII", new Permutation(cycles8, a), "ZM"));
        test.put("Beta", new FixedRotor("Beta", new Permutation(cycles9, a)));
        test.put("Gamma", new FixedRotor("Gamma", new Permutation(cycles10, a)));
        test.put("B", new Reflector("B", new Permutation(cycles11, a)));
        test.put("C", new FixedRotor("C", new Permutation(cycles12, a)));

        Machine m = new Machine(a, 5, 3, test);
        String[] insert = new String[]{"B", "Beta", "I", "II", "III"};
        m.insertRotors(insert);
        m.setRotors("AAAA");
        System.out.println(m.convert("HELLOWORLD"));





    }


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
