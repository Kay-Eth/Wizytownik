package manage;

import java.io.IOException;

public interface Exporter {
    public void Export(String path, DatabaseManager dbManager) throws IOException;
}