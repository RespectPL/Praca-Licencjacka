package pracalicencjacka;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import static java.lang.Math.log;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;

public class CountingWords {
    private Vector<Vector<String>> texts;
    private Vector<Map<String, Integer>> cWords;
    private Vector<Integer> allWordsInText;
    private int numberOfTexts;
    
    public CountingWords(Vector<Vector<String>> texts) {
        this.texts = texts;
        this.cWords = new Vector<Map<String, Integer>>();
        this.allWordsInText = new Vector<Integer>();
        this.numberOfTexts = this.texts.size();
    }
                                                    
    public void count() throws FileNotFoundException {
        int j = 1;
        int countedWords = 0;
        
        ArrayList<Attribute> atts;
        double[] vals;
        Instances data;
        
        atts = new ArrayList<Attribute>();
        
        for(Vector<String> text: texts) {
            for(String word : text) {
                Attribute e = new Attribute(word);
                if(!atts.contains(e))
                    atts.add(e);
            }
        }
        
        data = new Instances("Preprocessing Legal Texts ARFF - Counting Words", atts, 0);
        
        for(Vector<String> text : texts) {
            Map<String, Integer> countingWords = new HashMap<String, Integer>();
            for(Vector<String> text1 : texts) {
                for(String word : text1) {
                    if(!countingWords.containsKey(word)) {
                        countingWords.put(word, 0);
                    }
                }
            }
            for(String word : text) {
                int count = countingWords.get(word);
                count++;
                countingWords.remove(word);
                countingWords.put(word, count);
                countedWords++;
            }
            cWords.add(countingWords);
            allWordsInText.add(countedWords);
            
            PrintWriter pw = new PrintWriter("Document"+j+"Statistic.txt");
            vals = new double[data.numAttributes()];
            int i = 0;
            Set<Entry<String, Integer>> entrySet = countingWords.entrySet();
            for(Entry<String, Integer> entry : entrySet) {
                vals[i] = entry.getValue();
                i++;
                pw.println(entry.getKey() + " : " + entry.getValue());
            }
         
            data.add(new DenseInstance(1.0, vals));
            pw.close();    
            countedWords = 0;
            j++;
        }
        PrintWriter pw = new PrintWriter("CountingWordsStatistic.arff");
        pw.print(data);
        pw.close();
        System.out.println(data);
    }
    
    public Vector<Map<String, Double>> tfidf() {   
        double tf1;
        double idf1;
        double tf_idf1;
        int i = 0;
        Vector<Map<String, Double>> ret = new Vector<Map<String, Double>>();
        for(Map<String, Integer> countWords : cWords) {
            Map<String, Double> tf_idf = new HashMap<String, Double>();
            int allWords = allWordsInText.get(i);
            Set<Entry<String, Integer>> entrySet = countWords.entrySet();
            for(Entry<String, Integer> entry : entrySet) {    
                String word = entry.getKey();
                int count = entry.getValue();
                
                tf1 = count/(double)allWords;
                int d = 0;
                for(Map<String, Integer> c : cWords) {
                    if(c.containsKey(word) && c.get(word) != 0)
                        d++;
                }
                idf1 = log(numberOfTexts/(double)d);
                tf_idf1 = tf1 * idf1;
                tf_idf.put(word, tf_idf1);
            }
            ret.add(tf_idf);
            i++;
        }
        return ret;
    }
}
