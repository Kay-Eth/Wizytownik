package manage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import fileporters.*;

public class FileManager {
    public File currentFile = null;

    public DatabaseManager m_dbManager;
    
    private Importer m_importer;
    private Exporter m_exporter;

    public static enum FILE_TYPE {
        TXT,
        CSV,
        JSON
    };

    public FileManager(DatabaseManager dbManager) {
        m_dbManager = dbManager;
    }

    public void Import(String path, ArrayList<String> fileData) throws Exception {
        currentFile = null;
        currentFile = new File(path);
        m_dbManager.CleanTable();

        m_importer.Import(path, m_dbManager, fileData);

        m_dbManager.Sort(0, false);
    }

    public void Export(String path) throws IOException {
        m_exporter.Export(path, m_dbManager);
    }

    public void Update() throws IOException {
        if (currentFile == null) {
            return;
        }
        String extension = "";

        int i = currentFile.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            extension = currentFile.getAbsolutePath().substring(i+1);
        }

        switch(extension) {
            case "txt":
                SetExporter(new TEXTExporter());
                break;

            case "csv":
            	SetExporter(new CSVExporter());
                break;

            case "json":
            	SetExporter(new JSONExporter());
                break;
        }

        Export(currentFile.getAbsolutePath());
    }

    public void SetCurrentFile(File file) {
        currentFile = file;
    }
    
    public void SetImporter(Importer importer) {
    	this.m_importer = importer;
    }
    
    public void SetExporter(Exporter exporter) {
    	this.m_exporter = exporter;
    }
}