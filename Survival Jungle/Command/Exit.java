package Command;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * 
 * @author Bitnarae Kim
 * The Exit class shows a JPanel that gives an option to exit the game.
 *
 */
public class Exit extends Command {

	@Override
	public JPanel execute() {
		// TODO Auto-generated method stub
		
		int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Confirm exit", JOptionPane.YES_NO_OPTION);
		if (i == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if(i == JOptionPane.NO_OPTION){
			System.out.println("No button clicked");
		} else if (i == JOptionPane.CANCEL_OPTION){
			System.out.println("Cancel button clicked");
		}
		return null;
	}

}
