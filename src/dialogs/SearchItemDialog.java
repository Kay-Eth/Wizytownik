package dialogs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import manage.DatabaseManager;

public class SearchItemDialog extends DialogHandler {
	
	private JTable m_searchTable;

    private JTextField nameField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField webField = new JTextField();
    private JTextField emailField = new JTextField();
	
	public SearchItemDialog(DatabaseManager dbManager) {
		super(dbManager);
	}

	public void ShowDialog() {
		JPanel mainPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel fieldsPanel = new JPanel();
        JPanel tablePanel = new JPanel();

        // fieldsPanel JPanels
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.Y_AXIS));

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.Y_AXIS));

        JPanel webPanel = new JPanel();
        webPanel.setLayout(new BoxLayout(webPanel, BoxLayout.Y_AXIS));

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));

        // Search button
        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Search();
            }
        });
        buttonPanel.add(searchButton);

        // JTextFields
        nameField.setPreferredSize(new Dimension(120, 20));
        addressField.setPreferredSize(new Dimension(120, 20));
        phoneField.setPreferredSize(new Dimension(120, 20));
        webField.setPreferredSize(new Dimension(120, 20));
        emailField.setPreferredSize(new Dimension(120, 20));
        
        // Contruct fieldsPanel
        namePanel.add(new JLabel("Name"));
        namePanel.add(nameField);

        addressPanel.add(new JLabel("Address"));
        addressPanel.add(addressField);

        phonePanel.add(new JLabel("Phone"));
        phonePanel.add(phoneField);

        webPanel.add(new JLabel("Web"));
        webPanel.add(webField);

        emailPanel.add(new JLabel("Email"));
        emailPanel.add(emailField);

        fieldsPanel.add(namePanel);
        fieldsPanel.add(addressPanel);
        fieldsPanel.add(phonePanel);
        fieldsPanel.add(webPanel);
        fieldsPanel.add(emailPanel);

        DefaultTableModel def_model = new DefaultTableModel();
        def_model.addColumn("NAME");
        def_model.addColumn("ADDRESS");
        def_model.addColumn("PHONE");
        def_model.addColumn("WEB");
        def_model.addColumn("EMAIL");
        m_searchTable = new JTable(def_model);
        m_searchTable.setDefaultEditor(Object.class, null);
        m_searchTable.setAutoCreateRowSorter(true);
        m_searchTable.getRowSorter().toggleSortOrder(0);
        
        JScrollPane m_tableScrollPane = new JScrollPane(m_searchTable);
        m_tableScrollPane.setPreferredSize(new Dimension(600, 400));
        tablePanel.add(m_tableScrollPane);

        panel.add(fieldsPanel);
        panel.add(buttonPanel);
        panel.add(tablePanel);

        mainPanel.add(panel);

        int result = JOptionPane.showConfirmDialog(
            null,
            mainPanel, 
            "Search",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            
        }
	}

	public void Search() {
        DefaultTableModel resultModel = new DefaultTableModel();
        resultModel.addColumn("NAME");
        resultModel.addColumn("ADDRESS");
        resultModel.addColumn("PHONE");
        resultModel.addColumn("WEB");
        resultModel.addColumn("EMAIL");

        for (int i = 0; i < m_dbManager.GetCardCount(); i++) {
            boolean[] checkField = {false, false, false, false, false};

            if (!nameField.getText().isEmpty()) {
                if (DatabaseManager.ValidateName(nameField.getText()))
                    checkField[0] = true;
                else
                    JOptionPane.showMessageDialog(null, "Text in Name field is invalid", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (!addressField.getText().isEmpty()) {
                checkField[1] = true;
            }
            if (!phoneField.getText().isEmpty()) {
                if (DatabaseManager.ValidatePhone(phoneField.getText()))
                    checkField[2] = true;
                else
                    JOptionPane.showMessageDialog(null, "Text in Phone field is invalid", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (!webField.getText().isEmpty()) {
                if (DatabaseManager.ValidateWeb(webField.getText()))
                    checkField[3] = true;
                else
                    JOptionPane.showMessageDialog(null, "Text in Web field is invalid", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (!emailField.getText().isEmpty()) {
                if (DatabaseManager.ValidateEmail(emailField.getText()))
                    checkField[4] = true;
                else
                    JOptionPane.showMessageDialog(null, "Text in Email field is invalid", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            if (!(checkField[0] || checkField[1] || checkField[2] || checkField[3] || checkField[4]))
            {
                System.out.println("Empty");
                continue;
            }

            String[] checkRow = new String[5];
            checkRow[0] = (String)(m_dbManager.GetDataAt(i, 0));
            checkRow[1] = (String)(m_dbManager.GetDataAt(i, 1));
            checkRow[2] = (String)(m_dbManager.GetDataAt(i, 2));
            checkRow[3] = (String)(m_dbManager.GetDataAt(i, 3));
            checkRow[4] = (String)(m_dbManager.GetDataAt(i, 4));

            if (checkField[0]) {
                if (checkRow[0].indexOf(nameField.getText()) == -1)
                    continue;
            }
            if (checkField[1]) {
                if (checkRow[1].indexOf(addressField.getText()) == -1)
                    continue;
            }
            if (checkField[2]) {
                if (checkRow[2].indexOf(phoneField.getText()) == -1)
                    continue;
            }
            if (checkField[3]) {
                if (checkRow[3].indexOf(webField.getText()) == -1)
                    continue;
            }
            if (checkField[4]) {
                if (checkRow[4].indexOf(emailField.getText()) == -1)
                    continue;
            }

            resultModel.addRow(checkRow);
        }

        m_searchTable.setModel(resultModel);
    }
}
