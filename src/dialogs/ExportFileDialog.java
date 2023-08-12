package dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import manage.FileExporter;
import manage.FileManager;

public class ExportFileDialog extends FileDialogHandler {

	public ExportFileDialog(FileManager fileManager) {
		super(fileManager);
	}

	public void ShowDialog() {
		JFileChooser fileDialog = new JFileChooser();
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        File file;
        int returnVal =  fileDialog.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileDialog.getSelectedFile();
            String extension = "";
            FileNameExtensionFilter filter = (FileNameExtensionFilter)fileDialog.getFileFilter();

            extension = filter.getExtensions()[0];

            String path = file.getAbsolutePath() + "." + extension;
            
            try {
                FileExporter.Export(path, extension, m_fileManager);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e, "The Export failed!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "The export was successful!", "EXPORT", JOptionPane.INFORMATION_MESSAGE);
        }
	}
}
