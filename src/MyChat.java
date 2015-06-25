import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;

/**
 * @author mhty7
 *
 */
public class MyChat extends JFrame implements Action {

	/**
	 * 
	 */
	private JPanel _upperBound;
	private JPanel _bottomBound;
	
	protected JTextArea _chatTextWindow;
	protected JTextArea _clientTextWindow;
	protected JTextField _inputField;
	
	private final static String[] MODE = {"Chat Window","Online Clients"};
	
	public MyChat() {
		JPanel wrap=new JPanel();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wrap.setLayout(new BoxLayout(wrap,BoxLayout.Y_AXIS));
		
		wrap.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		_upperBound=new JPanel();
		_upperBound.add(createUIFor(0));
		_upperBound.add(createUIFor(1));
		_upperBound.setLayout(new BoxLayout(_upperBound,BoxLayout.X_AXIS));
		_upperBound.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		_upperBound.setAlignmentY(JComponent.TOP_ALIGNMENT);
		
		_bottomBound = new JPanel();
		_bottomBound.add(createUIFor(2));
		_bottomBound.setLayout(new BoxLayout(_bottomBound,BoxLayout.X_AXIS));
		_bottomBound.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		_bottomBound.setAlignmentY(JComponent.TOP_ALIGNMENT);
		
		wrap.add(_upperBound);
		wrap.add(_bottomBound);

		
		
		add(wrap, BorderLayout.CENTER);
		

		pack();
		setVisible(true);
		
	}
	private JPanel createUIFor(int mode){
		switch(mode){
		case 0 :
			return createChatWindow(MODE[mode]);
			
		case 1 :
			return createOnlineWindow(MODE[mode]);
			
		case 2 :
			return createChatInput();
			
		default :
			break;	
		}
		return null;
		
	}
	

	
	
	private JPanel createChatWindow(String st){
		JPanel pane = new JPanel();
		pane.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		pane.setAlignmentY(JComponent.TOP_ALIGNMENT);
		
		//Dimension size = new Dimension(400,300);
		//pane.setMaximumSize(size);
		//pane.setPreferredSize(size);
		//pane.setMinimumSize(size);
		

		JLabel label = new JLabel(st);
		_chatTextWindow = new JTextArea(MySetting.INITIAL_ROW_COUNT,MySetting.INITIAL_COLOMN_COUNT_FOR_CHAT);
		JScrollPane tw=new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tw.setViewportView(_chatTextWindow);
		
		
		_chatTextWindow.getDocument().addDocumentListener(new MyTextController());
		
		_chatTextWindow.setEditable(false);
		//_chatTextWindow.setWrapStyleWord(true);
		_chatTextWindow.setLineWrap(true);
		

		
		
		pane.add(label);
		pane.add(Box.createRigidArea(new Dimension(0, 5)));
		pane.add(tw);
		label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		tw.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        return pane;
		
	}
	private JPanel createOnlineWindow(String st){
		JPanel pane = new JPanel();
		pane.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		pane.setAlignmentY(JComponent.TOP_ALIGNMENT);
		

		
		JLabel label = new JLabel(st);
		_clientTextWindow = new JTextArea(MySetting.INITIAL_ROW_COUNT,MySetting.INITIAL_COLOMN_COUNT_FOR_CLIENTS);
		JScrollPane tw=new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tw.setViewportView(_clientTextWindow);
		
		//_clientTextWindow.getDocument().addDocumentListener(new MyTextController());
		_clientTextWindow.setEditable(false);
		//_clientTextWindow.setWrapStyleWord(true);
		//_clientTextWindow.setLineWrap(true);
		
		pane.add(label);
		pane.add(Box.createRigidArea(new Dimension(0, 5)));
		pane.add(tw);
		label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		tw.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		

		pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        return pane;
	}
	private JPanel createChatInput(){
		JPanel pane = new JPanel();
		pane.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		pane.setAlignmentY(JComponent.TOP_ALIGNMENT);
		
		//Dimension size = new Dimension(400,50);
		//pane.setMaximumSize(size);
		//pane.setPreferredSize(size);
		//pane.setMinimumSize(size);
		
		_inputField = new JTextField();
		pane.add(_inputField);
		
		JButton btn = new JButton("SEND");
		btn.addActionListener(this);
		pane.add(btn);
		
		Keymap km = JTextComponent.addKeymap("newKeyMap", _inputField.getKeymap());
		km.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false),this);
		_inputField.setKeymap(km);
		
		pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));

        return pane;
	}
	
	
	public void scourClientWindow(){
		_clientTextWindow.setText("");
	}
	public void writeToWindow(String str,MySetting.Type type){
		switch (type){
		
		case CHAT_WINDOW :
			_chatTextWindow.append(str);
			break;
			
		case ONLINE_CLIENT :
			_clientTextWindow.append(str);
			break;
		
			
		default :
			break;
		}
		
		
	}
	
	
	//function to be overridden
	protected void sendMessage(String msg){
	}

	
	@Override
	public void actionPerformed(ActionEvent e){
		String msg = _inputField.getText();
		_inputField.setText("");
		
		if(msg.length()>0){
			sendMessage(msg);	
			
		}
	}
	
	
	@Override
	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
