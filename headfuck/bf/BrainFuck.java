package headfuck.bf;

public class BrainFuck {

	public static final void interpret(String code) {
		try {
			char[] prog = code.toCharArray();
			int err = 0;
			for (int i = 0; i < prog.length; i++) {
				char c = prog[i];
				if (c == '[')
					err++;
				else if (c == ']')
					err--;
			}
			if (err > 0)
				System.err.println("error: dangling [");
			else if (err < 0)
				System.err.println("error: dangling ]");
			else {
				int ptr = 0;
				int lptr = 0;
				byte[] cells = new byte[10000];
				for (int i = 0; i < prog.length; i++)
				{
					switch (prog[i]) {
					case '+':
						cells[ptr]++;
						break;
					case '-':
						cells[ptr]--;
						break;
					case '>':
						ptr++;
						break;
					case '<':
						ptr--;
						break;
					case '[':
						if (cells[ptr] == 0) {
							i++;
							while (lptr > 0 || prog[i] != ']') {
								if (prog[i] == '[')
									lptr++;
								if (prog[i] == ']')
									lptr--;
								i++;
							}
						}
						break;
					case ']':
						if (cells[ptr] != 0) {
							i--;
							while (lptr > 0 || prog[i] != '[') {
								if (prog[i] == ']')
									lptr++;
								if (prog[i] == '[')
									lptr--;
								i--;
							}
							i--;
						}
						break;
					case '.':
						System.out.print((char) cells[ptr]);
						break;
					case ',':
						cells[ptr] = (byte) System.in.read();
						break;

					default:
						break;

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
