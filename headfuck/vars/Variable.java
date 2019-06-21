package headfuck.vars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import headfuck.HeadFuck;
import headfuck.util.ErrorHelper;
import headfuck.util.ErrorHelper.Errors;

public abstract class Variable {
	
	protected String content;
	
	public Variable(String content) {
		set(content);
	}
	
	public void set(String content) {
		this.content = content;
	}
	
	public abstract short exec(short arg);
	public abstract InputStream atIn();
	public abstract OutputStream atOut();
	
	
	// STATIC //
	
	
	private static final JFunc m = new JFunc() {
		public short jexec(short arg) {
			JOptionPane.showMessageDialog(null, arg);
			return arg;
		}
	};
	
	private static final JFunc f = new JFunc() {
		public short jexec(short arg) {
			try {
				new File("c:/users/user/desktop/test/" + arg).createNewFile();
				return arg;
			} catch (IOException e) {
				ErrorHelper.warn(e, Errors.IO, VarMap.instance().getRef());
				return arg;
			}
		}
		public InputStream atIn() {
			try {
				File f = new File("c:/users/user/desktop/test/" + VarMap.instance().getRef().currentCell());
				if(!f.exists())
					return null;
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
				return Channels.newInputStream(raf.getChannel());
			} catch (Exception e) {
				ErrorHelper.warn(e, Errors.IO, VarMap.instance().getRef());
				return null;
			}
		}
		public OutputStream atOut() {
			try {
				File f = new File("c:/users/user/desktop/test/" + VarMap.instance().getRef().currentCell());
				if(!f.exists())
					return null;
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				OutputStream out = Channels.newOutputStream(raf.getChannel());
				return out;
			} catch (Exception e) {
				ErrorHelper.warn(e, Errors.IO, VarMap.instance().getRef());
				return null;
			}
		}
	};
	
	
	public static final boolean isStd(char val) {
		switch (val) {case'f':case'm': 
			return true;}
		return false;
	}
	
	public static final Variable getStd(char var) {
		switch (var) {
		case 'm':
			return m;
		case 'f':
			return f;
		}
		
		return null;
	}
}