package algorithmforsocialmedia;

import java.util.LinkedList;

public class NeighbourArtistsPerDegree {
	
	private int degreeOfSeparation;
	
	private int size;
		
	private LinkedList<Edge> [] neighbourArtists;
	
	public NeighbourArtistsPerDegree(int size)
	{
		this.size = size;
    	
    	this.neighbourArtists = new LinkedList[this.size];
		
	
	}
	
	public void initializeNeighbourArtists()
	{

		for(int i =0; i<this.size; i++)
    	{
			neighbourArtists[i] = new LinkedList<>();
    	}
    
	}
	
	public void setNeighbourArtists(int index, LinkedList<Edge> edge)
	{
		this.neighbourArtists[index] = edge;
	}

	public int getDegreeOfSeparation() {
		return degreeOfSeparation;
	}

	public void setDegreeOfSeparation(int degreeOfSeparation) {
		this.degreeOfSeparation = degreeOfSeparation;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public LinkedList<Edge>[] getNeighbourArtists() {
		return neighbourArtists;
	}

	public void setNeighbourArtists(LinkedList<Edge>[] neighbourArtists) {
		this.neighbourArtists = neighbourArtists;
	}
	
	


}
