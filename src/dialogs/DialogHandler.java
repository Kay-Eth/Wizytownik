package dialogs;

import manage.DatabaseManager;

public abstract class DialogHandler implements IDialogHandler {
	
	protected DatabaseManager m_dbManager;
	
	DialogHandler(DatabaseManager dbManager) {
		this.m_dbManager = dbManager;
	}
}
