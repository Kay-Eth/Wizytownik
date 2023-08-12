package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dialogs.IDialogHandler;

public class MenuListener implements ActionListener {
	
	private IDialogHandler m_iDialogHandler;
	
	public MenuListener(IDialogHandler dialogHandler) {
		this.m_iDialogHandler = dialogHandler;
	}
	
	public void actionPerformed(ActionEvent e) {
		this.m_iDialogHandler.ShowDialog();
	}

}
