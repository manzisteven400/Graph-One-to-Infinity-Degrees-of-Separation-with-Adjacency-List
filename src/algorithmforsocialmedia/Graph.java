package algorithmforsocialmedia;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {
	
	private LinkedList<Edge> [] adjacentList;
	
	public int vertices;
	
	private Map <String,String> artistTrack = new HashMap<String, String>();
	
	private Map <Integer,NeighbourArtistsPerDegree> neighbourArtists = new HashMap<Integer,NeighbourArtistsPerDegree>();
	
	private Map <String,Integer> adjacentListIndex = new HashMap<String,Integer>();


	
    public Graph(int vertices)
	{
    	this.vertices = vertices;
    	
    	this.adjacentList = new LinkedList[this.vertices];
		
	}
    
    public void initializeGraph( )
    {
    	for(int i =0; i<this.vertices; i++)
    	{
    		adjacentList[i] = new LinkedList<>();
    	}
    }
    
    public void populateGraph(String artistsFilePath)
    {
		  java.io.BufferedReader br = null;
		  
		  int fileLines = 0;
		  
		  try
		  {

			  br = new java.io.BufferedReader(new java.io.FileReader(artistsFilePath));

			  
			  String line = br.readLine();
			  
			  while(line != null)
			  {
				  
				  String [] splittedLine = line.split(",");
				  
				  Edge edge = new Edge();
				  edge.setArtist(splittedLine[2].trim());
				  edge.setMovie(splittedLine[0].trim());
				  if(isArtistInTheMap(adjacentListIndex, splittedLine[1].trim()))
				  {
					  insertEdge(adjacentListIndex.get(splittedLine[1].trim()), edge);
				  }
				  else
				  {
					  fileLines ++;

					  adjacentListIndex.put(splittedLine[1].trim(), fileLines);

					  insertEdge(adjacentListIndex.get(splittedLine[1].trim()), edge);
				  

				  }
				  

				  Edge edge2 = new Edge();
				  edge2.setArtist(splittedLine[1].trim());
				  edge2.setMovie(splittedLine[0].trim());
				  if(isArtistInTheMap(adjacentListIndex, splittedLine[2].trim()))
				  {
					  insertEdge(adjacentListIndex.get(splittedLine[2].trim()), edge2);
				  }
				  else
				  {
					  fileLines ++;

					  adjacentListIndex.put(splittedLine[2].trim(), fileLines);

					  insertEdge(adjacentListIndex.get(splittedLine[2].trim()), edge2);
				  

				  }
				  
				  
				  
				  
					
				  
				  line = br.readLine();

			  }

		  
		  }
		  catch(Throwable e)
		  {
			  
		  }

    }
    
    public void insertEdge(int adjacentListIndex, Edge edge)
    {
    	this.adjacentList[adjacentListIndex].addFirst(edge);
    	
    }
    
    public LinkedList<Edge> getDirectNeighborArtists(String artist)
    {
    	LinkedList<Edge> neighborArtists = this.adjacentList[this.adjacentListIndex.get(artist)];
    	
    	return neighborArtists;
    }
    
    public boolean isZeroDegreeOfSeparation(String sourceArtist, String destinationArtist, int degreeOfSeparation)
    {
    	boolean isItZeroDegreeOfSeparation = false;
    	
    	
    	LinkedList<Edge> neighborArtists = getDirectNeighborArtists(sourceArtist);
    	
    	for(int i =0; i<neighborArtists.size(); i++)
    	{
    		StringBuffer artistTrackFormattedString = new StringBuffer();

    		
    		if(destinationArtist.equalsIgnoreCase(neighborArtists.get(i).getArtist()))
    		{
    			isItZeroDegreeOfSeparation = true;
    			artistTrackFormattedString.append(System.lineSeparator());

    			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
    			artistTrackFormattedString.append(System.lineSeparator());

    			artistTrackFormattedString.append(neighborArtists.get(i).getMovie());
    			artistTrackFormattedString.append(":");
    			artistTrackFormattedString.append(sourceArtist);
    			artistTrackFormattedString.append(" ");
    			artistTrackFormattedString.append(neighborArtists.get(i).getArtist());
    			this.artistTrack.put(neighborArtists.get(i).getArtist(), artistTrackFormattedString.toString());
    			//break;
    			
    			
    		}
    		else
    		{
    			artistTrackFormattedString.append(System.lineSeparator());

    			
    			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");

    			artistTrackFormattedString.append(System.lineSeparator());

    			artistTrackFormattedString.append(System.lineSeparator());
    			artistTrackFormattedString.append(neighborArtists.get(i).getMovie());
    			artistTrackFormattedString.append(":");
    			artistTrackFormattedString.append(sourceArtist);
    			artistTrackFormattedString.append(" ");
    			artistTrackFormattedString.append(neighborArtists.get(i).getArtist());
    			this.artistTrack.put(neighborArtists.get(i).getArtist(), artistTrackFormattedString.toString());
    			
    			
    			
    		
    			
    		}
    	}
    	
    	return isItZeroDegreeOfSeparation;

    }
    
    public boolean isOneDegreeOfSeparation(String sourceArtist, String destinationArtist, int degreeOfSeparation)
    {
    	boolean isItOneDegreeOfSeparation = false;
    	
    	LinkedList<Edge> neighborArtists = getDirectNeighborArtists(sourceArtist);
    	
    	NeighbourArtistsPerDegree neighbourArtistsPerDegree = new NeighbourArtistsPerDegree(neighborArtists.size());
    	neighbourArtistsPerDegree.initializeNeighbourArtists();
    	
    	
    	for (int i = 0; i<neighborArtists.size(); i++)
    	{
        	LinkedList<Edge> neighborArtistsLevel1 = getDirectNeighborArtists(neighborArtists.get(i).getArtist());
        	neighbourArtistsPerDegree.setNeighbourArtists(i,neighborArtistsLevel1);
        	
        	for (int j = 0; j<neighborArtistsLevel1.size(); j++)
        	{

        		StringBuffer artistTrackFormattedString = new StringBuffer();

        		
        		if(destinationArtist.equalsIgnoreCase(neighborArtistsLevel1.get(j).getArtist()))
        		{
        			isItOneDegreeOfSeparation = true;
        			if(isArtistInTheArtistTrackMap(this.artistTrack, neighborArtists.get(i).getArtist()))
        			{
        				String currentArtistTrack = this.artistTrack.get(neighborArtists.get(i).getArtist());
        				artistTrackFormattedString.append(currentArtistTrack);
        				

            			/**artistTrackFormattedString.append(System.lineSeparator());

            			//artistTrackFormattedString.append(""+(degreeOfSeparation-1)+" "+"degree of separation");
            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
            			artistTrackFormattedString.append(":");
            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
            			artistTrackFormattedString.append(" ");
            			artistTrackFormattedString.append(destinationArtist);
            			this.artistTrack.remove(neighborArtistsLevel1.get(j).getArtist());
            			this.artistTrack.put(neighborArtistsLevel1.get(j).getArtist(), artistTrackFormattedString.toString());
        				**/
        				
            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
            			artistTrackFormattedString.append(":");
            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getArtist());
            			artistTrackFormattedString.append(" ");
            			artistTrackFormattedString.append(neighborArtists.get(i).getArtist());
            			this.artistTrack.remove(neighborArtistsLevel1.get(j).getArtist());
            			this.artistTrack.put(neighborArtistsLevel1.get(j).getArtist(), artistTrackFormattedString.toString());
        				
            			
            			//break;
            			
        				
        			}
        			else
        			{

            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
            			artistTrackFormattedString.append(":");
            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getArtist());
            			artistTrackFormattedString.append(" ");
            			artistTrackFormattedString.append(neighborArtists.get(i).getArtist());
            			this.artistTrack.put(neighborArtistsLevel1.get(j).getArtist(), artistTrackFormattedString.toString());
            			//break;
            			
        			}
        			
        			
        		}
        		else
        		{

        			if(isArtistInTheArtistTrackMap(this.artistTrack, neighborArtists.get(i).getArtist()))
        			{
        				String currentArtistTrack = this.artistTrack.get(neighborArtists.get(i).getArtist());
        				artistTrackFormattedString.append(currentArtistTrack);

            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
            			artistTrackFormattedString.append(":");
            			artistTrackFormattedString.append(neighborArtists.get(i).getArtist());
            			artistTrackFormattedString.append(" ");
            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getArtist());
            			this.artistTrack.remove(neighborArtistsLevel1.get(j).getArtist());
            			this.artistTrack.put(neighborArtistsLevel1.get(j).getArtist(), artistTrackFormattedString.toString());
            			
            			//break;
            			
        				
        			}
        			else
        			{

            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
            			artistTrackFormattedString.append(System.lineSeparator());

            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
            			artistTrackFormattedString.append(":");
            			artistTrackFormattedString.append(neighborArtists.get(i).getArtist());
            			artistTrackFormattedString.append(" ");
            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getArtist());
            			this.artistTrack.put(neighborArtistsLevel1.get(j).getArtist(), artistTrackFormattedString.toString());
            			//break;
            			
        			}
        			
        			
        		
        			
        			
        			
        			
        		}
        	
        		
        	}

    	}
    	
    	this.neighbourArtists.put(degreeOfSeparation, neighbourArtistsPerDegree);
    	

    	return isItOneDegreeOfSeparation;
    }
    
    public boolean findDegreeOfSeparation(LinkedList<Edge> [] neighbourArtists, String sourceArtist, String destinationArtist, int degreeOfSeparation)  
    {
	    boolean isArtistFound = false;
	    int index = -1;

    	NeighbourArtistsPerDegree neighbourArtistsPerDegree = new NeighbourArtistsPerDegree(getArraySize(neighbourArtists));
    	neighbourArtistsPerDegree.initializeNeighbourArtists();
		
	    
	    for(int i=0; i<neighbourArtists.length; i++)
	    {

    		LinkedList<Edge> neighbors = neighbourArtists[i];
	    	
    		
        	//NeighbourArtistsPerDegree neighbourArtistsPerDegree = new NeighbourArtistsPerDegree(neighbourArtists.length);
        	//neighbourArtistsPerDegree.initializeNeighbourArtists();
    		
	    	
	    	for(int j =0; j < neighbors.size(); j++)
	    	{

    			//System.out.println("Getting here");
	    		


	    		LinkedList<Edge> neighborArtistsLevel1 = getDirectNeighborArtists(neighbors.get(j).getArtist());
	        	
	        	
	    		
	    		neighbourArtistsPerDegree.setNeighbourArtists(++index,neighborArtistsLevel1);
	    		

    			//System.out.println("degree is added "+""+degreeOfSeparation);

	        	for(int k =0; k<neighborArtistsLevel1.size(); k++)
	        	{

					//System.out.println("In the loop");
	        		

		    
		    	

		    	
		    		

	        		
	        		StringBuffer artistTrackFormattedString = new StringBuffer();

	        		
	        		if(destinationArtist.equalsIgnoreCase(neighborArtistsLevel1.get(k).getArtist()))
	        		{
	        			
	        			isArtistFound = true;
	        			if(isArtistInTheArtistTrackMap(this.artistTrack, neighbors.get(j).getArtist()))
	        			{
	        				String currentArtistTrack = this.artistTrack.get(neighbors.get(j).getArtist());
	        				artistTrackFormattedString.append(currentArtistTrack);
	        				
                            /**
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			//artistTrackFormattedString.append(""+(degreeOfSeparation-1)+" "+"degree of separation");
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(k).getMovie());
	            			artistTrackFormattedString.append(":");
	            			artistTrackFormattedString.append(neighbors.get(j).getArtist());
	            			artistTrackFormattedString.append(" ");
	            			artistTrackFormattedString.append(destinationArtist);
	            			//this.artistTrack.remove(neighborArtistsLevel1.get(k).getArtist());
	            			//this.artistTrack.put(neighborArtistsLevel1.get(k).getArtist(), artistTrackFormattedString.toString());
	        				**/
	        				
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(k).getMovie());
	            			artistTrackFormattedString.append(":");
	            			artistTrackFormattedString.append(neighbors.get(j).getArtist());
	            			artistTrackFormattedString.append(" ");
	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(k).getArtist());
	            			this.artistTrack.remove(neighborArtistsLevel1.get(k).getArtist());
	            			this.artistTrack.put(neighborArtistsLevel1.get(k).getArtist(), artistTrackFormattedString.toString());
	        				
	            			//System.out.println("here");
	            			//break;
	            			
	        				
	        			}
	        			else
	        			{

	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
	            			artistTrackFormattedString.append(":");
	            			artistTrackFormattedString.append(neighbors.get(j).getArtist());
	            			artistTrackFormattedString.append(" ");
	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getArtist());
	            			this.artistTrack.put(neighborArtistsLevel1.get(j).getArtist(), artistTrackFormattedString.toString());
	            			//break;
	            			
	        			}
	        			
	        			
	        		}
	        		else
	        		{

	        			if(isArtistInTheArtistTrackMap(this.artistTrack, neighbors.get(j).getArtist()))
	        			{
	        				String currentArtistTrack = this.artistTrack.get(neighbors.get(j).getArtist());
	        				artistTrackFormattedString.append(currentArtistTrack);
	        				
                            /**
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			//artistTrackFormattedString.append(""+(degreeOfSeparation-1)+" "+"degree of separation");
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(k).getMovie());
	            			artistTrackFormattedString.append(":");
	            			artistTrackFormattedString.append(neighbors.get(j).getArtist());
	            			artistTrackFormattedString.append(" ");
	            			artistTrackFormattedString.append(destinationArtist);
	            			this.artistTrack.remove(neighborArtistsLevel1.get(k).getArtist());
	            			this.artistTrack.put(neighborArtistsLevel1.get(k).getArtist(), artistTrackFormattedString.toString());
	        				
	        				**/
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(k).getMovie());
	            			artistTrackFormattedString.append(":");
	            			artistTrackFormattedString.append(neighbors.get(j).getArtist());
	            			artistTrackFormattedString.append(" ");
	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(k).getArtist());
	            			this.artistTrack.remove(neighborArtistsLevel1.get(k).getArtist());
	            			this.artistTrack.put(neighborArtistsLevel1.get(k).getArtist(), artistTrackFormattedString.toString());
	        				
	            			
	            			//break;
	            			
	        				
	        			}
	        			else
	        			{

	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(""+degreeOfSeparation+" "+"degree of separation");
	            			artistTrackFormattedString.append(System.lineSeparator());

	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getMovie());
	            			artistTrackFormattedString.append(":");
	            			artistTrackFormattedString.append(neighbors.get(j).getArtist());
	            			artistTrackFormattedString.append(" ");
	            			artistTrackFormattedString.append(neighborArtistsLevel1.get(j).getArtist());
	            			this.artistTrack.put(neighborArtistsLevel1.get(j).getArtist(), artistTrackFormattedString.toString());
	            			//break;
	            			
	        			}
	        			
	        			
	        		
	        			
	        			
	        		}
	        	
	        		
	        	
	        		
	        		
	        	}
	    	}
	    	
	    	this.neighbourArtists.put(degreeOfSeparation, neighbourArtistsPerDegree);

	    	
	    	
	    }
	
         return isArtistFound;
    }
    
    public boolean isArtistInTheMap(Map<String,Integer> map, String artist)
    {
    	boolean isArtistFoundInTheMap = false;
    	
    	for(Map.Entry<String,Integer> entry: map.entrySet())
    	{
    		if(artist.equalsIgnoreCase(entry.getKey()))
    		{
    			isArtistFoundInTheMap = true;
    			break;
    		}
    	}
    	
    	return isArtistFoundInTheMap;
    }
    
    public int getArraySize(LinkedList<Edge> [] neighbors)
    {
    	int size = 0;
    	for(int i =0; i<neighbors.length; i++)
    	{
    		size = size + neighbors[i].size();
    	}
    	
    	return size;
    }
    
    public boolean isArtistInTheArtistTrackMap(Map<String,String> map, String artist)
    {

    	boolean isArtistFoundInTheMap = false;
    	
    	for(Map.Entry<String,String> entry: map.entrySet())
    	{
    		if(artist.equalsIgnoreCase(entry.getKey()))
    		{
    			isArtistFoundInTheMap = true;
    			break;
    		}
    	}
    	
    	return isArtistFoundInTheMap;
    
    	
    }

	public LinkedList<Edge>[] getAdjacentList() {
		return adjacentList;
	}

	public void setAdjacentList(LinkedList<Edge>[] adjacentList) {
		this.adjacentList = adjacentList;
	}

	public int getVertices() {
		return vertices;
	}

	public void setVertices(int vertices) {
		this.vertices = vertices;
	}

	public Map<String, String> getArtistTrack() {
		return artistTrack;
	}

	public void setArtistTrack(Map<String, String> artistTrack) {
		this.artistTrack = artistTrack;
	}

	public Map<Integer, NeighbourArtistsPerDegree> getNeighbourArtists() {
		return neighbourArtists;
	}

	public void setNeighbourArtists(
			Map<Integer, NeighbourArtistsPerDegree> neighbourArtists) {
		this.neighbourArtists = neighbourArtists;
	}

	public Map<String, Integer> getAdjacentListIndex() {
		return adjacentListIndex;
	}

	public void setAdjacentListIndex(Map<String, Integer> adjacentListIndex) {
		this.adjacentListIndex = adjacentListIndex;
	}
    
    

}
