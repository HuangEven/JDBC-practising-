package lab13.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StuFrame extends JFrame{
	private JDesktopPane desktop;
	private JMenuBar menuBar;
	private JMenu alter;
	private JMenuItem add;
	private JMenuItem delete;
	private JMenuItem update;
	private JMenu check;
	private JMenuItem query;
	
	public StuFrame(){
		super("StuInfo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 500);
		//this.setVisible(true);
		
		menuBar=new JMenuBar();
		desktop=new JDesktopPane();
		
		alter=new JMenu("更改");
		add=new JMenuItem("新增");
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Add addFrame=new Add();
				addFrame.setVisible(true);
				addFrame.pack();
				desktop.add(addFrame);
			}
		});
		delete=new JMenuItem("删除");
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Delete deleteFrame=new Delete();
				deleteFrame.setVisible(true);
				deleteFrame.pack();
				desktop.add(deleteFrame);
			}
			
		});
		update=new JMenuItem("更新");
		update.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Alter alterFrame=new Alter();
				alterFrame.setVisible(true);
				alterFrame.pack();
				desktop.add(alterFrame);
			}
			
		});
		alter.add(add);
		alter.add(delete);
		alter.add(update);
		
		check=new JMenu("查看");
		query=new JMenuItem("查询");
		query.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Query queryFrame=new Query();
				queryFrame.setVisible(true);
				queryFrame.pack();				
				desktop.add(queryFrame);
			}			
		});
		check.add(query);
		
		menuBar.add(alter);
		menuBar.add(check);
		
		this.setJMenuBar(menuBar);
		this.add(desktop);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		new StuFrame();
	}
}
