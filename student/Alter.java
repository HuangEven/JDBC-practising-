package lab13.student;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import lab13.student.Add.BtnListener;
import lab13.student.Delete.DeleteListener;

public class Alter extends JInternalFrame{
	private JLabel[] labels=new JLabel[4];
	private JTextField[] textFields=new JTextField[4];
	private JButton btn_pre;
	private JButton btn_next;
	private JButton btn;
	private String[] attributes={
			"学号：","姓名：","电话：","电子邮箱："};
	private Map<Integer,List<String>> infoTabel;
	private int location;
	
	public Alter(){
		super("更新",true,true,true,true);
		this.setLayout(new GridLayout(6,0,5,5));
		this.setPreferredSize(new Dimension(400,300));
		
		TextListener tl=new TextListener();
		for(int i=0;i<labels.length;i++){
			labels[i]=new JLabel(attributes[i]);
			textFields[i]=new JTextField();
			textFields[i].addMouseListener(tl);
			this.add(labels[i]);
			this.add(textFields[i]);
		}
		
		infoTabel=getDataSet();
		location=0;
		List<String> student=infoTabel.get(0);
		for(int i=0;i<student.size();i++){
			textFields[i].setText(student.get(i));
			System.out.println(student.get(i));
		}
		
		AlterListener al=new AlterListener();
		
		btn_pre=new JButton("上一条");
		btn_pre.addActionListener(al);
		btn_next=new JButton("下一条");
		btn_next.addActionListener(al);
		btn=new JButton("确认修改");
		btn.addActionListener(al);
		
		this.add(btn_pre);
		this.add(btn_next);
		this.add(btn);
	}
	
	//连接数据库获取信息表
	public Map<Integer,List<String>> getDataSet(){
		Database db=new Database();
		List<String> data=new ArrayList<String>();
		try{
			ResultSet rs=
					db.getResult("SELECT * FROM Student");
			while(rs.next()){
				data.add(rs.getString("sno"));
				data.add(rs.getString("sname"));
				data.add(rs.getString("stel"));
				data.add(rs.getString("smail"));
			}
			db.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		Map<Integer,List<String>> infoTabel=new HashMap<Integer,List<String>>();
		for(int i = 0;i<data.size();i+=4){
	        List<String> student = data.subList(i,i+4);
	        infoTabel.put(i/4, student);//得到分组
        }

		return infoTabel;
	}
	
	//文本框监听
	class TextListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e) {
			if(e.getSource()==textFields[0]){
				textFields[0].setText("不能修改主码");
			}
			if(e.getSource()==textFields[1]){
				textFields[1].setText("");
			} 
			if(e.getSource()==textFields[2]){
				textFields[2].setText("");
			} 
			if(e.getSource()==textFields[3]){
				textFields[3].setText("");
			} 
		}
	}
	
	//按钮监听
	class AlterListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("上一条")){
				//反向循环
				if(location==0)
					location=infoTabel.size()-1;
				else 
					location--;
				//重置学生信息
				for(int i=0;i<infoTabel.get(location).size();i++){
					textFields[i].setText(
							infoTabel.get(location).get(i));
				}
			}
			
			if(e.getActionCommand().equals("下一条")){
				//正向循环
				location=(location+1)%infoTabel.size();
				
				for(int i=0;i<infoTabel.get(location).size();i++){
					textFields[i].setText(
							infoTabel.get(location).get(i));
				}
			}
			
			if(e.getActionCommand().equals("确认修改")){			
				String sno=infoTabel.get(location).get(0);
				String sname=textFields[1].getText();
				String stel=textFields[2].getText();
				String smail=textFields[3].getText();
				String sql="UPDATE Student SET sname=\'"+sname
						+"\',stel=\'"+stel+"\',smail=\'"+smail
						+"\' WHERE sno=\'"+sno+"\'";
						
				Database db=new Database();
				db.Update(sql);
				
				List<String> newList=new ArrayList<String>();
				newList.add(sno);
				newList.add(sname);
				newList.add(stel);
				newList.add(smail);
				infoTabel.put(location, newList);		//同时更新信息表				
			}
		}	
	}
}
