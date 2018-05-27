package lab13.student;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

import lab13.student.Add.BtnListener;

public class Query extends JInternalFrame{
	private JLabel[] labels=new JLabel[4];
	private JTextField[] textFields=new JTextField[4];
	private JButton btn;
	private String[] attributes={
			"查询学生姓名：","学号：","电话：","电子邮箱："};
	
	public Query(){
		super("查找",true,true,true,true);
		this.setLayout(new GridLayout(5,0,5,5));
		this.setPreferredSize(new Dimension(400,300));
		
		for(int i=0;i<labels.length;i++){
			labels[i]=new JLabel(attributes[i]);
			textFields[i]=new JTextField();
			this.add(labels[i]);
			this.add(textFields[i]);
		}
		
		btn=new JButton("查询");
		btn.addActionListener(new BtnListener());
		this.add(btn);
	}
	
	class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String sname=textFields[0].getText();
			String sql="SELECT sno,stel,smail FROM Student "
					+ "WHERE sname=\'"+sname+"\'";
			
			Database db=new Database();
			try{
				ResultSet rs=db.getResult(sql);
				
				if(rs.next()){
					do{
						textFields[1].setText(rs.getString("sno"));
						textFields[2].setText(rs.getString("stel"));
						textFields[3].setText(rs.getString("smail"));
					}while(rs.next());
				}
				else
					JOptionPane.showMessageDialog(null, "无此学生记录",
							"Warning",JOptionPane.WARNING_MESSAGE);
				
				db.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}		
	}	
}
