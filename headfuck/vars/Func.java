package headfuck.vars;

import java.io.InputStream;
import java.io.OutputStream;

import headfuck.HeadFuck;
import headfuck.util.ErrorHelper;
import headfuck.util.ErrorHelper.Errors;

public class Func extends Variable {
	
	public Func(String content) {
		super(content);
	}

	public short exec(short arg) {
		char[] instr = content.toCharArray();
		HeadFuck ref = VarMap.instance().getRef();
		for (int i = 0; i < instr.length; i++) {
			try {
				ref.nextCharacter(instr[i]);
			} catch (Exception e) {
				ErrorHelper.error(e, Errors.OTHER, ref);
			}
		}
		return arg;
	}

	public InputStream atIn() {
		return null;
	}

	public OutputStream atOut() {
		return null;
	}
}
