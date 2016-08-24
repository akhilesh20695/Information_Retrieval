package akhilesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Tokenizer {

	public ArrayList<String> getStopWords() throws FileNotFoundException
	{
		String stopWordsPath=System.getProperty("user.dir").concat("/src/akhilesh/stopwords.txt"); //path for stopwords.txt file.
		Scanner s = new Scanner(new File(stopWordsPath));
		ArrayList<String> stopWords = new ArrayList<String>();// Array List to store all the Stopwords
		while (s.hasNext())
		{
			stopWords.add(s.next());//reading stopwords file and storing each stopword in an arraylist
		}
		s.close();
		return stopWords;
	}
	
	

	public HashMap<String,HashMap<String,Integer>> tokenize(HashMap<String,HashMap<String,Integer>>invertedIndex,ArrayList<String> FileName) throws FileNotFoundException
	{
		ArrayList<String> stopWords=getStopWords();
		HashMap<String,HashMap<String,Integer>> tempIndex=new HashMap<String,HashMap<String,Integer>>();
		
		for(int j=0;j<FileName.size();j++)
		{
			String Individual_File=FileName.get(j);
			
			Scanner s=new Scanner(new File(Individual_File));
			ArrayList<String>words=new ArrayList<String>();
			
			while(s.hasNext())
			{
				String temp_word=s.next();
				temp_word = temp_word.replaceAll("\\<.*?>","");
				//temp_word = temp_word.replaceAll("[^\\w\\s]","");
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
						
						words.add(temp_word.toLowerCase());
					}
				}
			}
			s.close();
			
			ArrayList<String>StemmedWords=new ArrayList<String>();
			
			for(int i=0;i<words.size();i++)
			{
				Stemmer stemVariable=new Stemmer();
				stemVariable.add(words.get(i).toCharArray(), words.get(i).length());
				stemVariable.stem();
				StemmedWords.add(stemVariable.toString());	
			}
			
			
			for(int i=0;i<StemmedWords.size();i++)
			{
				HashMap<String,Integer>temp=new HashMap<String,Integer>();
				//Check if the word exists in the HashMap
				if(tempIndex.containsKey(StemmedWords.get(i))) //Word exists in the HashMap
				{
					temp=tempIndex.get(StemmedWords.get(i));
					
					if(temp.containsKey(Individual_File))
					/*Checking if document containing the word is already 
					 * in the posting list or not.
					 * If it's in the posting list, get it's frequency, add and store it in the hash table.
					*/
					{
						int freq=temp.get(Individual_File)+1;
						temp.put(Individual_File, freq);
					}
					/*
					 * If not, now save the document along with it's frequency i.e 1.
					 */
					else
					{
						temp.put(Individual_File, 1);
					}
					tempIndex.put(StemmedWords.get(i),temp);
				}
				else
				{
					temp.put(Individual_File, 1);
					tempIndex.put(StemmedWords.get(i),temp);
				}
			}
		}
		return tempIndex;
	}

}
