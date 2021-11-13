# Gitlet Design Document

**Name**: Caedi Seim

## Classes and Data Structures
### Branch:
Basic functionality of a branch.
Keeps a pointer to the head and other blobs in the branch.
Using a hashset/map object to keep track of the data

###Blob:
Basic functionality of a file
Can read the text/things in file

###Commit:
In branch or extends it,
Updates branch everytime called
--This will be serializable

###Init
Creates a new working directory if there isn’t already one there. Adds the blobs.

###Merge:
Merge different branches into one branch.

###Check:
Checks for failures in the directory, deletes any untracked files,
clears the staging area. Checks for legality of commits (if the commit with id exists, etc).

## Algorithms
###Blob
Will implement serializable. Requires a file name, byte code, and the message.

###Init
Checks if the directory exits, if not creates directories for the branches and blobs and commits.

###Commit 
Creates a commit line to string. Initializes a new branch with the current head or if there is no head, make the current 
commit the head. 

###Merge
Sort through contents in each branch and merge the non-overlapping onto the branch to merge into using 
Merge sort. Implements all the rules of merging a two documents as presented in the video.


## Persistence
The following classes should be serializable therefore their results can be saved: commit,
blob, and, branch. Each time a change is made to a blob, the commit will have to create a new file
with the same blob file name, but with a different commit id. Therefore this commit will point to an
entirely different blob than the one previously committed.
