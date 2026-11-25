import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Non-deterministic finite automaton model.
 *
 * States are numbered from 1 to the state count. Invalid states or symbols are
 * ignored by the mutation methods, preserving the behaviour of the original
 * compiled classes that shipped in the classes/ directory.
 *
 * @author antonio
 */
public class Automaton {
	private Set<Character> alfabeto;
	private int numEstados;
	private Set<Integer> estadosFinais;
	private List<Map<Character, Set<Integer>>> transicoes;

	public Automaton(char[] alfabeto, int numEstados) {
		this.alfabeto = new HashSet<Character>();
		if (alfabeto != null) {
			for (char symbol : alfabeto) {
				this.alfabeto.add(Character.valueOf(symbol));
			}
		}

		this.numEstados = numEstados;
		this.estadosFinais = new HashSet<Integer>();
		this.transicoes = new ArrayList<Map<Character, Set<Integer>>>();
		for (int i = 0; i < numEstados; i++) {
			this.transicoes.add(new HashMap<Character, Set<Integer>>());
		}
	}

	public void setFinalStates(int[] estados) {
		if (estados == null) {
			return;
		}
		for (int estado : estados) {
			if (isValidState(estado)) {
				estadosFinais.add(Integer.valueOf(estado));
			}
		}
	}

	public void addTransitions(int estado, char symbol, int[] targets) {
		if (!isValidState(estado) || !isValidSymbol(symbol)) {
			return;
		}

		Map<Character, Set<Integer>> mapa = transicoes.get(estado - 1);
		Set<Integer> destinos = mapa.get(Character.valueOf(symbol));
		if (destinos == null) {
			destinos = new HashSet<Integer>();
			mapa.put(Character.valueOf(symbol), destinos);
		}

		if (targets != null) {
			for (int target : targets) {
				if (isValidState(target)) {
					destinos.add(Integer.valueOf(target));
				}
			}
		}
	}

	public int[] getTargets(int state, char symbol) {
		if (!isValidState(state) || !isValidSymbol(symbol)) {
			return new int[0];
		}

		Set<Integer> destinos = transicoes.get(state - 1).get(Character.valueOf(symbol));
		if (destinos == null || destinos.isEmpty()) {
			return new int[0];
		}

		int[] targets = new int[destinos.size()];
		int i = 0;
		for (Integer destino : destinos) {
			targets[i++] = destino.intValue();
		}
		return targets;
	}

	public boolean isFinal(int state) {
		return estadosFinais.contains(Integer.valueOf(state));
	}

	private boolean isValidState(int state) {
		return state >= 1 && state <= numEstados;
	}

	private boolean isValidSymbol(char symbol) {
		return alfabeto.contains(Character.valueOf(symbol));
	}
}
