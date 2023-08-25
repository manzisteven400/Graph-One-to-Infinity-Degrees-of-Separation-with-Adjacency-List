package algorithmforsocialmedia;

import java.io.File;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class MainClass {
	
	public static void main (String [] args)
	{
		String sourceArtist = "artist A";
		String destinationArtist = "artist L";
		int degreeOfSeparation = 0;
		
		File artistFile = new File("C:/Users/manzi/Desktop/movies.txt");
		HashSet<String> f = null;

		try
		{
		    f = new HashSet<String>(FileUtils.readLines(artistFile));
		}
		catch(IOException e)
		{
			
		}
		
		Graph graph = new Graph(2*f.size());
		graph.initializeGraph();
		graph.populateGraph("C:/Users/manzi/Desktop/movies.txt");
		
		if(graph.isZeroDegreeOfSeparation(sourceArtist, destinationArtist, degreeOfSeparation))
		{
			System.out.println(graph.getArtistTrack().get(destinationArtist));
		}
		else
		{
			degreeOfSeparation++;
			if(graph.isOneDegreeOfSeparation(sourceArtist, destinationArtist, degreeOfSeparation))
			{

				System.out.println(graph.getArtistTrack().get(destinationArtist));
			
			}
			
			else
			{

                while(!graph.findDegreeOfSeparation(graph.getNeighbourArtists().get(degreeOfSeparation).getNeighbourArtists(), sourceArtist, destinationArtist, ++degreeOfSeparation))
                {
                	
                	
                	if(degreeOfSeparation>6)
                	{
                		break;
                	}
                	
                	
                }
                
                if(degreeOfSeparation<=6)
                {


    				System.out.println(graph.getArtistTrack().get(destinationArtist));
    			
    			
                	
                }
                
                else
                {
                	



    				System.out.println("No degree of separation found");
    			
    			
                	
                
                	
                }
			
			
			}
		}

	}

}
