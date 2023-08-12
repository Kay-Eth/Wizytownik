package fileporters;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import manage.*;

public class CSVExporter implements Exporter {
    public void Export(String path, DatabaseManager dbManager) throws IOException {
        //SEPARATOR
        JPanel panel = new JPanel();

        panel.add(new JLabel("Separator:"));
        
        JTextField field = new JTextField(1);
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) { 
                if (field.getText().length() >= 1 ) // limit textfield to 3 characters
                    e.consume(); 
            } 
        });
        panel.add(field);

        int result = JOptionPane.showConfirmDialog(
            null,
            panel, 
            "Export as CSV",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String separator = field.getText();
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(path));

            for (int row = 0; row < dbManager.GetCardCount(); row++) {
                String entry = "";
                
                entry += dbManager.GetDataAt(row, 0);
                entry += separator;
                entry += dbManager.GetDataAt(row, 1);
                entry += separator;
                entry += dbManager.GetDataAt(row, 2);
                entry += separator;
                entry += dbManager.GetDataAt(row, 3);
                entry += separator;
                entry += dbManager.GetDataAt(row, 4);

                bWriter.write(entry);
                bWriter.newLine();
            }

            bWriter.close();
        }
    }
}