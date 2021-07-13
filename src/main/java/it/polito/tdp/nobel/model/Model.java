package it.polito.tdp.nobel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	double mediaSoluzioneMigliore;
	List<Esame> partenza=new ArrayList<Esame>();
	Set<Esame> soluzioneMigliore;
	
	public Model() {
		EsameDAO e=new EsameDAO();
		this.partenza=e.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame> parziale=new HashSet<Esame>();
		soluzioneMigliore=new HashSet<Esame>();
		mediaSoluzioneMigliore=0;
		
//		cerca1(parziale, 0 , numeroCrediti);
		cerca2(parziale, 0 , numeroCrediti);
		
		return soluzioneMigliore;
	}
	
	private void cerca2(Set<Esame> parziale, int L, int m) {
		
		int crediti = sommaCrediti(parziale);
		
		if(crediti>m) {
			return;
		}
		if(crediti==m) {
			double media= calcolaMedia(parziale);
			if(media>mediaSoluzioneMigliore) {
				soluzioneMigliore=new HashSet<>(parziale);
				mediaSoluzioneMigliore=media;
			}
			return;
		}
		if(L==partenza.size()) {
			return;
		}
		
		parziale.add(partenza.get(L));
		cerca2(parziale,L+1,m);
		
		parziale.remove(partenza.get(L));
		cerca2(parziale,L+1,m);
}
	
	
	

	private void cerca1(Set<Esame> parziale, int L, int m) {
		
		int crediti = sommaCrediti(parziale);
		
		if(crediti>m) {
			return;
		}
		if(crediti==m) {
			double media= calcolaMedia(parziale);
			if(media>mediaSoluzioneMigliore) {
				soluzioneMigliore=new HashSet<>(parziale);
				mediaSoluzioneMigliore=media;
			}
			return;
		}
		if(L==partenza.size()) {
			return;
		}
		
		for(Esame e:partenza) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca1(parziale, L+1,m);
				parziale.remove(e);
			}
		}
	}

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
