package train;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Just a little class I wrote to be able to efficiently generate and label some voice data to send to Azure. 
 * It shouldn't be used in production, hence its deprecation.
 * @author kevin
 *
 */
@Deprecated
public class DataManager {
	
	public static void generateTransFile (String sortedDirectory, String allDirectory, String utterancesFile) throws IOException {
		
		BufferedReader br = new BufferedReader (new FileReader(utterancesFile));
		StringBuilder contents = new StringBuilder();
		
		String line;
		while ((line = br.readLine())!=null) { //Looping through all the directories via utterances
			String dirName = line;
			String utteranceName = line.split(",", 2)[1].trim();
			File folder = new File(sortedDirectory, dirName);
			File [] files = folder.listFiles();
			for (File f: files) { //Looping thru all the audio files
				File target = new File(allDirectory + "/" + dirName.split(",")[0] + "-" + f.getName());
				Files.copy(f.toPath(), target.toPath());
				contents.append(target.getName() + " " + utteranceName + "\n");
			}
		}
		
		File trans = new File("train/trans.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(trans));
        output.write(contents.toString());

        output.close();
		br.close();
	}
	
	public static void generateDirectories (String directory, String utterancesFile) throws IOException {
		
		BufferedReader br = new BufferedReader (new FileReader(utterancesFile));
		
		String line;
		while ((line = br.readLine())!=null) {
			String dirName = line;
		     File file = new File(directory, dirName);
		     if (file.mkdir()) {
		    	 System.out.println("Made directory");
		     }

		}
		br.close();
		
		
	}

}
