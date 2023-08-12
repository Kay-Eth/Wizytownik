package manage;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableManager {
    private JTable m_jTable;

    public static String columns[] = {"NAME", "ADDRESS", "PHONE", "WEB", "E-MAIL"};

    public TableManager() {
        assert(columns.length == Card.size);
        this.m_jTable = new JTable();
        DefaultTableModel defModel = new DefaultTableModel(null, columns);
        SetDefaultTableModel(defModel);
        this.m_jTable.setDefaultEditor(Object.class, null);
		this.m_jTable.getTableHeader().setReorderingAllowed(false);
    }

    public DefaultTableModel GetDefaultTableModel() {
        return (DefaultTableModel)(this.m_jTable.getModel());
    }

    public void SetDefaultTableModel(DefaultTableModel defaultTableModel) {
        this.m_jTable.setModel(defaultTableModel);
    }

    public JTable GetJTable() {
        return this.m_jTable;
    }

    public void CleanTable() {
        ((DefaultTableModel)(this.m_jTable.getModel())).setRowCount(0);
    }

    public int[] GetSelectedRows() {
        return this.m_jTable.getSelectedRows();
    }

    public void AddCard(Card card) {
        this.GetDefaultTableModel().addRow(card.GetAsStringArray());
    }
}