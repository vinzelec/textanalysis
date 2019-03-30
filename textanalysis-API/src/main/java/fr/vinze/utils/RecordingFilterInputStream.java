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

	private final StringBuilder builder;

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
		if (temp != -1) {
			builder.append((char) temp);
		}
		return temp;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int temp = super.read(b, off, len);
		if (temp != -1) {
			builder.append(new String(b, 0, temp));
		}
		return temp;
	}
}
