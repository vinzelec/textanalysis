package fr.vinze.textanalysis.matrix;

import fr.vinze.textanalysis.matrix.logentropy.LogEntropyMatrixBuilderTest;

public class CSVExporterTest {

	// not Junit... just displaying the result on standard out stream

	public static void main(String[] args) throws Exception {
		LogEntropyMatrixBuilderTest testcase = new LogEntropyMatrixBuilderTest();
		testcase.init();
		DocumentTokenMatrix<Double> matrix = testcase.buildMatrix();
		String csv = DocumentTokenMatrixCSVExporter.exportToCSV(matrix);
		System.out.println(csv);
	}

}
