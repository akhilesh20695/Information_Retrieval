/* Program to Index Files using Porter Stemmer
 * Author: Akhilesh Kumar
 */
package akhilesh;
import java.io.*;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;

public class Indexer {
	static ArrayList<String>fileNames=new ArrayList<String>();
	
	public static ArrayList<String> printFileNames(String Directory)
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
				printFileNames(file.getAbsolutePath());
			}
		}
		return fileNames;
	}

	public static void main(String[] args) throws FileNotFoundException {

		//System.out.println("Enter the folder path which have all the files: ");
		/*Scanner sc=new Scanner(System.in); // path to the corpus which have to be indexed
		String path=sc.next();
		sc.close();*/
		String path="/home/akhilesh/data/toi/2009/1";
		System.out.println("Indexing Files");
		ArrayList<String> FilesList=new ArrayList<String>(); //ArrayList to store all the file names
		FilesList=printFileNames(path);
		
		HashMap<String,HashMap<String,Integer>>invertedIndex=new HashMap<String,HashMap<String,Integer>>();  

		/*Calling tokenize function of tokenizer class to tokenize the file and 
		  remove stopwords also and return a HashMap with particular word and it's
		  document and frequency.(Posting List)
		 */
		Tokenizer obj=new Tokenizer();
		invertedIndex=obj.tokenize(invertedIndex,FilesList);	

		
		/*
		 * Printing the HashMap i.e. Printing the Inverted Index Hash.
		 */
		Set<String> FinalWords=invertedIndex.keySet();
		Iterator<String> mainIterator=FinalWords.iterator();
		while(mainIterator.hasNext())
		{
			String word=(String)mainIterator.next();
			HashMap<String,Integer>postingList=new HashMap<String,Integer>();
			postingList=invertedIndex.get(word);
			
			Set<String> docs=postingList.keySet();
			Iterator<String> posting_list_iterator=docs.iterator();
			
			System.out.print(word+": ");
			while(posting_list_iterator.hasNext())
			{
				String docName=(String)posting_list_iterator.next();
				Integer frequency=(Integer)postingList.get(docName);
				System.out.print(docName+": "+frequency+"; ");
			}
			System.out.print("\n");
		}
		System.out.println("Indexer Execution Completed");
	}

}
