package production;
import java.io.File;
import java.io.IOException;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class FileHandler {

	
	public static File convertMP3toWAV(File mp3, File target){
		try {
			target.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Converter converter = new Converter();
		try {
			converter.convert(mp3.getAbsolutePath(), target.getAbsolutePath());
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
		return target;
	}
	

}
