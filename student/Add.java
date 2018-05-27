package lab13.student;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Add extends JInternalFrame{
	private JLabel[] labels=new JLabel[4];
	private JTextField[] textFields=new JTextField[4];
	private JButton btn;
	private String[] attributes={
			"学号：","姓名：","电话：","电子邮箱："};
	
	public Add(){
		super("新增",true,true,true,true);
		this.setLayout(new GridLayout(5,0,5,5));
		this.setPreferredSize(new Dimension(400,300));
		
		for(int i=0;i<labels.length;i++){
			labels[i]=new JLabel(attributes[i]);
			textFields[i]=new JTextField();
			this.add(labels[i]);
			this.add(textFields[i]);
		}
		
		btn=new JButton("确定");
		btn.addActionListener(new BtnListener());
		this.add(btn);
	}
	
	class BtnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String sno=textFields[0].getText();
			String sname=textFields[1].getText();
			String stel=textFields[2].getText();
			String smail=textFields[3].getText();
			
			Database db=new Database();
			db.setSql("insert into student values (\'"
					+sno+"\',\'"+sname+"\',\'"+stel+"\',\'"+smail+"\')");
			db.Update();
			db.close();
		}		
	}	
}
