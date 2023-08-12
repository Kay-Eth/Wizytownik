package manage;

import java.io.*;
import java.util.ArrayList;

import fileporters.*;

public class FileImporter {
    private static ArrayList<String> m_fileData;
    
    public static void LoadFileData(String path) throws IOException {
		CleanData();
	
		BufferedReader bReader = new BufferedReader(new FileReader(new File(path)));

		String line = null;

		while((line = bReader.readLine()) != null) {
			m_fileData.add(line);
		}

		bReader.close();
    }

    public static void Import(String path, String extension, FileManager fileManager) throws Exception {
    	LoadFileData(path);
    	
    	switch(extension) {
	        case "txt":
	            fileManager.SetImporter(new TEXTImporter());
	            break;
	
	        case "csv":
	        	fileManager.SetImporter(new CSVImporter());
	            break;
	
	        case "json":
	        	fileManager.SetImporter(new JSONImporter());
	            break;
    	}
    	
		fileManager.Import(path, m_fileData);
		CleanData();
	}
	
	public static void CleanData() {
		m_fileData = new ArrayList<String>(0);
	}
}