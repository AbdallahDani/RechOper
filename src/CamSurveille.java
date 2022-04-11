import ilog.concert.*;
import ilog.cplex.*;

public class CamSurveille {
	
	
	public static void main(String[] args) {
		
		//Création du Grph et Matrice d'adjacence
		Grph G = new Grph(49);
		
		// Emplacements des cameras;
		G.ajArrete(1, 2);
		G.ajArrete(1, 3);
		G.ajArrete(2, 41);
		G.ajArrete(2, 39);
		G.ajArrete(3, 11);
		G.ajArrete(4, 5);
		G.ajArrete(4, 6);
		G.ajArrete(4, 9);
		G.ajArrete(6, 7);
		G.ajArrete(6, 8);
		G.ajArrete(9, 10);
		G.ajArrete(11, 21);
		G.ajArrete(12, 13);
		G.ajArrete(12, 15);
		G.ajArrete(13, 14);
		G.ajArrete(14, 15);
		G.ajArrete(14, 18);
		G.ajArrete(15, 19);
		G.ajArrete(16, 20);
		G.ajArrete(17, 18);
		G.ajArrete(18, 19);
		G.ajArrete(19, 20);
		G.ajArrete(20, 21);
		G.ajArrete(21, 22);
		G.ajArrete(22, 25);
		G.ajArrete(22, 23);
		G.ajArrete(23, 32);
		G.ajArrete(24, 25);
		G.ajArrete(25, 26);
		G.ajArrete(25, 30);
		G.ajArrete(26, 27);
		G.ajArrete(26, 28);
		G.ajArrete(28, 29);
		G.ajArrete(30, 31);
		G.ajArrete(31, 32);
		G.ajArrete(31, 33);
		G.ajArrete(32, 38);
		G.ajArrete(33, 34);
		G.ajArrete(33, 37);
		G.ajArrete(34, 35);
		G.ajArrete(35, 36);
		G.ajArrete(37, 43);
		G.ajArrete(37, 38);
		G.ajArrete(38, 40);
		G.ajArrete(39, 40);
		G.ajArrete(40, 41);
		G.ajArrete(41, 42);
		G.ajArrete(43, 44);
		G.ajArrete(44, 49);
		G.ajArrete(44, 45);
		G.ajArrete(45, 47);
		G.ajArrete(46, 46); // Remarque: Cela doit avoir une caméra 
		G.ajArrete(47, 48);
	
		
		calcul(G);
	}
	public static void calcul (Grph adj){
		try {
			int n = 49;
			IloCplex simplexe = new IloCplex ();
			
			// Var décision 2ème contrainte
			IloNumVar[] V = new IloNumVar[n];
			for (int i=0;i<n;i++){
				V[i]= simplexe.boolVar();
			}
			
			
			// La fonction obj
			IloLinearNumExpr F = simplexe.linearNumExpr();
			
			for (int i=0;i<n;i++){
				F.addTerm(1, V[i]);
			}
			
			
			// minimization
			simplexe.addMinimize(F);
			
			
			// les contraintes Xi + Xj >= 1
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					if(adj.adjacent(i, j)) {
						IloLinearNumExpr contrainte = simplexe.linearNumExpr();
						contrainte.addTerm(1, V[i]);
						contrainte.addTerm(1, V[j]);
						simplexe.addGe(contrainte, 1);						
					}
				}
			}
			
			simplexe.solve(); 
			
			// Affichage final
			System.out.println("\nLe Nombre des caméras sera placé : "+ simplexe.getObjValue() );
			System.out.println("les " + simplexe.getObjValue() + " Place qui sera surveiller : ") ;
			for (int i=0;i<n;i++) {
				if(simplexe.getValue(V[i]) != 0.0) {
					int k = i + 1;
					System.out.print("La Place "+k+" /");
				}				
			}
		} catch (IloException e){
			System.out.print("Exception" + e);
		}
	}
}