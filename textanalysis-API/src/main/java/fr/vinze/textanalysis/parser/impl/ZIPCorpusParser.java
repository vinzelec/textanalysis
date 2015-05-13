package fr.vinze.textanalysis.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.impl.RawTextDocumentCorpusImpl;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.parser.CorpusParser;
import fr.vinze.textanalysis.parser.CorpusType;
import fr.vinze.textanalysis.parser.ParseException;

public class ZIPCorpusParser implements CorpusParser {

	@Override
	public RawTextDocumentCorpus parseCorpus(File source) throws FileNotFoundException, ParseException, IOException {
		ZipInputStream in = null;
		try {
			RawTextDocumentCorpus corpus = new RawTextDocumentCorpusImpl();
			in = new ZipInputStream(new FileInputStream(source));
			byte[] buffer = new byte[1024];
			ZipEntry entry;
			while ((entry = in.getNextEntry()) != null) {
				String name = entry.getName();
				// extract data
				StringBuilder content = new StringBuilder();
				// open output streams
				while (in.read(buffer) > 0) {
					content.append(new String(buffer));
				}
				corpus.addDocument(new RawTextDocumentImpl(name, content.toString()));
			}
			in.close();
			return corpus;
		} finally {
			if (in != null) {
				IOUtils.closeQuietly(in);
			}
		}
	}

	@Override
	public CorpusType canParse() {
		return CorpusType.ZIP;
	}

}
