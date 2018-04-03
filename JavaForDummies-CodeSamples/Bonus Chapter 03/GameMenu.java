import javax.swing.*;
import java.awt.event.*;

public class GameMenu extends JFrame
{
	public static void main(String [] args)
	{
		new GameMenu();
	}

	JMenuItem newItem, pauseItem, quitItem, exitItem;
	JCheckBoxMenuItem soundItem, musicItem;

	public GameMenu()
	{
	    this.setSize(600,400);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(buildMenu());

	    this.setVisible(true);
	}

	private JMenuBar buildMenu()
	{
		MenuListener ml = new MenuListener();

		JMenuBar menuBar = new JMenuBar();

		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		menuBar.add(gameMenu);

		JMenu optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic('O');
		menuBar.add(optionsMenu);

		newItem = addItem(gameMenu,
			"New Game", 'N', 0, ml);
		pauseItem = addItem(gameMenu,
			"Pause Game", 'P', 0, ml);
		quitItem = addItem(gameMenu,
			"Quit Game", 'Q', 0, ml);
		gameMenu.addSeparator();
		exitItem = addItem(gameMenu,
			"Exit", 'x', 0, ml);

		soundItem = addOption(optionsMenu,
			"Sound", 'S', 0, ml);
		musicItem = addOption(optionsMenu,
			"Music", 'M', 0, ml);

		return menuBar;
	}

	private JMenuItem addItem(JMenu menu,
		String title, char key, int pos,
		ActionListener listener)
	{
		JMenuItem item = new JMenuItem(title, key);
		if (pos > 0)
		    item.setDisplayedMnemonicIndex(pos);
		item.addActionListener(listener);
		menu.add(item);
		return item;
	}

	private JCheckBoxMenuItem addOption(JMenu menu,
		String title, char key, int pos,
		ActionListener listener)
	{
		JCheckBoxMenuItem item =
			new JCheckBoxMenuItem(title);
		item.setMnemonic(key);
		if (pos > 0)
		    item.setDisplayedMnemonicIndex(pos);
		item.addActionListener(listener);
		menu.add(item);
		return item;
	}

	private class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String name =
				((JMenuItem)e.getSource()).getText();
			System.out.println(name);
			if (e.getSource() == exitItem)
				System.exit(0);
			else if (e.getSource() == pauseItem)
			{
				if (pauseItem.getText().equals("Pause Game"))
				{
				    pauseItem.setText("Resume Game");
				    pauseItem.setMnemonic('R');
					quitItem.setEnabled(false);
				}
				else
				{
					pauseItem.setText("Pause Game");
					pauseItem.setMnemonic('P');
					quitItem.setEnabled(true);
				}
			}
			else if (e.getSource() == musicItem)
			{
				if (musicItem.getState() == true)
				    System.out.println(
						"Your mamma can't dance.");
				else
					System.out.println(
						"Your daddy can't rock and roll.");
			}
		}
	}
}