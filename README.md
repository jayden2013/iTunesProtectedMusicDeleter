# iTunesProtectedMusicDeleter
Deletes all music files that have the specified apple ID associated with them.

# Limitations
Currently, only a single directory of files can be scanned and deleted at a time.

# Usage

### Windows:  
java -classpath .;jaudiotagger-2.2.6-SNAPSHOT.jar *.java

java -classpath .;jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter \<apple id\>

### Linux / Mac OS:
java -classpath .:jaudiotagger-2.2.6-SNAPSHOT.jar *.java

java -classpath .:jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter \<apple id\>