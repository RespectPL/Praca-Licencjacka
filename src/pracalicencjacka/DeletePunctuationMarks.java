package pracalicencjacka;

import java.util.Vector;

public class DeletePunctuationMarks {
    private Vector<Vector<String>> texts;
    private Vector<Vector<String>> processedText;
    
    public DeletePunctuationMarks(Vector<Vector<String>> texts) {
        this.texts = texts;
        this.processedText = new Vector<>();
    }
    
    public Vector<Vector<String>> process() {
        for(Vector<String> text : texts) {
            Vector<String> newTableText = new Vector<String>();
            for(String word : text) {
                String newWord = "";
                for(int j=0; j<word.length(); j++) {
                    char singleCharacter = word.charAt(j);
                    if(singleCharacter != '.' && singleCharacter != ',' && singleCharacter != '!' 
                            && singleCharacter != '?' && singleCharacter != '\\' && singleCharacter != '/' 
                            && singleCharacter != '(' && singleCharacter != ')' && singleCharacter != ';' 
                            && singleCharacter != ':' && singleCharacter != '-' && singleCharacter != '[' 
                            && singleCharacter != ']' && singleCharacter != '"') {
                        newWord += singleCharacter;
                    }
                }
                newTableText.add(newWord);
            }
            processedText.add(newTableText);
        }
        
        return processedText;
    }
    
}
