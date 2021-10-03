package pracalicencjacka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

public class DeleteStopWords {
    private Vector<Vector<String>> texts;
    private Vector<String> stopWords;
    private Vector<Vector<String>> processedText;
    
    public DeleteStopWords(Vector<Vector<String>> texts) {
        this.texts = texts;
        this.stopWords = new Vector<>();
        this.processedText = new Vector<>();
    }
    
    public Vector<Vector<String>> process() {
        try {
            File f = new File("stopwords-pl.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String line;
            while((line = bfr.readLine()) != null) { 
                stopWords.add(line);
            }
            bfr.close();
            
            for(Vector<String> text : texts) {
                Vector<String> newTableWords = new Vector<String>();
                for(String word : text) {
                    word = word.toLowerCase();
                    Boolean search = false;
                    for(String stop : stopWords) {
                        if(word.equals(stop.toLowerCase())) {
                            search = true;
                            break;
                        }
                    }
                    if(!search) {
                        newTableWords.add(word);
                    }
                }
                processedText.add(newTableWords);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return processedText;
    }
}
