package headfuck.vars;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class JFunc extends Variable {

	public JFunc(String content) {
		super(null);
	}
	
	public JFunc() {
		super(null);
	}

	public void set(String var) {
		return;
	}

	public short exec(short arg) {
		return jexec(arg);
	}
	
	protected abstract short jexec(short arg);

	public InputStream atIn() {return null;}
	public OutputStream atOut() {return null;}

}
