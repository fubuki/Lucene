package example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IndexDoc {
  public static void main(String[] args) throws TikaException, IOException {

    InputStream is = null;
    try {
      is = new FileInputStream("doc/index.pdf");
      ContentHandler contenthandler = new BodyContentHandler(10 * 1024 * 1024);
      Metadata metadata = new Metadata();
      PDFParser pdfparser = new PDFParser();
      pdfparser.parse(is, contenthandler, metadata, new ParseContext());
      //System.out.println(contenthandler.toString());

      Analyzer analyzer = new StandardAnalyzer();
      Directory directory = new RAMDirectory();
      IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(analyzer));
      Document doc = new Document();
      doc.add(new TextField("content", contenthandler.toString(), Store.YES));
      FieldType type = new FieldType();
      type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
      type.setStored(true);

      writer.addDocument(doc);
      writer.close();

      DirectoryReader reader = DirectoryReader.open(directory);
      IndexSearcher searcher = new IndexSearcher(reader);
      Query query = new TermQuery(new Term("content", "wiki"));
      TopDocs topDocs = searcher.search(query, 10);



      ScoreDoc[] scores = topDocs.scoreDocs;
      int length = scores.length;
      int id = scores[0].doc;
      //System.out.println("length:"+length);
      Document result = searcher.doc(scores[0].doc);
      //System.out.println("result:"+result.get("content"));


      // test hightlight function
      String text = doc.get("content");
      SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
      Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));

      TokenStream tokenStream =
          TokenSources.getAnyTokenStream(searcher.getIndexReader(), id, "content", analyzer);
      TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, 4);
      for (int j = 0; j < frag.length; j++) {
        if ((frag[j] != null) && (frag[j].getScore() > 0)) {
          System.out.println((frag[j].toString()));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (is != null)
        is.close();
    }
  }
}
