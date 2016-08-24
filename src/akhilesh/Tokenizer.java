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

	public HashMap<String,HashMap<String,Integer>> tokenize(HashMap<String,HashMap<String,Integer>>invertedIndex,String FileName,String Path) throws FileNotFoundException
	{
		ArrayList<String> stopWords=getStopWords();
		HashMap<String,HashMap<String,Integer>> tempIndex=new HashMap<String,HashMap<String,Integer>>();
		String FilePath=Path.concat("/").concat(FileName);

		Scanner s=new Scanner(new File(FilePath));
		ArrayList<String>words=new ArrayList<String>();
		while(s.hasNext())
		{
			if(!stopWords.contains(s.next().toLowerCase()))
			{
				words.add(s.next());
			}
		}
		s.close();

		for(int i=0;i<words.size();i++)
		{
			if(tempIndex.containsKey(words.get(i)))
			{
				HashMap<String,Integer>temp=new HashMap<String,Integer>();
				temp=tempIndex.get(words.get(i));
				temp.put(FileName, temp.get(FileName)+1);
				tempIndex.put(words.get(i),temp);
			}
			else
			{
				HashMap<String,Integer>temp=new HashMap<String,Integer>();
				temp.put(FileName, 1);
				tempIndex.put(words.get(i),temp);
			}
		}

		return tempIndex;


	}

}
