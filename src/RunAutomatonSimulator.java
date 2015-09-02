
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author docentes labp
 *
 */
public class RunAutomatonSimulator {
	
	/**
	 * Run class 
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main (String args[]) throws IOException {
		BufferedReader leitor = 
				new BufferedReader (new InputStreamReader (System.in));
		System.out.print("Nome do ficheiro de entrada: ");
		String nomeFicheiro = leitor.readLine().trim();
		
		Scanner in = new Scanner(
			new BufferedReader(new FileReader(nomeFicheiro)));
		
		// Ler alfabeto 
		String[] a = in.nextLine().split(" "); 
		char[] b = new char[a.length-1];
		// ignorar palavra "Alfabeto" 
		for(int i = 1; i < a.length; i++){
			b[i-1] = a[i].charAt(0);
		}
		
		// Numero de estados
		int numEst = Integer.parseInt( in.nextLine().split(" ")[1] ); 
		
		// Estados finais (de aceitacao) 
		String[] c = in.nextLine().split(" "); 
		int[] d = new int[c.length-1];
		// ignorar palavra "Finais" 
		for(int i = 1; i < c.length; i++){
			d[i-1] = Integer.parseInt( c[i] );
		}
		
		Automaton auto = new Automaton(b, numEst);
		auto.setFinalStates(d);
		
		
		// Ignorar a palavra "Transicoes"
		in.nextLine();
		
		// Funcao de transicoes
		while(in.hasNextLine()){
			String[] delta = in.nextLine().split(" "); 
			for(int i = 0; i < delta.length; i++){
				// estado 
				int state = Integer.parseInt( delta[0] );
				// simbolo 
				char symbol = delta[2].charAt(1);
				// destinos 
				int[] targets = new int[delta.length-4];
				for(int k=4; k<delta.length; k++) 
					targets[k-4] = Integer.parseInt(delta[k]);
				auto.addTransitions(state, symbol, targets);
			}
		}
		
		in.close();
		
		//
		// Quando a escolha eh 1, pedir input e simular. 
		// 
		int choice = 1; 
		
		while( choice == 1 ){
			System.out.println("\nEscolha uma opcao: ");
			System.out.println("1. Simular automato com input. ");
			System.out.println("2. Sair. ");
			
			choice = Integer.parseInt( leitor.readLine().trim() );
			
			if(choice == 1) {
				System.out.print("\nIntroduza um input string: ");
				String input = leitor.readLine().trim();
				
				System.out.println();
				
				AutomatonSimulator autoSim = 
						new AutomatonSimulator( auto, input );
				
				System.out.println("\nO input \"" + input + 
						"\" eh " + (run(autoSim) ? "aceite." : "rejeitado.") );
			} 
		}
		
		System.out.println("\nBye!");
		leitor.close();
		
	}
	
	
	/*
	 * Executa o simulador 
	 * */
	private static boolean run( AutomatonSimulator as ){
		while ( as.step() ) ; 
		if( as.accepted() ) {
			System.out.println(as.getConfiguration());
			return true;
		}
		else 
			return false;
	}
}
