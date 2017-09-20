package task;


import java.util.Scanner;
/*
 * Class to use DictionaryMaker and BracketChecker that realize user console interface 
 */
public class Helper {
	BracketChecker bracketChecker;		
	DictionaryMaker dictionaryMaker;
	public Helper(){		//class constructor that initialize variables
		bracketChecker = new BracketChecker();
		dictionaryMaker = new DictionaryMaker();
		
	}
	public void start(){ 		//method that realize user console interface
		Scanner in = new Scanner(System.in);
		System.out.println("Input your preferred operation: \n"
				+ "B - Bracket sequence analysis \n"
				+ "T - Text analysis \n"
				+ "E - Exit");
		String ch = "$";
		while(true){
			System.out.println("Your choice:");
			ch = in.nextLine();
			switch(ch){
				case "B":{
					System.out.println("Input full path to the file with bracket sequence:");
					String path = in.nextLine();
					try {
						bracketChecker.readFile(path);
						System.out.println(bracketChecker.bracketsCheck());
					} catch (Exception e) {
						System.out.println("Wrong path! \n");
					}
					break;
				}
				case "T":{
					System.out.println("Input full path to the file with text:");
					String path = in.nextLine();
					try {
						dictionaryMaker.readText(path);
						System.out.println(dictionaryMaker.getTopTen());
					} catch (Exception e) {
						System.out.println("Wrong path! \n");
					}
					break;
					
				}
				case "E":{
					in.close();
					System.out.println("Exit");
					System.exit(0);
				}
				default:{
					System.out.println("Wrong operation! Input another one.\n");
					break;
				}
			}
		}
	}
}
