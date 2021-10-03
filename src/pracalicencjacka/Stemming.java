package pracalicencjacka;

import java.util.Vector;
import morfologik.polish.PolishStemmer;
import morfologik.stemming.*;

public class Stemming {
    private Vector<Vector<String>> texts;
    private Vector<Vector<String>> processedText;
    
    public Stemming(Vector<Vector<String>> texts) {
        this.texts = texts;
        this.processedText = new Vector<>();
    }
    
    public Vector<Vector<String>> process() {
        PolishStemmer stemmer = new PolishStemmer();
        
        for(Vector<String> text : texts) {
            Vector<String> newTableWords = new Vector<String>();
            for(String word : text) {
                Boolean check = false;
                String wordStemm = null;
                for(WordData wd : stemmer.lookup(word)) {
                        wordStemm = wd.getStem().toString();
                        check = true;
                        break;
                }
                if(!check) {
                    newTableWords.add(word);
                }
                else {
                    newTableWords.add(wordStemm);
                }
            }
            processedText.add(newTableWords);
        }
        
        return processedText;
    }
}
