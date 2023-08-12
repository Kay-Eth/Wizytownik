package manage;

import java.util.ArrayList;

public interface Importer {
    public void Import(String path, DatabaseManager m_dbManager, ArrayList<String> fileData) throws Exception;
}