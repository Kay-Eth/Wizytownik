package dialogs;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import manage.DatabaseManager;

public class AddItemDialog extends DialogHandler {
	
	public AddItemDialog(DatabaseManager dbManager) {
		super(dbManager);
	}

	public void ShowDialog() {
		JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField webField = new JTextField();
        JTextField emailField = new JTextField();
  
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(Box.createVerticalStrut(15)); // a spacer
        
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        panel.add(Box.createVerticalStrut(15)); // a spacer

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(Box.createVerticalStrut(15)); // a spacer

        panel.add(new JLabel("Website:"));
        panel.add(webField);

        panel.add(Box.createVerticalStrut(15)); // a spacer

        panel.add(new JLabel("E-mail:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(
            null,
            panel, 
            "Add new Item",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String validationMessage = DatabaseManager.ValidateData(nameField.getText(), addressField.getText(), phoneField.getText(), webField.getText(), emailField.getText());
            if (validationMessage == null) {
                try {
                    m_dbManager.AddRow(true, new String[] {nameField.getText(), addressField.getText(), phoneField.getText(), webField.getText(), emailField.getText()});
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Failed to add a row!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, validationMessage, "Failed to add a row!", JOptionPane.ERROR_MESSAGE);
            }
        }
	}

}
