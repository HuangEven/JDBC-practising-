package lab13.student;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;


public class Delete extends JInternalFrame{
	private JLabel[] labels=new JLabel[4];
	private JTextField[] textFields=new JTextField[4];
	private JButton btn_pre;
	private JButton btn_next;
	private JButton btn;
	private String[] attributes={
			"学号：","姓名：","电话：","电子邮箱："};
	private Map<Integer,List<String>> infoTabel;
	private int location;
	
	public Delete(){
		super("删除",true,true,true,true);
		this.setLayout(new GridLayout(6,0,5,5));
		this.setPreferredSize(new Dimension(400,300));
		
		for(int i=0;i<labels.length;i++){
			labels[i]=new JLabel(attributes[i]);
			textFields[i]=new JTextField();
			this.add(labels[i]);
			this.add(textFields[i]);
		}
		
		infoTabel=getDataSet();
		location=0;
		List<String> student=infoTabel.get(0);
		for(int i=0;i<student.size();i++){
			textFields[i].setText(student.get(i));
		}
		
		DeleteListener dl=new DeleteListener();
		
		btn_pre=new JButton("上一条");
		btn_pre.addActionListener(dl);
		btn_next=new JButton("下一条");
		btn_next.addActionListener(dl);
		btn=new JButton("删除此条记录");
		btn.addActionListener(dl);
		
		this.add(btn_pre);
		this.add(btn_next);
		this.add(btn);

	}
	
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
	
	class DeleteListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
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
			
			if(e.getActionCommand().equals("删除此条记录")){			
				String sno=infoTabel.get(location).get(0); //获取相关学生信息以确定哪个学生
				String sql="DELETE FROM Student WHERE sno=\'"+sno+"\'";
				
				Database db=new Database();
				db.Update(sql);
				
				infoTabel.remove(location);			//将删除信息移除	
			}
		}
		
	}
}
