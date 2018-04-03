import javax.swing.*;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class SpinOffs extends JFrame
{
	public static void main(String [] args)
	{
		new SpinOffs();
	}

	private JTree tree1;
	private JLabel showName;
	private DefaultTreeModel model;

	public SpinOffs()
	{
	    this.setSize(225,325);
	    this.setTitle("Famous Spinoffs");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel panel1 = new JPanel();

		DefaultMutableTreeNode root, andy, archie, happy,
							   george, maude;

		root = new DefaultMutableTreeNode("Famous Spinoffs");

		andy = makeShow("The Andy Griffith Show", root);
		makeShow("Gomer Pyle, U.S.M.C.", andy);
		makeShow("Mayberry R.F.D.", andy);

		archie = makeShow("All In the Family", root);
		george = makeShow("The Jeffersons", archie);
		makeShow("Checking In", george);
		maude = makeShow("Maude", archie);
		makeShow("Good Times", maude);
		makeShow("Gloria", archie);
		makeShow("Archie Bunker's Place", archie);

		happy = makeShow("Happy Days", root);
		makeShow("Mork and Mindy", happy);
		makeShow("Laverne and Shirley", happy);
		makeShow("Joanie Loves Chachi", happy);

		tree1 = new JTree(root);

		tree1.getSelectionModel().setSelectionMode(
			TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree1.setVisibleRowCount(12);
		tree1.addTreeSelectionListener(new TreeListener());

		JScrollPane scroll = new JScrollPane(tree1);
	    panel1.add(scroll);

	    showName = new JLabel();
	    panel1.add(showName);

	    this.add(panel1);
	    this.setVisible(true);
	}

	private DefaultMutableTreeNode makeShow(String title,
	    DefaultMutableTreeNode parent)
	{
		DefaultMutableTreeNode show;
		show = new DefaultMutableTreeNode(title);
		parent.add(show);
		return show;
	}

	private class TreeListener implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent e)
		{
			Object o = tree1.getLastSelectedPathComponent();
			DefaultMutableTreeNode show;
			show = (DefaultMutableTreeNode) o;
			String title = (String)show.getUserObject();
			showName.setText(title);
		}
	}
}