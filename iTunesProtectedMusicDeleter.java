import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		if (args.length < 1 || args.length > 2) {
			System.err.println(usage());
			System.exit(0);
		}
		File directory;
		if (args.length == 2) {
			directory = new File(args[1]);
		} else {
			directory = new File(System.getProperty("user.dir"));
		}
		ArrayList<File> fileArrayList = getFiles(directory);
		ArrayList<File> protectedFiles = new ArrayList<File>();
		for (File file : fileArrayList) {
			try {
				AudioFile audioFile = AudioFileIO.read(file);
				System.out.println("Checking " + file);
				if (audioFile.getTag().getFirst("apID").contains(args[0])) {
					protectedFiles.add(file);
				}
			} catch (Exception e) {
			}
		}
		if (!protectedFiles.isEmpty()) {
			printFiles(protectedFiles);
			writeFiles(protectedFiles);
			System.out.println("Press enter to ***DELETE*** these files");
			waitForEnter();
			deleteFiles(protectedFiles);
		} else {
			System.out.println("Unable to locate music with apple ID: " + args[0]);
			waitForEnter();
		}
	}

	/**
	 * Writes the file list to a log file
	 * 
	 * @param fileList
	 */
	private static void writeFiles(ArrayList<File> fileList) {
		String logDirectory = System.getProperty("user.dir");
		String logFile = logDirectory + "\\audio_log.txt";
		StringBuffer toWrite = new StringBuffer();
		for (File file : fileList) {
			toWrite.append(file);
			toWrite.append("\n");
		}
		try {
			Files.writeString(Paths.get(logFile), toWrite);
			System.out.println("Saved a list of matching audio files in " + logFile);
		} catch (IOException e) {
			System.err.println("Failed to write log.");
		}

	}

	/**
	 * Waits for user to press enter
	 */
	private static void waitForEnter() {
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}

	/**
	 * Populates an ArrayList with all files in the directory and subdirectories
	 * 
	 * @param directory
	 * @return
	 */
	private static ArrayList<File> getFiles(File directory) {
		System.out.println("Searching directory for audio files: " + directory);
		ArrayList<File> files = new ArrayList<File>();
		File[] directoryList = directory.listFiles();
		for (File file : directoryList) {
			if (file.isDirectory()) {
				files.addAll(getFiles(file));
			} else {
				files.add(file);
			}
		}
		return files;
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
			count++;
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
			return "java -classpath .;jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter <apple id> [directory]";
		} else {
			return "java -classpath .:jaudiotagger-2.2.6-SNAPSHOT.jar iTunesProtectedMusicDeleter <apple id> [directory]";
		}
	}
}