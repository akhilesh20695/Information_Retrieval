package akhilesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Tokenizer {

	static ArrayList<String> stopWords=new ArrayList<String>();
	
	//Function to get all the stopwords from stopwords file and store it in an ArrayList
	public void getStopWords() throws FileNotFoundException
	{
		String stopWordsPath=new String("/home/akhilesh/stopwordslist.txt");
		Scanner s = new Scanner(new File(stopWordsPath));
		//ArrayList<String> stopWords = new ArrayList<String>();// Array List to store all the Stopwords
		while (s.hasNext())
		{
			stopWords.add(s.next().toLowerCase());//reading stopwords file and storing each stopword in an arraylist
		}
		s.close();
	}
	
	

	public HashMap<String,HashMap<String,Integer>> tokenize(ArrayList<String> FileName) throws FileNotFoundException
	{
		getStopWords();
		HashMap<String,HashMap<String,Integer>> tempIndex=new HashMap<String,HashMap<String,Integer>>();
		
		for(int j=0;j<FileName.size();j++)
		{
			String Individual_File=FileName.get(j);
			
			String tempName=new File(Individual_File).getName();
			
			System.out.println("Indexing File: "+Individual_File);
			
			Scanner s=new Scanner(new File(Individual_File));
			
			//ArrayList<String>words=new ArrayList<String>();
			ArrayList<String>StemmedWords=new ArrayList<String>();
			
			while(s.hasNext())
			{
				String temp_word=s.next();
				
				temp_word = temp_word.replaceAll("\\<.*?>","");
				temp_word = temp_word.replaceAll("[^a-zA-Z0-9_-]", "");
				
				if(temp_word.length() != 0)
				{
					if(!stopWords.contains(temp_word.toLowerCase()))
					{
						
						if(temp_word.substring(temp_word.length()-1).equals("."))
							temp_word = temp_word.replaceAll(".$", "");
						if(temp_word.substring(temp_word.length()-1).equals(","))
							temp_word = temp_word.replaceAll(",$", "");
						if(temp_word.substring(temp_word.length()-1).equals(";"))
							temp_word = temp_word.replaceAll(";$", "");
						
						//words.add(temp_word.toLowerCase());
						Stemmer stemVariable=new Stemmer();
						stemVariable.add(temp_word.toCharArray(), temp_word.length());
						stemVariable.stem();
						StemmedWords.add(stemVariable.toString().toLowerCase());
						
					}
				}
			}
			
			s.close();
			
			for(int i=0;i<StemmedWords.size();i++)
			{
				HashMap<String,Integer>temp=new HashMap<String,Integer>();
				//Check if the word exists in the HashMap
				if(tempIndex.containsKey(StemmedWords.get(i))) //Word exists in the HashMap
				{
					temp=tempIndex.get(StemmedWords.get(i));
					
					if(temp.containsKey(tempName))
					/*Checking if document containing the word is already 
					 * in the posting list or not.
					 * If it's in the posting list, get it's frequency, add and store it in the hash table.
					*/
					{
						int freq=temp.get(tempName)+1;
						temp.put(tempName, freq);
					}
					/*
					 * If not, now save the document along with it's frequency i.e 1.
					 */
					else
					{
						temp.put(tempName, 1);
					}
					tempIndex.put(StemmedWords.get(i),temp);
				}
				else
				{
					temp.put(tempName, 1);
					tempIndex.put(StemmedWords.get(i),temp);
				}
			}
		}
		return tempIndex;
	}

}
