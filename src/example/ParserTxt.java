package example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.util.Version;
public class ParserTxt {
	public static void main(String[] args) throws IOException, ParseException {

		int minGram = 1;
		int maxGram = 3;
		Reader reader = new StringReader("�������ʬ�N�ۤv�w�쬰�@�ӥ]�t�H���Ҧ����ѻ�쪺�ʬ���ѡA�Ӥ��O�@���r��B����B�׾©Υ����L�ʽ誺����");
		NGramTokenizer gramTokenizer = new NGramTokenizer(Version.LUCENE_46,
	              reader, minGram, maxGram);
		gramTokenizer.reset();
		CharTermAttribute charTermAttribute = gramTokenizer.addAttribute(CharTermAttribute.class);
	

		while (gramTokenizer.incrementToken()) {
		    String token = charTermAttribute.toString();
		    System.out.println(token);
		    //Do something
		}
		
		gramTokenizer.end();
		gramTokenizer.close();
	}
}
