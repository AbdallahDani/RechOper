public class Grph {
	
	private boolean matAdj[][];
	private int noeu ;
    
	//Constructor
	public Grph(int noeu) {
		this.noeu  = noeu;
		matAdj = new boolean[noeu][noeu];
	}

	// Les arrêtes entre deux noeu
	public void ajArrete(int i, int j) {
		matAdj[i-1][j-1] = true;
		matAdj[j-1][i-1] = true;
	}
	
	
	public boolean adjacent(int i, int j) {
		return matAdj[i][j];
	}

	// Affichage de La Matrice
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("    ");
		for(int i = 0; i < noeu; i++) {
			int k = i + 1;
			String ks = k +"";
			s.append(i < 9 ? "0" + ks : ks );
			s.append(" ");
		}
		s.append("\n");
		for (int i = 0; i < noeu ; i++) {
			if(i < 9) s.append("0");
			s.append(i+1 + ":  ");
			for (int j = 0; j < matAdj[i].length; j++) {
				boolean bool = matAdj[i][j];
				int b = bool ? 1 : 0;
				s.append(b+"  ");
			}
			s.append("\n");
		}
		return s.toString();
	}
}