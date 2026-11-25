import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Command line runner for the automaton simulator.
 *
 * Usage: RunAutomatonSimulator [automaton-file]
 * When no argument is given, the file name is requested interactively.
 *
 * @author docentes labp
 */
public class RunAutomatonSimulator {

	public static void main(String[] args) throws IOException {
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));

		String nomeFicheiro;
		if (args.length > 0) {
			nomeFicheiro = args[0];
		} else {
			System.out.print("Enter the input file name: ");
			nomeFicheiro = leitor.readLine().trim();
		}

		Automaton auto = lerAutomato(nomeFicheiro);

		int choice = 1;
		while (choice == 1) {
			System.out.println("\nChoose an option: ");
			System.out.println("1. Simulate automaton with input. ");
			System.out.println("2. Exit. ");

			String linha = leitor.readLine();
			if (linha == null) {
				break;
			}
			try {
				choice = Integer.parseInt(linha.trim());
			} catch (NumberFormatException e) {
				System.out.println("Invalid option: " + linha);
				continue;
			}

			if (choice == 1) {
				System.out.print("\nEnter an input string: ");
				String input = leitor.readLine().trim();

				AutomatonSimulator autoSim = new AutomatonSimulator(auto, input);
				System.out.println("\nThe input \"" + input + "\" is "
						+ (run(autoSim) ? "accepted." : "rejected."));
			}
		}

		System.out.println("\nBye!");
	}

	private static Automaton lerAutomato(String nomeFicheiro) throws IOException {
		try (Scanner in = new Scanner(new BufferedReader(new FileReader(nomeFicheiro)))) {
			String[] a = in.nextLine().trim().split("\\s+");
			char[] alfabeto = new char[a.length - 1];
			for (int i = 1; i < a.length; i++) {
				alfabeto[i - 1] = a[i].charAt(0);
			}

			int numEst = Integer.parseInt(in.nextLine().trim().split("\\s+")[1]);

			String[] c = in.nextLine().trim().split("\\s+");
			int[] finais = new int[c.length - 1];
			for (int i = 1; i < c.length; i++) {
				finais[i - 1] = Integer.parseInt(c[i]);
			}

			Automaton auto = new Automaton(alfabeto, numEst);
			auto.setFinalStates(finais);

			in.nextLine(); // "Transitions"

			while (in.hasNextLine()) {
				String linha = in.nextLine().trim();
				if (linha.isEmpty()) {
					continue;
				}
				String[] delta = linha.split("\\s+");
				int state = Integer.parseInt(delta[0]);
				char symbol = delta[2].charAt(1);
				int[] targets = new int[delta.length - 4];
				for (int k = 4; k < delta.length; k++) {
					targets[k - 4] = Integer.parseInt(delta[k]);
				}
				auto.addTransitions(state, symbol, targets);
			}

			return auto;
		}
	}

	private static boolean run(AutomatonSimulator as) {
		while (as.step()) {
			// advance until the simulation is finished
		}
		if (as.accepted()) {
			System.out.println(as.getConfiguration());
			return true;
		}
		return false;
	}
}
