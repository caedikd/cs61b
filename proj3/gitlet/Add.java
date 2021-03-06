package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;

/** Adds a copy of the file as it currently exists to the staging area
 * Staging an already-staged file overwrites the previous
 * entry in the staging area with the new contents. The
 * staging area should be somewhere in .gitlet.
 */

/**
 * use the add directory as the place that will hold the file.
 * there will be a hashmap of blobs that will be serialized
 * into this file that are the things that are supposed to be added
 * in status we will unserialize from this file and get the
 * names of things that will be added
 * @author caedi
 */
public class Add implements Serializable {

    /** File private var. */
    private static File staging = new File(Init.getAdd(), "addStaging");

    /**
     * File accessor.
     * @return File
     */
    public static File getStaging() {
        return staging;
    }

    /**
     * File accessor.
     * @return File
     */
    public static File getModified() {
        return modified;
    }

    /**
     * File accessor.
     * @return File
     */
    public static File getRmStaging() {
        return rmStaging;
    }

    /** File private var. */
    private static File rmStaging = new File(Init.getAdd(), "rmStaging");
    /** File private var. */
    private static File modified =  new File(Init.getAdd(), "staged");

    /** File private var. */
    private static LinkedHashMap stagedAdd;

    /** File private var. */
    private static LinkedHashMap stagedRem;

    /** File private var. */
    private static LinkedHashMap stagedMod;

    /** Adds the file to the staging directory given the fileName.
     * @param fileName name
     * */
    public static void add(String fileName) throws IOException {
        File temp = new File(Init.getCWD(), fileName);

        if (rmStaging.exists()) {
            stagedRem = Utils.readObject(rmStaging,
                    LinkedHashMap.class);
            if (stagedRem.containsKey(fileName)) {
                stagedRem.remove(fileName);
                Utils.writeObject(rmStaging, stagedRem);
            }
            System.exit(0);
        } else if (!temp.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }


        byte[] inside = Utils.readContents(temp);
        String id = Utils.sha1(inside);

        Head currHead = new Head(Init.getHead());
        String currentSha = currHead.getCurrentCommitSha();
        File commitMetadata = new File(Init.getCommits(),
                currentSha + "/" + currentSha);

        if (commitMetadata.exists()) {
            stagedAdd = Utils.readObject(commitMetadata,
                    LinkedHashMap.class);
            if (stagedAdd.containsKey(fileName)) {
                String[] pathSha = (String[]) stagedAdd.get(fileName);
                if (pathSha[0].equals(id)) {
                    if (rmStaging.exists()) {
                        stagedRem = Utils.readObject(rmStaging,
                                LinkedHashMap.class);
                        if (stagedRem.containsKey(fileName)) {
                            stagedRem.remove(fileName);
                            Utils.writeObject(rmStaging, stagedRem);
                        }
                    }
                    System.exit(0);
                }

            }
            if (modified.exists()) {
                stagedMod = Utils.readObject(modified, LinkedHashMap.class);
            } else {
                stagedMod = new LinkedHashMap();
            }
            stagedMod.put(fileName, inside);
            stagedAdd.put(fileName, inside);
        } else {
            case1(fileName, inside);
        }

        Utils.writeObject(modified, stagedMod);
        Utils.writeObject(staging, stagedAdd);

    }

    /**
     * Remove lines.
     * @param fileName fn
     * @param inside inside
     */
    public static void case1(String fileName, byte[] inside) {
        if (modified.exists()) {
            stagedMod = Utils.readObject(modified, LinkedHashMap.class);
        } else {
            stagedMod = new LinkedHashMap();
        }
        stagedAdd = new LinkedHashMap();
        stagedAdd.put(fileName, inside);
        stagedMod.put(fileName, inside);
    }

    /**
     * If the current working version of the file is identical to
     * the version in the current commit, do not stage it to be added,
     * and remove it from the staging area if it is already there (as can
     * happen when a file is changed, added, and then changed back). The
     * file will no longer be staged for removal (see gitlet rm), if
     * it was at the time of the command.
     * @param fileName fn
     */
    public static void rm(String fileName) {
        File temp = new File(Init.getCWD(), fileName);
        int i = 0;
        if (rmStaging.exists()) {
            stagedRem = Utils.readObject(rmStaging, LinkedHashMap.class);
        } else {
            stagedRem = new LinkedHashMap();
        }

        if (modified.exists()) {
            LinkedHashMap stagedAdd1 =
                    Utils.readObject(modified, LinkedHashMap.class);
            if (stagedAdd1.containsKey(fileName)) {
                stagedAdd1.remove(fileName);
                i += 1;
            }
            Utils.writeObject(modified, stagedAdd1);
        } else {
            Head currHead = new Head(Init.getHead());
            String currentSha = currHead.getCurrentCommitSha();
            File commitMetadata = new File(Init.getCommits(),
                    currentSha + "/" + currentSha);
            if (commitMetadata.exists()) {
                LinkedHashMap trackedFiles =
                        Utils.readObject(commitMetadata, LinkedHashMap.class);
                if (trackedFiles.containsKey(fileName)) {
                    stagedRem.put(fileName, trackedFiles.get(fileName));
                    i += 1;
                    temp.delete();
                    trackedFiles.remove(fileName);
                    Utils.writeObject(commitMetadata, trackedFiles);
                }
                Utils.writeObject(rmStaging, stagedRem);
            }
        }

        if (i == 0) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

    }

}
