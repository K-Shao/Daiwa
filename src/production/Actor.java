package production;

public class Actor {
	
	public void handle (String text) {
		System.out.println("I think you said: " + text);
		System.out.println("I think this means: " + Interpreter.interpret(text));
	}
	
	public Actor () {
		
	}

}
