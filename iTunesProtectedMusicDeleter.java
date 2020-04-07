import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

/**
 * iTunes Protected Music Deleter
 * 
 * @author Jayden Weaver
 *
 */
public class iTunesProtectedMusicDeleter {

	public static void main(String args[]) {
		if (args.length < 1) {
			System.err.println(usage());
			System.exit(0);
		}
		File directory = new File(System.getProperty("user.dir"));
		File[] files = directory.listFiles();
		ArrayList<File> protectedFiles = new ArrayList<File>();
		for (File file : files) {
			try {
				AudioFile audioFile = AudioFileIO.read(file);
				System.out.println(file);
				if (audioFile.getTag().getFirst("apID").contains(args[0])) {
					protectedFiles.add(file);
				}
			} catch (Exception e) {
			}
		}
		printFiles(protectedFiles);
		System.out.println("Press enter to ***DELETE*** these files");
		try {
			System.in.read();
		} catch (IOException e) {
		}
		deleteFiles(protectedFiles);
	}

	/**
	 * Prints the file paths passed to it
	 * 
	 * @param fileList
	 */
	private static void printFiles(ArrayList<File> fileList) {
		System.out.println("Found the following protected files (" + fileList.size() + "):");
		for (File file : fileList) {
			System.out.println(file);
		}
	}

	/**
	 * Deletes the files that are passed to it
	 * 
	 * @param fileList
	 */
	private static void deleteFiles(ArrayList<File> fileList) {
		System.out.println("Deleting " + fileList.size() + " music files.");
		int count = 1;
		for (File file : fileList) {
			System.out.println("Deleting file " + count + " of " + fileList.size());
			file.delete();
		}
		System.out.println("ALL FILES DELETED!");
	}

	/**
	 * Prints usage
	 * 
	 * @return
	 */
	private static String usage() {
		if (System.getProperty("os.name").contains("Windows")) {
			return "java -classpath .;jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter";
		} else {
			return "java -classpath .:jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter";
		}
	}
}