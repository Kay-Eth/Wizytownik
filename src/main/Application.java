package main;

import manage.*;

public class Application {
	// Main Window
	private Window m_window;

	// Managers
	private DatabaseManager m_databaseManager;
	private FileManager m_fileManager;
	
	public Application() {
		m_window = new Window(this);

		// Manager initialization
		m_databaseManager = new DatabaseManager(m_window.GetTableManager());
		m_fileManager = new FileManager(m_databaseManager);
		m_databaseManager.SetFileManager(m_fileManager);

		m_window.PrepareGUI();
	}
	
	public void Run() {
		m_window.ShowWindow();
	}

	public Window GetWindow() {
		return this.m_window;
	}

	public DatabaseManager GetDatabaseManager() {
		return this.m_databaseManager;
	}

	public FileManager GetFileManager() {
		return this.m_fileManager;
	}
}
