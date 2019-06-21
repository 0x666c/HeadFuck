package headfuck.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import headfuck.HeadFuck;

public class ErrorHelper {
	
	private static final boolean debugMode = true;
	
	public static enum Errors {
		SYNTAX,
		IO,
		OTHER;
	}
	
	private static final int ERROR_OFFSET_LEFT  = 12;
	private static final int ERROR_OFFSET_RIGHT = 12;
	
	public static final void error(Throwable e, Errors err, HeadFuck runtime) { // Exit
		System.err.println(generateMessage(e, err, runtime, "~FATAL ERROR~ ", true, true));
		if(debugMode) {
			System.err.flush();
			e.printStackTrace();
		}
		System.exit(runtime.flowPointer);
	}
	
	public static final void warn(Throwable e, Errors err, HeadFuck runtime) { // Do not exit
		if(debugMode) {
			System.err.flush();
			e.printStackTrace();
		}
		System.err.println(generateMessage(e, err, runtime, "~WARNING~ ", false, false));
	}
	
	
	private static final String generateMessage(Throwable e, Errors err, HeadFuck runtime, final String title, boolean showArrow, boolean doDump) {
		final String src = runtime.src;
		
		String left  = src.substring(0, runtime.flowPointer);
		String right = src.substring(runtime.flowPointer, src.length());
		
		if(left.length() > ERROR_OFFSET_LEFT) {
			left = left.substring(left.length() - ERROR_OFFSET_LEFT, left.length());
		}
		
		if(right.length() > ERROR_OFFSET_RIGHT) {
			right = right.substring(0, ERROR_OFFSET_RIGHT);
		}
		
		int errAtChar = left.length();
		final String arrow = Stream.generate(() -> "~").limit(errAtChar).collect(Collectors.joining()) + "^ here";
		
		final String msg = 
				  title + err + " error" + (e.getMessage() == null ? "" : ": "+ e.getClass().getSimpleName() + ": " + e.getMessage()) + "\n"
				+ "character '"+runtime.program[runtime.flowPointer]+"' at "+runtime.flowPointer+".\n\n"
				+ (showArrow ? left + right + "\n" : "")
				+ (showArrow ? arrow + "\n\n" : "")
				+ (doDump ? dump(runtime) : "");
		
		return msg;
	}
	
	private static final String dump(HeadFuck runtime) {
		final String dump =
				
				  "flowPointer: " + runtime.flowPointer + "\n"
				+ "cellPointer: " + runtime.cellPointer + "\n"
				+ "loopPointer: " + runtime.loopPointer + "\n"
				+ "current cell: " + runtime.cells.get(Math.max(0, runtime.cellPointer)) + "\n"
				+ "in : " + runtime.attachedInput + "\n"
				+ "out: " + runtime.attachedOutput + "\n";
		
		return dump;
	}

}
