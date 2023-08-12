package manage;

import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseManager {
	private FileManager m_fileManager;
	private TableManager m_tableManager;

	private ArrayList<Card> m_cardsDataBase;
	
	public DatabaseManager(TableManager tableManager) {
		this.m_tableManager = tableManager;
		m_cardsDataBase = new ArrayList<Card>(0);
	}

	public void SetFileManager(FileManager fileManager) {
		m_fileManager = fileManager;
	}

	private int Partition(String arr[], int left, int right) { 
		  int i = left, j = right; 
		  String tmp; 
		  String pivot = arr[(left + right) / 2]; 
		  
		  while (i <= j) { 
				while(arr[i].compareToIgnoreCase(pivot) > 0)
					i++;
				while(arr[j].compareToIgnoreCase(pivot) < 0)
					j--;
				if (i <= j) {
					tmp = arr[i];
					arr[i] = arr[j];
					arr[j] = tmp;
					i++;
					j--;
				}
		  }; 
		  
		  return i; 
	} 
	 
	private void QuickSort(String arr[], int left, int right) { 
		  int index = Partition(arr, left, right); 
		  if (left < index - 1) 
				QuickSort(arr, left, index - 1); 
		  if (index < right) 
				QuickSort(arr, index, right); 
	}

	public void Sort(int column, boolean descending) {
		ArrayList<Card> sortedDB = new ArrayList<Card>(0);

		String[][] table = new String[m_cardsDataBase.size()][Card.size];
		for (int i = 0; i < m_cardsDataBase.size(); i++) {
			table[i] = m_cardsDataBase.get(i).GetAsStringArray().clone();
		}

		HashMap<String, String[]> tableMap = new HashMap<String, String[]>();

		for (int i = 0; i < m_cardsDataBase.size(); i++) {
			tableMap.put(table[i][column], table[i]);
		}

		String[] compareArray = new String[m_cardsDataBase.size()];
		for (int i = 0; i < compareArray.length; i++) {
			compareArray[i] = table[i][column];
		}

		QuickSort(compareArray, 0, compareArray.length - 1);

		if (descending) {
			for (int i = 0; i < compareArray.length; i++) {
				String[] row = tableMap.get(compareArray[i]);
				
				sortedDB.add(new Card(row));
			}
		} else {
			for (int i = compareArray.length - 1; i > -1; i--) {
				String[] row = tableMap.get(compareArray[i]);
				
				sortedDB.add(new Card(row));
			}
		}

		m_cardsDataBase = sortedDB;

		UpdateTable();
	}
	
	public static String ValidateData(String name, String address, String phone, String web, String e_mail) {
		String errorMessage = "";

		if (name.isEmpty()) {
			errorMessage += "Name cannot be empty\n";
		}
		if (!ValidateName(name)) {
			errorMessage += "Name can contain only non-numeric characters\n";
		}
		if (address.isEmpty()) {
			errorMessage += "Address cannot be empty\n";
		}
		if (phone.isEmpty()) {
			errorMessage += "Phone number cannot be empty\n";
		}
		if (!ValidatePhone(phone)) {
			errorMessage += "Phone number can contain only 0-9, +, (, ), x, # and space characters\n";
		}
		if (web.isEmpty()) {
			errorMessage += "Web address cannot be empty\n";
		}
		if (!ValidateWeb(web)) {
			errorMessage += "Web address is invalid\n";
		}
		if (e_mail.isEmpty()) {
			errorMessage += "Email address cannot be empty\n";
		}
		if (!ValidateEmail(e_mail)) {
			errorMessage += "Email address is invalid\n";
		}

		if (errorMessage.isEmpty())
			return null;
		else
			return errorMessage;
	}
	public static String ValidateData(String[] row) {
		return ValidateData(row[0], row[1], row[2], row[3], row[4]);
	}
	public static String ValidateData(Card card) {
		return ValidateData(card.GetAsStringArray());
	}

	public static boolean ValidateName(String name) {
		return name.matches("^[^0-9]+$");
	}
	public static boolean ValidatePhone(String phone) {
		return phone.matches("^[0-9\\+x()# ]+$");
	}
	public static boolean ValidateWeb(String web) {
		return web.matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
	}
	public static boolean ValidateEmail(String email) {
		return email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
	}

	public void AddRow(boolean update, String[] row) throws Exception {
		m_cardsDataBase.add(new Card(row));

		try {
			if (update)	this.UpdateFile();
		} catch(IOException e) {
			m_cardsDataBase.remove(m_cardsDataBase.size()-1);
			throw e;
		}

		UpdateTable();
	}
	
	public void EditRow(int row, String name, String address, String phone, String web, String e_mail) throws Exception {
		Card backupCard = new Card(m_cardsDataBase.get(row).GetAsStringArray());
		m_cardsDataBase.get(row).SetData(new String[] {name, address, phone, web, e_mail});

		try {
			this.UpdateFile();
		} catch(IOException e) {
			m_cardsDataBase.get(row).SetData(backupCard.GetAsStringArray());
			throw e;
		}

		UpdateTable();
	}
	
	public void DeleteRows(boolean update) throws Exception {
		ArrayList<Card> backupList = new ArrayList<Card>(0);

		int[] rows = this.m_tableManager.GetSelectedRows();

		try {
			for (int i = 0; i < rows.length; i++) {
				backupList.add(new Card(m_cardsDataBase.get(i).GetAsStringArray()));
				m_cardsDataBase.remove(rows[i] - i);
			}
		} catch (IndexOutOfBoundsException e) {
			for (int i = 0; i < backupList.size(); i++) {
				m_cardsDataBase.add(new Card(backupList.get(i).GetAsStringArray()));
			}
			throw e;
		}
		
		try {
			if (update)	this.UpdateFile();
		} catch(IOException e) {
			try {
				this.UpdateFile();
			} catch(IOException f) {
				for (int i = 0; i < backupList.size(); i++) {
					m_cardsDataBase.add(new Card(backupList.get(i).GetAsStringArray()));
				}
				try {
					this.UpdateFile();
				} catch (IOException g) {
					System.out.println("Failed to restore file after UpdateFile has failed.");
					throw new Exception("Failed to update file and to restore file.", new Throwable("CRITICAL ERROR"));
				}
				throw f;
			}
		}

		UpdateTable();
	}

	public void CleanTable() {
		this.m_cardsDataBase.clear();
		this.m_tableManager.CleanTable();
	}

	public void UpdateTable() {
		this.m_tableManager.CleanTable();
		
		for (int i = 0; i < m_cardsDataBase.size(); i++) {
			this.m_tableManager.AddCard(m_cardsDataBase.get(i));
		}
	}

	private void UpdateFile() throws IOException {
		this.m_fileManager.Update();
	}

	public int GetCardCount() {
		return m_cardsDataBase.size();
	}

	public String GetDataAt(int row, int column) {
		return m_cardsDataBase.get(row).GetAsStringArray()[column];
	}

	public int[] GetSelectedRows() {
		return m_tableManager.GetJTable().getSelectedRows();
	}
	
	public int GetSelectedRowsCount() {
		return m_tableManager.GetJTable().getSelectedRowCount();
	}
}
