package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.util.Version;

public class SmartChineseAnalysis {
	public static void main(String[] args) throws IOException, ParseException {

		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_46,true);
		String str = "�������ʬ�N�ۤv�w�쬰�@�ӥ]�t�H���Ҧ����ѻ�쪺�ʬ���ѡA�Ӥ��O�@���r��B����B�׾©Υ����L�ʽ誺����";
		List<String> result = new ArrayList<String>();
		try{
			 TokenStream tokenStream = analyzer.tokenStream("field", str);    
			 CharTermAttribute term=tokenStream.addAttribute(CharTermAttribute.class);    
			 tokenStream.reset();       
			 while( tokenStream.incrementToken() ){        
				 result.add( term.toString() );       
			 }       
			 tokenStream.end();       
			 tokenStream.close();   
		 } catch (IOException e) {    
			 e.printStackTrace();   
		 }
		
		System.out.println(result);
	}
}
