package headfuck.vars;

import headfuck.HeadFuck;
import headfuck.util.ErrorHelper;
import headfuck.util.ErrorHelper.Errors;

public class VarMap {
	
	private static final VarMap INSTANCE = new VarMap();
	
	private HeadFuck mainRef;
	
	public VarMap() {}
	
	public void setRef(HeadFuck ref) {
		mainRef = ref;
	}
	
	public HeadFuck getRef() {
		return mainRef;
	}
	
	public static final VarMap instance() {
		return INSTANCE;
	}
	
	private Variable[] variables = new Variable['z' - 'a'];

	public int size() {
		return variables.length;
	}

	public boolean varExists(char letter) {
		if(isEnglish(letter)) {
			return variables[letter - 'a'] != null;
		}
		return false;
	}
	
	public Variable get(char letter) {
		if(isEnglish(letter)) {
			return variables[letter - 'a'];
		} else {
			ErrorHelper.error(new RuntimeException("character '" + letter + " is invalid"), Errors.OTHER, mainRef);
			return null; // error() exits
		}
	}

	public void put(char letter, Variable value) {
		if(isEnglish(letter)) {
			variables[letter - 'a'] = value;
		} else
		ErrorHelper.error(new RuntimeException("character '" + letter + "' is invalid"), Errors.OTHER, mainRef);
	}
	
	
	public static final boolean isEnglish(char letter) {
		return (letter >= 'a' && letter <= 'z');
	}
}
