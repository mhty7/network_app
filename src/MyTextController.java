import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * 
 */

/**
 * @author mhty7
 *
 */
public class MyTextController implements DocumentListener{

	/**
	 * 
	 */
	public MyTextController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		final Document doc = arg0.getDocument();
		final Element ele = doc.getDefaultRootElement();
		int cnt = ele.getElementCount();
		
		

		
		if(cnt<MySetting.INITIAL_ROW_COUNT)
			return;
		
		SwingUtilities.invokeLater(new Runnable() {
		      @Override public void run() {
		        removeLines(doc, ele);
		        }
		});

		
		
		
		
		
	}
	

	private void removeLines(Document doc, Element ele) {
		Element rm = ele.getElement(0);
		
		try {
			doc.remove(0, rm.getEndOffset());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
