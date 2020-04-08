# iTunesProtectedMusicDeleter
Deletes all music files that have the specified apple ID associated with them.

# Usage

Specify the apple id to search for. Optionally, specify a directory to search. Otherwise, the default directory is the current working directory.

### Windows:  
javac -classpath .;jaudiotagger-2.2.6-SNAPSHOT.jar *.java

java -classpath .;jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter \<apple id\> [directory]

### Linux / Mac OS:
javac -classpath .:jaudiotagger-2.2.6-SNAPSHOT.jar *.java

java -classpath .:jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter \<apple id\> [directory]