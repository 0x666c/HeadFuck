package headfuck;

import java.io.InputStream;
import java.io.OutputStream;

import headfuck.bf.BrainFuck;
import headfuck.lists.CellList;
import headfuck.lists.ShortList;
import headfuck.util.ErrorHelper;
import headfuck.util.ErrorHelper.Errors;
import headfuck.vars.Func;
import headfuck.vars.VarMap;
import headfuck.vars.Variable;

public class HeadFuck {
	
	public HeadFuck(boolean bfCompatible, String source) {
		src = source;
		program = tokenize(src);
		bf = bfCompatible;
	}
	
	
	public final String src;
	public final boolean bf;
	
	public  InputStream  attachedInput  = System.in;
	public  OutputStream attachedOutput = System.out;
	private InputStream  defaultInput   = System.in;
	private OutputStream defaultOutput  = System.out;
	
	public int cellPointer = 0;
	public int flowPointer = 0;
	public int loopPointer = 0;
	public char[] program;
	
	// CellListv2 cells = new RefShortList(10000);
	public CellList cells = new ShortList(10000);
	
	VarMap map = VarMap.instance(); {map.setRef(this);}
	
	public final void nextCharacter(char c) throws Exception {
		switch (c) {
		case '+': cells.inc(cellPointer); break;
		case '-': cells.dec(cellPointer); break;
		case '>': cellPointer++; break;
		case '<': cellPointer--; break;
		case '[': openBracket(); break;
		case ']': closingBracket(); break;
		case '*': cells.set(cellPointer, getch()); break;
		case '^': putch(cells.get(cellPointer)); break;
		
		case '.': cellPointer = 0; break;
		case '_': cells.set(cellPointer, 0); break;
		case '!': System.exit(cells.get(cellPointer)); break;
		
		case '@': at(); break;

		default:
			if(VarMap.isEnglish(c)) {
				variable(c).exec(currentCell());
			}
		}
	}
	
	private final void openBracket() {
		 if(cells.cmp(cellPointer, 0)) {
             flowPointer++;
             while(loopPointer > 0 || program[flowPointer] != ']') {
                 if(program[flowPointer] == '[') loopPointer++;
                 if(program[flowPointer] == ']') loopPointer--;
				flowPointer++;
			}
		}
	}
	
	private final void closingBracket() {
		if(!cells.cmp(cellPointer, 0)) {
            flowPointer--;
            while(loopPointer > 0 || program[flowPointer] != '[') {
                if(program[flowPointer] == ']') loopPointer++;
                if(program[flowPointer] == '[') loopPointer--;
                flowPointer--;
            }
			flowPointer--;
		}
	}
	
	private final void at() {
		char boundTo = program[++flowPointer];
		char boundWhat = program[++flowPointer];
		
		if(boundTo == '*') {
			if(boundWhat == '*') {
				attachedInput = defaultInput;
				return;
			} else if(VarMap.isEnglish(boundWhat)) {
				InputStream o = variable(boundWhat).atIn();
				if(o == null)
					return;
				attachedInput = o;
				return;
			}
		} else if(boundTo == '^') {
			if(boundWhat == '^') {
				attachedOutput = defaultOutput;
				return;
			} else if(VarMap.isEnglish(boundWhat)) {
				OutputStream o = variable(boundWhat).atOut();
				if(o == null)
					return;
				attachedOutput = o;
				return;
			}
		}
		ErrorHelper.error(new RuntimeException("Syntax error"), Errors.SYNTAX, this);
	}
	
	private final Variable variable(char var) {
		if(Variable.isStd(var)) {
			return Variable.getStd(var);
		}
		return map.get(var);
	}
	
	private final byte getch() {
		try {
			return (byte) attachedInput.read();
		} catch (Exception e) {
			ErrorHelper.error(e, Errors.IO, this);
			return 0; // Never reach this line since error() exits
		}
	}
	
	private final void putch(int b) {
		try {
			attachedOutput.write(b);
			attachedOutput.flush();
		} catch (Exception e) {
			ErrorHelper.error(e, Errors.IO, this);
		}
	}
	
	public final short currentCell() {
		return (short) cells.get(cellPointer);
	}
	
	
	private final char[] tokenize(String src) {
		return src.toCharArray();
	}
	
	private void preallocate() {
		int start = src.indexOf(':');
		int end = src.indexOf(';');
		if(start != -1 && end == -1)
			ErrorHelper.error(new RuntimeException("Unclosed :"), Errors.SYNTAX, this);
		if(start == -1 && end != -1)
			ErrorHelper.error(new RuntimeException("Dangling ;"), Errors.SYNTAX, this);
		if(start == -1 && end == -1)
			return;
		else {
			flowPointer = end + 1;
			
			char[] sec = src.substring(start, end).toCharArray();
			for (int i = 0; i < sec.length; i++) {
				char c = sec[i];
				
				if(c == '{') {
					int fstart = i+1;
					int fend = -1;
					for (int j = fstart; j < sec.length; j++) {
						if(sec[j] == '}')
							fend = j;
					}
					if(fend == -1)
						ErrorHelper.error(new RuntimeException("Dangling {"), Errors.SYNTAX, this);
					else {
						char label;
						if(!VarMap.isEnglish(label = sec[i-1]) || i-1 < 0) {
							ErrorHelper.error(new RuntimeException("Unlabeled func"), Errors.SYNTAX, this);
						} else {
							final String str = new String(sec, fstart, fend - fstart);
							map.put(label, new Func(str));
						}
					}
				}
			}
		}
	}
	
	private final void debug(int delay) {
		try {
			Thread.sleep(delay);
		} catch (Exception e) {}
		System.out.println("Execptr: "+flowPointer+" executing: '" + (flowPointer < 0 ? "undef" : program[flowPointer]) + "' Loopptr: " + loopPointer + " cellvalue: " + program[cellPointer] + " cellptr: "+ cellPointer);
	}
	
	public void run() throws Exception {
		if(bf) {
			BrainFuck.interpret(src);
			System.exit(0);
		} else {
			preallocate();
			
			//System.exit(0);
			
			while(flowPointer < program.length) {
				try {
					nextCharacter(program[flowPointer]);
					flowPointer++;
				} catch (Exception e) {
					ErrorHelper.error(e, Errors.OTHER, this);
				}
			}
		}
		
	}
}