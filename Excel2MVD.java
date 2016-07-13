package nl.tue.generator;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import nl.tue.generator.ImportExcel;
import nl.tue.generator.IfcSupportRule;

public class Excel2MVD {

	private String inputFileExcel;
	private String inputIfc;
	private String outputMvdFile;
	

	public void convert() throws ParserConfigurationException, SAXException, URISyntaxException, IOException {
		// Define Path Excel File
		ImportExcel ie = new ImportExcel(inputFileExcel);

		ArrayList<ArrayList<String>> rows = ie.extract();
		rows.remove(0);

		// Do not touch --> basis mvdXML file.
		AdjustmvdXML adjustmvdXML = new AdjustmvdXML(Excel2MVD.class.getResourceAsStream("Basis.mvdxml"));

		for (ArrayList<String> row : rows) {
			if (row.get(1).contains("Yes")) {
				Rule res = IfcSupportRule.parse(row.get(2));
				adjustmvdXML.apply(res);
			}

			if (row.get(2).length() > 0) {
			}

		}

		
		adjustmvdXML.generateMvd(getOutputMvdFile());
	}

	public String getInputIfc() {
		return inputIfc;
	}

	public void setInputIfc(String inputIfc) {
		this.inputIfc = inputIfc;
	}

	public String getInputFileExcel() {
		return inputFileExcel;
	}

	public void setInputFileExcel(String inputFileExcel) {
		this.inputFileExcel = inputFileExcel;
	}


	public String getOutputMvdFile() {
		return outputMvdFile;
	}

	public void setOutputMvdFile(String outputMvdFile) {
		this.outputMvdFile = outputMvdFile;
	}

}
