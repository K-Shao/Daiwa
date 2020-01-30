package ui;

import java.util.concurrent.ExecutionException;

import io.BonxHeader;
import io.Parser;
import production.Synthesizer;

public class Actor implements Runnable{
	
	private String speech;
	private BonxHeader header;
	private HomeScreen hs;
	
	public Actor (String speech, BonxHeader header, HomeScreen hs) {
		this.speech = speech;
		this.header = header;
		this.hs = hs;
	}

	@Override
	public void run() {
		try {
			Synthesizer.speak(Parser.interpret(speech, header), Sys.getInstance().getConfiguration());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Repainting");
		hs.repaintAll();
	}

}
