package dialogs;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Window;
import manage.FileManager;
import manage.FileImporter;

public class ImportFileDialog extends FileDialogHandler {

    private Window m_window;

    public ImportFileDialog(FileManager fileManager, Window window) {
        super(fileManager);
        this.m_window = window;
    }
	
	public void ShowDialog() {
		JFileChooser fileDialog = new JFileChooser();
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        File file;
        int returnVal =  fileDialog.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileDialog.getSelectedFile();
            String extension = "";

            int i = file.getAbsolutePath().lastIndexOf('.');
            if (i > 0) {
                extension = file.getAbsolutePath().substring(i+1);
            }
            
            try {
                FileImporter.Import(file.getAbsolutePath(), extension, m_fileManager);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "The Import failed!", JOptionPane.ERROR_MESSAGE);
                FileImporter.CleanData();
                return;
            }

            m_window.SetTitleInfo(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "The Import was successful!", "IMPORT", JOptionPane.INFORMATION_MESSAGE);
        }
	}

}
