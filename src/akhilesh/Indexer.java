/* Program to Index Files using Porter Stemmer
 * Author: Akhilesh Kumar
 */
package akhilesh;
import java.io.*;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Scanner;
import java.util.Iterator;

public class Indexer {
	static ArrayList<String>fileNames=new ArrayList<String>(); //Store all the filename in an arraylist
	
	public static void storeFileNames(String Directory)
	{
		File[] Files = new File(Directory).listFiles();
		for(File file: Files)
		{
			if(file.isFile())
			{
				fileNames.add(file.getAbsolutePath());
			}
			if(file.isDirectory())
			{
				storeFileNames(file.getAbsolutePath());
			}
		}
	}

	public static void main(String[] args) throws IOException 
	{

		//System.out.println("Enter the folder path which have all the files: ");
		/*Scanner sc=new Scanner(System.in); // path to the corpus which have to be indexed
		String path=sc.next();
		sc.close();*/
		String path="/home/akhilesh/data/toi/2009/1";
		System.out.println("Indexing Files");
		storeFileNames(path);
		
		HashMap<String,HashMap<String,Integer>>invertedIndex=new HashMap<String,HashMap<String,Integer>>();  

		/*Calling tokenize function of tokenizer class to tokenize the file and 
		  remove stopwords also and return a HashMap with particular word and it's
		  document and frequency.(Posting List)
		 */
		Tokenizer obj=new Tokenizer();
		invertedIndex=obj.tokenize(fileNames);	

		
		/*
		 * Printing the HashMap i.e. Printing the Inverted Index Hash.
		 */
		Set<String> FinalWords=invertedIndex.keySet();
		Iterator<String> mainIterator=FinalWords.iterator();
		Writer writer=null;
		try
		{
			writer=new BufferedWriter(new FileWriter("/home/akhilesh/indexer_result.txt"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		while(mainIterator.hasNext())
		{
			String word=(String)mainIterator.next();
			HashMap<String,Integer>postingList=new HashMap<String,Integer>();
			postingList=invertedIndex.get(word);
			
			Set<String> docs=postingList.keySet();
			Iterator<String> posting_list_iterator=docs.iterator();
			
			writer.write(word+": ");
			while(posting_list_iterator.hasNext())
			{
				String docName=(String)posting_list_iterator.next();
				Integer frequency=(Integer)postingList.get(docName);
				writer.write(docName+": "+frequency+";    ");
			}
			writer.write("\n");
		}
		System.out.println("Indexer Execution Completed");
	}
}
