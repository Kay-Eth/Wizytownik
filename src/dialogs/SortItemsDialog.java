package dialogs;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import manage.DatabaseManager;

public class SortItemsDialog extends DialogHandler {

	public SortItemsDialog(DatabaseManager dbManager) {
		super(dbManager);
	}

	public void ShowDialog() {
		JPanel panel = new JPanel();

        JRadioButton nameRB = new JRadioButton("Name", true);
        JRadioButton addressRB = new JRadioButton("Address");
        JRadioButton phoneRB = new JRadioButton("Phone");
        JRadioButton webRB = new JRadioButton("Web");
        JRadioButton emailRB = new JRadioButton("Email");

        ButtonGroup rbGroup = new ButtonGroup();

        rbGroup.add(nameRB);
        rbGroup.add(addressRB);
        rbGroup.add(phoneRB);
        rbGroup.add(webRB);
        rbGroup.add(emailRB);

        JCheckBox descCB = new JCheckBox("Descending", false);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Sort by:"));
        
        panel.add(nameRB);
        panel.add(addressRB);
        panel.add(phoneRB);
        panel.add(webRB);
        panel.add(emailRB);

        panel.add(descCB);

        int result = JOptionPane.showConfirmDialog(
            null,
            panel, 
            "Sort Items",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            if (nameRB.isSelected())
                m_dbManager.Sort(0, descCB.isSelected());
            else if (addressRB.isSelected())
                m_dbManager.Sort(1, descCB.isSelected());
            else if (phoneRB.isSelected())
                m_dbManager.Sort(2, descCB.isSelected());
            else if (webRB.isSelected())
                m_dbManager.Sort(3, descCB.isSelected());
            else if (emailRB.isSelected())
                m_dbManager.Sort(4, descCB.isSelected());
            else
                System.out.println("FATAL ERROR");
        }
	}

}
