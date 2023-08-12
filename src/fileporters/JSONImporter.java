package fileporters;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import manage.*;

public class JSONImporter implements Importer {
    public void Import(String path, DatabaseManager m_dbManager, ArrayList<String> fileData) throws Exception {
        String jsonString = "";

        for (int i = 0; i < fileData.size(); i++) {
            jsonString += fileData.get(i);
        }

        ArrayList<String[]> rows = new ArrayList<String[]>();
        JSONParser parser = new JSONParser();

    
        Object obj = parser.parse(jsonString);

        JSONArray cardsArray = (JSONArray)(((JSONObject)obj).get("Cards"));

        @SuppressWarnings("unchecked")
        Iterator<JSONArray> iterator = cardsArray.iterator();
        while (iterator.hasNext()) {
            JSONArray tempJAr = iterator.next();
            @SuppressWarnings("unchecked")
            Iterator<String> jterator = tempJAr.iterator();

            String[] row = new String[5];

            row[0] = jterator.next();
            row[1] = jterator.next();
            row[2] = jterator.next();
            row[3] = jterator.next();
            row[4] = jterator.next();

            if (DatabaseManager.ValidateData(row) != null) {
                throw new Exception("There are to many columns or extracted Strings are not matching to patterns in one or more rows.", new Throwable("Invalid data!"));
            }
        }

        for (int i = 0; i < rows.size(); i++) {
            m_dbManager.AddRow(false, rows.get(i));
        }
    }
}