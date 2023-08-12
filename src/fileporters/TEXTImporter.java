package fileporters;

import java.util.ArrayList;
import manage.*;

public class TEXTImporter implements Importer {
    public void Import(String path, DatabaseManager m_dbManager, ArrayList<String> fileData) throws Exception {
        m_dbManager.CleanTable();
        ArrayList<String[]> rows = new ArrayList<String[]>();

        for (int i = 0; i < fileData.size(); i++) {
            String[] parts = fileData.get(i).split("\t");

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