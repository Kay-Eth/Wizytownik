package manage;

import java.io.IOException;

import fileporters.*;

public class FileExporter {
    public static void Export(String path, String extension, FileManager fileManager) throws IOException {
    	switch(extension) {
        case "txt":
            fileManager.SetExporter(new TEXTExporter());
            break;

        case "csv":
        	fileManager.SetExporter(new CSVExporter());
            break;

        case "json":
        	fileManager.SetExporter(new JSONExporter());
            break;
    	}
    	
    	fileManager.Export(path);
    }
}