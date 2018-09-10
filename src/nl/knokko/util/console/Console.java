package nl.knokko.util.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public abstract class Console implements Runnable {
	
	private final PrintStream out;
	private final BufferedReader in;
	
	protected boolean stopping;
	
	public Console(){
		this(System.out, new BufferedReader(new InputStreamReader(System.in)));
	}
	
	public Console(PrintStream out, BufferedReader in){
		this.out = out;
		this.in = in;
	}
	
	@Override
	public void run() {
		try {
			println("Starting console");
			while(!stopping){
				String command = in.readLine();
				if(command != null)
					executeCommand(command.split(" "));
				else {
					println("End of console reached");
					stopping = true;
					stop();
					return;
				}
			}
		} catch(IOException ex){
			println("Console IO exception occured: " + ex.getMessage());
			stop();
		}
	}
	
	protected abstract void stop();
	
	protected abstract void executeCommand(String[] command);
	
	public PrintStream getWriter(){
		return out;
	}
	
	public void println(String message){
		if(!stopping)
			out.println(message);
	}
}