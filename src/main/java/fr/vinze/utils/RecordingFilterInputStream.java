package fr.vinze.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wrap an {@link InputStream} and record everything read in a {@link StringBuilder}
 * 
 * @author Vinze
 *
 */
public class RecordingFilterInputStream extends FilterInputStream {

	StringBuilder builder;

	public RecordingFilterInputStream(InputStream in) {
		super(in);
		builder = new StringBuilder();
	}

	public String getRecorded() {
		return builder.toString();
	}

	@Override
	public int read() throws IOException {
		int temp = super.read();
		builder.append((char) temp);
		return temp;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int temp = super.read(b);
		builder.append(b);
		return temp;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int temp = super.read(b, off, len);
		builder.append(b);
		return temp;
	}
}
