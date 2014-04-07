package example;

import java.io.IOException;
import java.io.File;

import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;

public class SpellCheck {
	public static void main(String[] args) throws IOException {
		


	      String spellCheckDir = "spell";
	      String spellCheckDic = "doc/dic.txt";
	      String wordToRespell = "wi";
	      
	      //���إ�spellcheck�Y�ݭn������
	      //�b�o�̷|���⻷��l���r�����ন����
	      Directory spellIndexDir = FSDirectory.open(new File(spellCheckDir));
	      SpellChecker spellChecker = new SpellChecker(spellIndexDir);
	      IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,
	              null);
	      spellChecker.indexDictionary(new PlainTextDictionary(new File(
	    		  spellCheckDic)), config, false);
	      
	      
	      //�Q�Ϋe�����o���ؿ��@���g�ˬd
	      spellChecker.setStringDistance(new LevensteinDistance());
	      String[] suggestions = spellChecker.suggestSimilar(wordToRespell, 5); //#C
	      System.out.println(suggestions.length + " suggestions for '" + wordToRespell + "':");
	      for (String suggestion : suggestions)
	    	  System.out.println(" " + suggestion);
	      
	      // close
	      spellIndexDir.close();
	      spellChecker.close();
	      
	      
	}
}
