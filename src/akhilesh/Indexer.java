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

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("Enter the folder path which have all the files: ");
		Scanner sc=new Scanner(System.in); // path to the corpus which have to be indexed
		String path=sc.next();
		sc.close();
		File folder=new File(path);
		File[] listOfFiles=folder.listFiles(); //All list of files in the folder

		ArrayList<String> FilesList=new ArrayList<String>(); //ArrayList to store all the file names

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				FilesList.add(listOfFiles[i].getName());
			} 
		}

		HashMap<String,HashMap<String,Integer>>invertedIndex=new HashMap<String,HashMap<String,Integer>>();  

		/*Calling tokenize function of tokenizer class to tokenize the file and 
		  remove stopwords also and return a HashMap with particular word and it's
		  document and frequency.(Posting List)
		 */
		Tokenizer obj=new Tokenizer();
		for(int i=0;i<FilesList.size();i++)
		{
			invertedIndex=obj.tokenize(invertedIndex,FilesList.get(i),path);
		}	

		//Passing the HashMap through stemmer function to stem the HashMap.
		
		//Printing the HashMap.
		Set FinalWords=invertedIndex.keySet();
		Iterator iterator=FinalWords.iterator();
		while(iterator.hasNext())
		{
			String word=(String)iterator.next();
			HashMap<String,Integer>postingList=new HashMap<String,Integer>();
			postingList=invertedIndex.get(word);
			
			Set docs=postingList.keySet();
			Iterator posting_list_iterator=docs.iterator();
			System.out.print(word+": ");
			while(posting_list_iterator.hasNext())
			{
				String docName=(String)posting_list_iterator.next();
				Integer frequency=(Integer)postingList.get(docName);
				System.out.print(docName+": "+frequency);
			}
			System.out.print("\n");
		}
	}

}
