package task;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
/*
 * Analyze text without 
 */
public class DictionaryMaker{
	
	private HashMap<String, Integer> wordMap; //dictionary of all words with their counts
	private HashSet<String> wrongWords;		//dictionary of prepositions, conjunctions and pronouns
	private String text;					// text for analyzing
	
	public DictionaryMaker(){				//// class constructor that initialize variables
		text = "";
		wordMap = new HashMap<String, Integer>();
		wrongWords = new HashSet<String>();
		Properties prop = new Properties();
		InputStream input = null;

		try {								// getting prepositions, conjunctions and pronouns from properties
			input = getClass().getClassLoader().getResourceAsStream("config.properties");
		    prop.load(input);

		    String badWords;
		    badWords = prop.getProperty("badWords");
		    String[] array = badWords.split(",");
		    for(int i =0; i < array.length; i++){
		    	wrongWords.add(array[i]);
		    }

		} catch (IOException ex) {
		    ex.printStackTrace();
		} finally {
		    if (input != null) {
		        try {
		            input.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	public void readText(String fileName) throws IOException{ // method that reads text from file
		BufferedReader bufferedReader = null;
		bufferedReader = new BufferedReader(new FileReader(fileName));
		String line;
		while((line = bufferedReader.readLine()) != null){
			text += line;
			text += " ";
		}
		bufferedReader.close();
	}
	private void makeDictionary(){	//method that analyze "text" and puts good words in to the "wordMap"
		String tmp = "";
		for(int i = 0; i < text.length(); i++){ //reading words without punctuation marks and spaces
			Character symbol = text.charAt(i);
			if(isLetter(symbol)){
				tmp += symbol;
			}
			else{
				if(isWord(tmp)){ 	//putting words into the "wordMap" 
					
					Integer countOfWord = wordMap.get(tmp);
					if(countOfWord != null){
						countOfWord++;
					}
					else{
						countOfWord = 1;
					}
					wordMap.put(tmp, countOfWord);
				}
				tmp = "";
			}
		}
	}
	public String getTopTen(){
		makeDictionary();
		String answer = "Top words: \n";
		LinkedList<Map.Entry<String, Integer>> result = sort();
		for(int i = 0; i < Math.min(10, wordMap.size()); i++){
			answer += result.get(i).getKey();
			answer+="\n";
		}
		return answer;
	}
	private boolean isLetter(Character symbol){		//method that checks whether a character is a letter
		if((symbol >= 'a' && symbol <= 'z') || (symbol >= 'A' && symbol <= 'Z') || symbol == '-')
			return true;
		return false;
	}
	private boolean isWord(String str){				//method that checks whether a string is a word  
		str = str.toLowerCase();
		if(str.length() <= 2) 
			return false;
		if(wrongWords.contains(str)) 
			return false;
		return true;
	}
	private LinkedList<Map.Entry<String, Integer>> sort(){	//method that sorts "wordMap" by value and returns LinkedList
		Set< Map.Entry<String, Integer> > aSet = wordMap.entrySet();
		List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(aSet);
		Collections.sort( aList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        });
		return (LinkedList<Entry<String, Integer>>) aList;		
	}
}
