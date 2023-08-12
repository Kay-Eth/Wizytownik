package fileporters;

import java.io.*;

import manage.*;

public class TEXTExporter implements Exporter {
    public void Export(String path, DatabaseManager dbManager) throws IOException {
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(path));

        for (int row = 0; row < dbManager.GetCardCount(); row++) {
            String entry = "";
            
            entry += dbManager.GetDataAt(row, 0);
            entry += "\t";
            entry += dbManager.GetDataAt(row, 1);
            entry += "\t";
            entry += dbManager.GetDataAt(row, 2);
            entry += "\t";
            entry += dbManager.GetDataAt(row, 3);
            entry += "\t";
            entry += dbManager.GetDataAt(row, 4);

            bWriter.write(entry);
            bWriter.newLine();
        }

        bWriter.close();
    }
}