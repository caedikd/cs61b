package gitlet;
import org.junit.Test;


public class CommitUnit {


    @Test
    public void printCommitDate() {
        Commit newCommit = new Commit( "initial commit", null);
        System.out.println(newCommit.toString());
    }

    @Test
    public void printFileNames() {

    }

}
