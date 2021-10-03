package pracalicencjacka;

import java.io.*;
import java.util.Vector;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class PDFtoText {
    private Vector<Vector<String>> texts;
    
    public PDFtoText(String[] args) throws IOException {
        texts = new Vector<Vector<String>>();
        for(int i=0; i<args.length; i++) {
            File f = new File(args[i]);
            String pText;
            PDDocument doc = PDDocument.load(f);
            PDFTextStripper pdfs = new PDFTextStripper();
            pText = pdfs.getText(doc);
            PrintWriter pw = new PrintWriter("Document"+(i+1)+".txt");
            pw.print(pText);
            pw.close();
            Vector<String> pt = new Vector<String>();
            String word = "";
            for(int j=0; j<pText.length(); j++) {
                if(pText.charAt(j) != ' ' && pText.charAt(j) != '\r' && pText.charAt(j) != '\n' && pText.charAt(j) != '\t') {
                    word += pText.charAt(j);
                }
                else {
                    pt.add(word);
                    word = "";
                }
            }
            texts.add(pt);
            doc.close();
        }
    }
    
    public Vector<Vector<String>> getTexts() {
        return texts;
    }
}
