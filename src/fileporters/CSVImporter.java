package fileporters;

import java.util.ArrayList;
import manage.*;

import javax.swing.*;
import java.awt.event.*;

public class CSVImporter implements Importer {
    public void Import(String path, DatabaseManager m_dbManager, ArrayList<String> fileData) throws Exception {
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
            "Import as CSV",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String separator = field.getText();
            
            m_dbManager.CleanTable();
            ArrayList<String[]> rows = new ArrayList<String[]>();

            for (int i = 0; i < fileData.size(); i++) {
                String[] parts = fileData.get(i).split(separator);

                if (parts.length == Card.size && DatabaseManager.ValidateData(parts) == null) {
                    rows.add(parts);
                }
                else
                {
                    throw new Exception("There are to many columns or extracted Strings are not matching to patterns in one or more rows.", new Throwable("Invalid data!"));
                }
            }

            for (int i = 0; i < rows.size(); i++) {
                m_dbManager.AddRow(false, rows.get(i));
            }
        }
    }
}