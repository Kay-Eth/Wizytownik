package fileporters;

import java.io.*;

import manage.*;

public class JSONExporter implements Exporter {
    public void Export(String path, DatabaseManager dbManager) throws IOException {
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(path));

        bWriter.write("{");
        bWriter.newLine();
        bWriter.write("\t\"Cards\":[");
        bWriter.newLine();

        for (int row = 0; row < dbManager.GetCardCount(); row++) {
            bWriter.write("\t\t[");
            bWriter.newLine();
            
            for (int i = 0; i < 5; i++) {
                bWriter.write("\t\t\t\"" + dbManager.GetDataAt(row, i) + "\"");
                if (i != 4) bWriter.write(",");
                bWriter.newLine();
            }

            bWriter.write("\t\t]");
            if (row != dbManager.GetCardCount() - 1) bWriter.write(",");

            bWriter.newLine();
        }

        bWriter.write("\t]\n}\n");

        bWriter.close();
    }
}