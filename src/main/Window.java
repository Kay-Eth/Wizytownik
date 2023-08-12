package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import manage.TableManager;
import dialogs.*;

public final class Window {
	//Application reference
	private Application m_app;

	// Window Settings
	private int width, height;
	
	// GUI Components
	private JFrame m_frame;
	
	private JMenu m_file;
	private JMenu m_edit;
	private JMenu m_view;
	
	private JMenuItem m_newFile;
	private JMenuItem m_import;
	private JMenuItem m_export;
	private JMenuItem m_exit;
	
	private JMenuItem m_addItem;
	private JMenuItem m_editItem;
	private JMenuItem m_deleteItem;

	private JMenuItem m_searchItems;
	private JMenuItem m_sortItems;
	
	private JScrollPane m_tableScrollPane;

	private TableManager m_tableManager;

	public Window(Application app) {
		width = 800;
		height = 600;

		m_app = app;
		m_tableManager = new TableManager();
	}

	public Window(Application app, int window_width, int window_height) {
		width = window_width;
		height = window_height;

		m_app = app;
		m_tableManager = new TableManager();
	}

	public void SetTitleInfo(String info) {
		m_frame.setTitle("Wizytownik - " + info);
	}
	
	public void PrepareGUI() {
		// FRAME
		m_frame = new JFrame("Wizytownik By Kajetan Kuszczynski");
		m_frame.setSize(width, height);
		
		m_frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
	        	 System.exit(0);
	        }        
	    });
		
		// Create menu bar
		m_file = new JMenu("File");
		m_edit = new JMenu("Edit");
		m_view = new JMenu("View");
		JMenuBar mb = new JMenuBar();
		
		// FILE
		m_newFile = new JMenuItem("New File");
		m_newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_app.GetFileManager().currentFile = null;
				m_frame.setTitle("Wizytownik");
				m_app.GetDatabaseManager().CleanTable();
			}
		});
		m_file.add(m_newFile);

		m_file.addSeparator();

		m_import = new JMenuItem("Import File");
		m_import.addActionListener(new MenuListener(new ImportFileDialog(m_app.GetFileManager(), this)));
		m_file.add(m_import);
		
		m_export = new JMenuItem("Export File");
		m_export.addActionListener(new MenuListener(new ExportFileDialog(m_app.GetFileManager())));
		m_file.add(m_export);
		
		m_file.addSeparator();

		m_exit = new JMenuItem("Exit");
		m_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_frame.dispatchEvent(new WindowEvent(m_frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		m_file.add(m_exit);
		
		// EDIT
		m_addItem = new JMenuItem("Add Row");
		m_addItem.addActionListener(new MenuListener(new AddItemDialog(m_app.GetDatabaseManager())));
		m_edit.add(m_addItem);
		
		m_editItem = new JMenuItem("Edit Row");
		m_editItem.addActionListener(new MenuListener(new EditItemDialog(m_app.GetDatabaseManager())));
		m_edit.add(m_editItem);
		
		m_deleteItem = new JMenuItem("Delete selected Rows");
		m_deleteItem.addActionListener(new MenuListener(new DeleteItemsDialog(m_app.GetDatabaseManager())));
		m_edit.add(m_deleteItem);

		// VIEW
		m_searchItems = new JMenuItem("Search");
		m_searchItems.addActionListener(new MenuListener(new SearchItemDialog(m_app.GetDatabaseManager())));
		m_view.add(m_searchItems);

		m_view.addSeparator();

		m_sortItems = new JMenuItem("Sort");
		m_sortItems.addActionListener(new MenuListener(new SortItemsDialog(m_app.GetDatabaseManager())));
		m_view.add(m_sortItems);
		
		// BAR
		mb.add(m_file);
		mb.add(m_edit);
		mb.add(m_view);
		
		m_frame.setJMenuBar(mb);
		
		m_tableScrollPane = new JScrollPane(m_tableManager.GetJTable());
		
		m_frame.getContentPane().add(BorderLayout.CENTER, m_tableScrollPane);
	}
	
	public void ShowWindow() {
		m_frame.setVisible(true);
	}

	public TableManager GetTableManager() {
		return m_tableManager;
	}
}
