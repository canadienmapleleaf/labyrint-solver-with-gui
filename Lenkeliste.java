import java.util.Iterator;

class Lenkeliste<T> implements Liste<T> {

	protected int counterNode = 0;

	Node start = null;
	Node slutt = null;

	class Node {
		Node neste = null;
		Node forrige = null;
		T data;

		Node(T x) {data = x;}
	}

	public Iterator iterator() {
			return new LenkelisteIterator(this);
	}

	public int stoerrelse() {
		return counterNode;
	}

	class LenkelisteIterator implements Iterator<T> {
			Liste<T> minListe;
			int indeks = 0;

			public LenkelisteIterator(Liste<T> list) {
					minListe = list;
			}

			@Override
			public boolean hasNext() {
					return indeks < minListe.stoerrelse();
			}

			@Override
			public T next() {
					return minListe.hent(indeks++);
			}

			@Override
			public void remove(){}
	}

	/*
	Metoden leggTil(int pos, T x) skal
	legge inn et nytt element i listen og skyve
	neste element ett hakk lenger bak
	**/

	public void leggTil(int pos, T x) {
		if(counterNode<pos || pos<0) {
			throw new UgyldigListeIndeks(pos);
		}
		else if(counterNode==pos) {
			leggTil(x);
		}
		else if(pos==0) {
			Node m = new Node(x);
			m.neste = start;
			start.forrige = m;
			start = m;
			counterNode++;
		}
		else if((counterNode-1)==pos && counterNode>1) {
			Node m = new Node(x);
			Node t = slutt.forrige;
			t.neste = m;
			m.forrige = t;
			m.neste = slutt;
			slutt.forrige = m;
			counterNode++;
		}
		 else {
				Node m = new Node(x);
				Node n = hentNode(pos);
				Node t = n.forrige;
				m.neste = n;
				m.forrige = t;
				n.forrige = m;
				t.neste = m;
				counterNode++;
		}
	}

	/*
	Metoden leggTil(T x) skal altså
	sette inn et element på slutten av listen
	**/

	public void leggTil(T x) {
		if(counterNode==0) {
			Node m = new Node(x);
			start = m;
			slutt = m;
		}
		 else {
			Node m = new Node(x);
			slutt.neste = m;
			m.forrige = slutt;
			slutt = m;
		}
		counterNode++;
	}

	public void sett(int pos, T x) {
		if(counterNode<=pos || pos<0) {
			throw new UgyldigListeIndeks(pos);
		}
		else if(pos==0) {
			if(counterNode==1) {
				Node m = new Node(x);
				start = m;
				slutt = m;
			} else {
				Node m = new Node(x);
				Node n = start.neste;
				m.neste = n;
				n.forrige = m;
				start = m;
			}
		}
		else if ((counterNode-1)==pos) {
			Node m = new Node(x);
			Node n = slutt.forrige;
			m.forrige = n;
			n.neste = m;
			slutt = m;
		} else {
			Node m = new Node(x);
			Node n = hentNode(pos);
			Node pre = n. forrige;
			Node nxt = n.neste;
			m.neste = nxt;
			m.forrige = pre;
			pre.neste = m;
			nxt.forrige = m;
		}
}

public Node hentNode(int pos) {
	if(pos>=counterNode || pos<0) {
		 throw new UgyldigListeIndeks(pos);
	} else {
			try{
				Node p = start;
				for(int i=0; i<pos; i++) {
					p = p.neste;
				}
				return p;
			} catch(Exception e) {
					System.out.println("noe galt med hentNode-metode");
					return null;
			}
		}
}




	public T hent(int pos) {
		if(pos>=counterNode || pos<0) {
			 throw new UgyldigListeIndeks(pos);
		} else {
				try{
					Node p = start;
					for(int i=0; i<pos; i++) {
						p = p.neste;
					}
					return p.data;
				} catch(Exception e) {
						System.out.println("noe galt med hent-metode");
						return null;
				}
			}
	}

	/*
	Metoden fjern(int pos) skal fjerne
	på gitt indeks i listen
	**/

	public T fjern(int pos) {
		if(counterNode==0 || pos<0 || counterNode<=pos) {
			throw new UgyldigListeIndeks(pos);
		}
		else if(pos==0) {
			if(counterNode==1) {
				Node m = start;
				start = null;
				slutt = null;
				counterNode--;
				return m.data;
			} else {
					Node m = start;
					Node n = start.neste;
					n.forrige = null;
					start = n;
					counterNode--;
					return m.data;
			}
		} else if(counterNode-1==pos) {
				Node m = slutt;
				Node n = slutt.forrige;
				n.neste = null;
				slutt = n;
				counterNode--;
				return m.data;
			} else {
					Node m = hentNode(pos);
					Node pre = m.forrige;
					Node nxt = m.neste;
					pre.neste = nxt;
					nxt.forrige = pre;
					counterNode--;
					return m.data;
		}
	}

	public T fjern() {
		if(start != null) {
			if(start.neste!=null) {
				Node m = start;
				Node n = start.neste;
				n.forrige = null;
				start = n;
				m.neste = null;
				counterNode--;
				return m.data;
			} else {
					Node n = start;
					start = null;
					slutt = null;
					counterNode--;
					return n.data;
				}
		} else {throw new UgyldigListeIndeks(-1);}
	}
}
