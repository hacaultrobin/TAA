package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.command.ICommand;

public class ChatUI implements IChatUI {

	private ICommand unregister;
	private ICommand postMessage;
	
	private String title = "Logiciel de discussion en ligne";
	private JFrame window = new JFrame(this.title);
	private JTextArea txtOutput = new JTextArea();
	private JTextField txtMessage = new JTextField();
	private JButton btnSend = new JButton("Envoyer");
	
	public ChatUI() {
		JPanel panel = (JPanel) this.window.getContentPane();
		JScrollPane sclPane = new JScrollPane(txtOutput);
		panel.add(sclPane, BorderLayout.CENTER);
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(this.txtMessage, BorderLayout.CENTER);
		southPanel.add(this.btnSend, BorderLayout.EAST);
		panel.add(southPanel, BorderLayout.SOUTH);

		// Gestion des évènements
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				window_windowClosing(e);
			}
		});
		btnSend.addActionListener(e -> btnSend_actionPerformed(e));
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				if (event.getKeyChar() == '\n')
					btnSend_actionPerformed(null);
			}
		});

		// Initialisation des attributs
		this.txtOutput.setBackground(new Color(220, 220, 220));
		this.txtOutput.setEditable(false);
		this.window.setSize(500, 400);
		this.window.setVisible(true);
		this.txtMessage.requestFocus();
	}

	@Override
	public void window_windowClosing(WindowEvent e) {
		if (unregister != null) {
			unregister.execute();			
		}
		System.exit(-1);
	}

	@Override
	public void btnSend_actionPerformed(ActionEvent e) {
		if (postMessage != null) {
			postMessage.execute();
			this.txtMessage.setText("");
			this.txtMessage.requestFocus();
		}		
	}
	
    @Override
	public void displayMessage(String message){
        this.txtOutput.append(message + "\n");
        this.txtOutput.moveCaretPosition(this.txtOutput.getText().length());
    }

    @Override
	public String requestPseudo() {
         String pseudo = JOptionPane.showInputDialog(
                this.window, "Entrez votre pseudo : ",
                this.title,  JOptionPane.OK_OPTION
        );        
        return pseudo;
    }

	@Override
	public String getMessage() {
		return this.txtMessage.getText();
	}

	@Override
	public void setUnregisterCmd(ICommand unregister) {
		this.unregister = unregister;
	}
	
	@Override
	public void setPostMessageCmd(ICommand postMessage) {
		this.postMessage = postMessage;
	}

}
