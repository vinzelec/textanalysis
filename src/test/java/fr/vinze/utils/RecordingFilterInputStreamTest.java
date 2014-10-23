package fr.vinze.utils;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class RecordingFilterInputStreamTest {

	String inputData = "This is the input data content.";
	
	ByteArrayInputStream input = null;
	RecordingFilterInputStream recordingIS = null;

	@Before
	public void initStreams() {
		input = new ByteArrayInputStream(inputData.getBytes());
		recordingIS = new RecordingFilterInputStream(input);
	}

	@Test(timeout = 1000)
	public void testRead() throws Exception {
		try {
			while (recordingIS.read() != -1) {
				// do nothing just read
			}
			String recorded = recordingIS.getRecorded();
			assertEquals("equals", inputData, recorded);
		} finally {
			if (input != null) {
				IOUtils.closeQuietly(input);
			}
			if (recordingIS != null) {
				IOUtils.closeQuietly(recordingIS);
			}
		}
	}

	@Test(timeout = 1000)
	public void testReadBytes() throws Exception {
		byte[] b = new byte[16];
		try {
			while (recordingIS.read(b) != -1) {
				// do nothing just read
			}
			String recorded = recordingIS.getRecorded();
			assertEquals("equals", inputData, recorded);
		} finally {
			if (input != null) {
				IOUtils.closeQuietly(input);
			}
			if (recordingIS != null) {
				IOUtils.closeQuietly(recordingIS);
			}
		}
	}

	@Test(timeout = 1000)
	public void testReadBytes2() throws Exception {
		byte[] b = new byte[16];
		try {
			while (recordingIS.read(b, 0, 16) != -1) {
				// do nothing just read
			}
			String recorded = recordingIS.getRecorded();
			assertEquals("equals", inputData, recorded);
		} finally {
			if (input != null) {
				IOUtils.closeQuietly(input);
			}
			if (recordingIS != null) {
				IOUtils.closeQuietly(recordingIS);
			}
		}
	}

}
