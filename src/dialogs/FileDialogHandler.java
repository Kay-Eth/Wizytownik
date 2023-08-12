package dialogs;

import manage.FileManager;

public abstract class FileDialogHandler implements IDialogHandler {
	protected FileManager m_fileManager;
	
	FileDialogHandler(FileManager fileManager) {
		this.m_fileManager = fileManager;
	}
}
