package gitlet;
import org.junit.Test;


public class CommitUnit {


    @Test
    public void printCommitDate() {
        Commit newCommit = new Commit("null", "message", null);
        System.out.println(newCommit.toString());
    }

}
