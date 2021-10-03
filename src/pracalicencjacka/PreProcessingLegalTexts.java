package pracalicencjacka;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class PreProcessingLegalTexts {
    public static void main(String[] args) throws IOException {
        
        //zamiana dokumnetow pdf do postaci wektorow tablic wyrazow
        PDFtoText pdftt = new PDFtoText(args);
        Vector<Vector<String>> mainTexts = pdftt.getTexts();
        
        //usuniecie znakow interpunkcyjnych
        DeletePunctuationMarks dpm = new DeletePunctuationMarks(mainTexts);
        Vector<Vector<String>> textsWithoutPunctuationMarks = dpm.process();
        
        //usuniecie "Stop words", wyrazow, ktore nie wnosza nic do znaczenia tekstu
        DeleteStopWords dsw = new DeleteStopWords(textsWithoutPunctuationMarks);
        Vector<Vector<String>> textsWithoutStopWords = dsw.process();
        
        //wydobycie podstawy slow
        Stemming stemm = new Stemming(textsWithoutStopWords);
        Vector<Vector<String>> textsAfterPreprocessing = stemm.process();
        
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Zliczanie wystapien slow;");
        System.out.println("2. Obliczenie wskaznika TF/IDF");
        
        Scanner in = new Scanner(System.in);
        System.out.println("Twoj wybor: ");
        String check1 = in.nextLine();
        int check = 0;
        
        try {
            check = Integer.parseInt(check1);
        }
        catch(Exception e) {
            System.out.println("Zly wybor. Uruchom program ponownie.");
            System.exit(0);
        }
            
        switch(check) {
            case 1 : { 
                //zliczanie slow wystepujacych w tekstach
                CountingWords countingWords = new CountingWords(textsAfterPreprocessing);
                countingWords.count();
                break;
            }
            case 2: {
                //Wyliczenie wskaźnika TF/IDF
                CountingWords countingWords = new CountingWords(textsAfterPreprocessing);
                countingWords.count();
                Vector<Map<String, Double>> tf_idf = countingWords.tfidf();
                
                ArrayList<Attribute> atts;
                double[] vals;
                Instances data;
        
                atts = new ArrayList<Attribute>();
        
                for(Vector<String> text : textsAfterPreprocessing) {
                    for(String word : text) {
                        Attribute e = new Attribute(word);
                        if(!atts.contains(e))
                            atts.add(e);
                    }
                }
        
                data = new Instances("Preprocessing Legal Texts ARFF - TF/IDF", atts, 0);
        
                int j = 1;
                for(Map<String, Double> one : tf_idf) {
                    PrintWriter pw = new PrintWriter("Document"+j+"TF_IDF.txt");
                    vals = new double[data.numAttributes()];
                    int i = 0;
                    Set<Entry<String, Double>> entrySet2 = one.entrySet();
                    for(Entry<String, Double> entry : entrySet2) {
                        vals[i] = entry.getValue();
                        i++;
                        pw.println(entry.getKey() + " : " + entry.getValue());
                    }
                    data.add(new DenseInstance(1.0, vals));
                    pw.close();
                    j++;   
                }
                PrintWriter pw = new PrintWriter("TF_IDFStatistic.arff");
                pw.print(data);
                pw.close();
                System.out.println(data);
                break;
            }
            default: {
                System.out.println("Zly wybor. Uruchom program ponownie.");
            }
        }
    }    
}
