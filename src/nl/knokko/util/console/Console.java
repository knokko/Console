/* 
 * The MIT License
 *
 * Copyright 2018 20182191.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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