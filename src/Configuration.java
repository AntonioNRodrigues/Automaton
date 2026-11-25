/**
 * One configuration (state + remaining input + recursion depth) of the
 * automaton simulation.
 *
 * @author antonio
 */
public class Configuration implements Cloneable {
	private int estado;
	private String restoDoInput;
	private int profundidade;

	public Configuration(int estado, String input, int profundidade) {
		this.estado = estado;
		this.restoDoInput = input;
		this.profundidade = profundidade;
	}

	public int getState() {
		return estado;
	}

	public boolean isInputEmpty() {
		return restoDoInput == null || restoDoInput.isEmpty();
	}

	public char getFirstSymbol() {
		return restoDoInput.charAt(0);
	}

	public String getRestOfInput() {
		return restoDoInput.substring(1);
	}

	public int getProfundidade() {
		return profundidade;
	}

	@Override
	public Configuration clone() {
		return new Configuration(estado, restoDoInput, profundidade);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < profundidade; i++) {
			sb.append('\t');
		}
		return sb.append('(').append(estado).append(", \"").append(restoDoInput)
				.append("\")").toString();
	}
}
