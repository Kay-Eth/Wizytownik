package dialogs;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import manage.DatabaseManager;

public class DeleteItemsDialog extends DialogHandler {

	public DeleteItemsDialog(DatabaseManager dbManager) {
		super(dbManager);
	}

	@Override
	public void ShowDialog() {
        JPanel panel = new JPanel();
       
        panel.add(new JLabel("Are you sure you want to delete " + m_dbManager.GetSelectedRowsCount() + " row(s)?"));

        int result = JOptionPane.showConfirmDialog(
            null,
            panel, 
            "Add new Item",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
        	DeleteRows();
        }
	}
	
	public void DeleteRows() {
        int rows =m_dbManager.GetSelectedRowsCount();
        
        if (rows <= 0) {
            JOptionPane.showMessageDialog(null, "No rows selected!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        try {
            m_dbManager.DeleteRows(true);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e, "Failed to delete a row/rows!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
