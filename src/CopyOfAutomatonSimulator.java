import java.util.Stack;

/**
 * 
 */

/**
 * @author antonio
 * 
 */
public class CopyOfAutomatonSimulator {
	private Stack<Configuration> stack;
	private Automaton auto;

	public CopyOfAutomatonSimulator(Automaton auto, String input) {
		stack = new Stack<Configuration>();
		stack.push(new Configuration(1, input, 0));
		this.auto = auto;
	}

	public boolean step() {
		if (!rejected() && !accepted()) {
			Configuration conf = getConfiguration();
			stack.pop();
			System.out.println(conf);
			
			if (conf.isInputEmpty()) {
				if (!stack.isEmpty()) {
					conf = getConfiguration();
					return true;
				}
			}
			if (!conf.isInputEmpty()) {
				int[] estados = auto.getTargets(conf.getState(),
						conf.getFirstSymbol());

				for (int i = estados.length - 1; i >= 0; i--) {
					stack.push(new Configuration(estados[i], conf
							.getRestOfInput(), conf.getProfundidade() + 1));
				}
				return true;
			}
		}
		return false;
	}

	public boolean accepted() {
		boolean mandFora = false;
		if (rejected()) {
			return false;
		}
		if (auto.isFinal(stack.peek().getState())
				&& stack.peek().isInputEmpty()) {
			mandFora = true;
		}
		return mandFora;
	}

	public boolean rejected() {
		return stack.isEmpty() ? true : false;
	}

	public Configuration getConfiguration() {
		return stack.peek();
	}

}
