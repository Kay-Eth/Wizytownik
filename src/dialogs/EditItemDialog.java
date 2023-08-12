package dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import manage.DatabaseManager;

public class EditItemDialog extends DialogHandler {
	
	private JTextField nameField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField webField = new JTextField();
    private JTextField emailField = new JTextField();
	
	public EditItemDialog(DatabaseManager dbManager) {
		super(dbManager);
	}
	
	public void ShowDialog() {
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        DefaultComboBoxModel<String> nameDefComboBoxModel = new DefaultComboBoxModel<String>();
        for (int i = 0; i < m_dbManager.GetCardCount(); i++) {
            nameDefComboBoxModel.addElement((String)(m_dbManager.GetDataAt(i, 0)));
        }
        JComboBox<String> nameComboBox = new JComboBox<String>(nameDefComboBoxModel);
        nameComboBox.setSelectedIndex(m_dbManager.GetSelectedRows()[0]);
        nameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unchecked")
                JComboBox<String> cb = (JComboBox<String>)e.getSource();
                int index = cb.getSelectedIndex();
                nameField.setText((String)(m_dbManager.GetDataAt(index, 0)));
                addressField.setText((String)(m_dbManager.GetDataAt(index, 1)));
                phoneField.setText((String)(m_dbManager.GetDataAt(index, 2)));
                webField.setText((String)(m_dbManager.GetDataAt(index, 3)));
                emailField.setText((String)(m_dbManager.GetDataAt(index, 4)));
            }
        });

        panel.add(nameComboBox);

        panel.add(Box.createVerticalStrut(15)); // a spacer

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

        nameField.setText((String)(m_dbManager.GetDataAt(m_dbManager.GetSelectedRows()[0], 0)));
        addressField.setText((String)(m_dbManager.GetDataAt(m_dbManager.GetSelectedRows()[0], 1)));
        phoneField.setText((String)(m_dbManager.GetDataAt(m_dbManager.GetSelectedRows()[0], 2)));
        webField.setText((String)(m_dbManager.GetDataAt(m_dbManager.GetSelectedRows()[0], 3)));
        emailField.setText((String)(m_dbManager.GetDataAt(m_dbManager.GetSelectedRows()[0], 4)));

        int result = JOptionPane.showConfirmDialog(
            null,
            panel, 
            "Edit Item",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String validationMessage = DatabaseManager.ValidateData(nameField.getText(), addressField.getText(), phoneField.getText(), webField.getText(), emailField.getText());
            if (validationMessage == null) {
                try {
                    m_dbManager.EditRow(nameComboBox.getSelectedIndex(), nameField.getText(), addressField.getText(), phoneField.getText(), webField.getText(), emailField.getText());
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Failed to edit a row!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, validationMessage, "Failed to edit a row!", JOptionPane.ERROR_MESSAGE);
            }
        }
	}

}
