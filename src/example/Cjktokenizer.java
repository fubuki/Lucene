package example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.util.Version;

public class Cjktokenizer {
	public static void main(String[] args) throws IOException, ParseException {


		Reader reader = new StringReader("�������ʬ�N�ۤv�w�쬰�@�ӥ]�t�H���Ҧ����ѻ�쪺�ʬ���ѡA�Ӥ��O�@���r��B����B�׾©Υ����L�ʽ誺����");
			
        Analyzer a = new CJKAnalyzer(Version.LUCENE_46);               
        TokenStream ts = a.tokenStream("", reader);   
        OffsetAttribute offsetAttribute = ts.getAttribute(OffsetAttribute.class);  
        CharTermAttribute termAttribute = ts.getAttribute(CharTermAttribute.class);           
        int n = 0;   
        ts.reset();
        while (ts.incrementToken()) {   
            int startOffset = offsetAttribute.startOffset();  
            int endOffset = offsetAttribute.endOffset();  
            String term = termAttribute.toString();  
            n++;   
            System.out.println("Token("+n+") �����e���G"+term);   
        }   
        System.out.println("==�@������"+n+"��=="); 
        
        ts.end();
        ts.close();
	}
}