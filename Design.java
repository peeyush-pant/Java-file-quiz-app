import java.awt.*;
import java.io.*;
import java.util.regex.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;

class Design extends WindowAdapter implements ActionListener 
{
	Frame m_Frame=new Frame("Test");
	Frame result_Frame;
	Frame welocme_Frame;
	Panel up_Pan=new Panel();
	Panel dw_Pan=new Panel();
	Panel sd_Pan=new Panel();
	Panel md_Pan=new Panel();
	Panel clock_Pan;
	Timer timer = new Timer();
	final int SIZE;
	final int correctScore,wrongScore;
	Scrollbar sb;
	String savedString,subString;
	int interval;
	Checkbox chbx[][];
	Panel md_SubPan[];
	CardLayout cd;
	Button submit,ok;
	boolean resultOpen,welcomeClose;
	boolean set[];
	Button sd_btn[];
	Button start;
	Label clock;
	public Design()
	{ 
		cd=new CardLayout();
		m_Frame.setLocation(10,10);
		m_Frame.setSize(1000,700);
		m_Frame.setLayout(new BorderLayout());
		m_Frame.addWindowListener(this);
		SIZE=calcSize(); //gets no. of questions
		sd_btn=new Button[SIZE];
		set=new boolean[SIZE]; 
		md_SubPan=new Panel[SIZE];
		chbx=new Checkbox[SIZE][4];
		
		md_Pan.setBackground(Color.white);
		md_Pan.setLayout(cd);
		md_PanAdd();
		
		up_Pan.setBackground(Color.darkGray);
		up_Pan.setLayout(new GridLayout(1,4));
		up_PanAdd();

		
		dw_Pan.setBackground(Color.gray);
		dw_Pan.setLayout(new GridBagLayout());
		dw_PanAdd();

		sd_Pan.setBackground(Color.lightGray);
		sd_Pan.setLayout(new GridBagLayout());
		sd_PanAdd();

		m_Frame.add(up_Pan,"North");
		m_Frame.add(md_Pan,"Center");
		m_Frame.add(dw_Pan,"South");
		m_Frame.add(sd_Pan,"West");
		correctScore=setCorrectScore();
		wrongScore=setWrongScore();
		show();
		
		
		
		
	}

	public static void main(String args[])
	{
		Design d=new Design();
	}

	private void dw_PanAdd()
	{
		GridBagConstraints gbc =new GridBagConstraints();
		Insets ins=new Insets(10,5,10,5);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.EAST;
		gbc.weightx=gbc.weighty=1.0;
		//gbc.anchor=GridBagConstraints.CENTER;
		gbc.ipadx=50;
		gbc.ipady=20;
		Button next=new Button("Next");
		next.addActionListener(this);
		dw_Pan.add(next,gbc);

		Button prev=new Button("Previous");
		gbc.gridx=1;
		prev.addActionListener(this);
		dw_Pan.add(prev,gbc);
		gbc.gridx=2;
		
		submit=new Button("Submit");
		submit.setBackground(Color.red);
		submit.setForeground(Color.white);
		submit.addActionListener(this);
		dw_Pan.add(submit,gbc);
		
		
	}
	//		gets total number of Questions from file info.txt 
	private int calcSize()
	{
		File inpFile =new File("info.txt");
		if(inpFile.exists())
		{
			String str="";
			char ch='\0';
			try
			{
				FileInputStream fin=new FileInputStream(inpFile);
				while((int)(ch=(char)fin.read())!=13)
				{
					str=str+ch;
				}
				fin.close();
			}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		int i=0;
		for(;i<str.length();i++)
		{
			if(str.charAt(i)=='=')
				break;
		}
		
		str=str.substring(i+1,str.length());
	
		

		return Integer.parseInt(str);
		}
		else 
			return 0;
	}
	
	private void sd_PanAdd()
	{
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		Insets ins=new Insets(5,5,5,5);
		gbc.insets=ins;		
		
		
		int key=0;
		if(SIZE<=10)
		{
			key=1;
			gbc.ipadx=gbc.ipady=20;
		}
		else if(SIZE<=20)
		{
			key=2;
			gbc.ipadx=gbc.ipady=20;
		}
		else if(SIZE<=30)
		{
			key=3;
			gbc.ipadx=gbc.ipady=15;
		}
		else 
		{
			key=4;
			gbc.ipadx=gbc.ipady=10;
		}
		int i=0,j=1,k=0;
		for(;i<SIZE;i++)
		{	
			if(j==i/key)
				k++;
			else
				k=0;
			gbc.gridy=j=i/key;
			gbc.gridx=k;
			sd_btn[i]=new Button(""+(i+1));
			sd_btn[i].addActionListener(this);
			sd_btn[i].setBackground(Color.yellow);
			sd_Pan.add(sd_btn[i],gbc);
			
		}
		 if(SIZE>42)
			{
				gbc.gridy=0;
				gbc.gridx=4;
				sb=new Scrollbar(Scrollbar.VERTICAL);
				sb.setBackground(Color.darkGray);
				sb.setForeground(Color.lightGray);
				gbc.gridheight=j;
				sd_Pan.add(sb,gbc);
				
			}	
	}	
	
	private void md_PanAdd()
	{
		
		TextArea txar[]=new TextArea[SIZE];
		CheckboxGroup chbxgp[]=new CheckboxGroup[SIZE];
		File inpFile =new File("info.txt");
		if(inpFile.exists())
		{	int ch=0;
			String str="";
			try
			{
				FileInputStream fin=new FileInputStream(inpFile);
				while((ch=fin.read())!=-1)
				{
					str=str+(char)ch;
				}
				fin.close();
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
			savedString=str;
			
			for(int i=0;i<SIZE;i++)
			{
				String question="",op1="",op2="",op3="",op4="";
				
				int start=0;
				int end=0;
				for(int k=0;k<str.length();k++)
				{
					if(str.charAt(k)=='*')
					{
						start=k+1;
						for(int l=k+1;l<str.length();l++)
						{
							if(str.charAt(l)=='*')
							{	
								end=l;
								
								break;
							}
							
						}
						break;
					}
				}
				question=str.substring(start,end);
				str=str.substring(end+1,str.length());
				int loop=0;
				for(int j=0;j<4;j++)
				{
					for(int k=0;k<str.length();k++)
					{
						if(str.charAt(k)=='/')
						{
							start=k+1;
							for(int l=k+1;l<str.length();l++)
							{
								if(str.charAt(l)=='/')
								{	
									end=l;
									if(loop==0)
									{
										op1=str.substring(start,end);
										
										loop++;
									}
									else if(loop==1)
									{
										op2=str.substring(start,end);
									
										loop++;
									}
									else if(loop==2)
									{
										op3=str.substring(start,end);
										
										loop++;
									}
									else if(loop==3)
									{
										op4=str.substring(start,end);
										
										loop++;
									}
									break;
								}
						
							
							}
							break;
						}
					}
					str=str.substring(end+1,str.length());
				}
				
				
				txar[i]=new TextArea("",4,70,TextArea.SCROLLBARS_NONE);
				txar[i].setFont(new Font("sans-serif", Font.BOLD, 20));
				md_SubPan[i]=new Panel();
				chbxgp[i]=new CheckboxGroup();
				
				md_SubPan[i].setLayout(new GridBagLayout());
				GridBagConstraints gbc=new GridBagConstraints();
				txar[i].setText("Q."+(i+1)+" : "+question);
				gbc.gridheight=2;
				gbc.gridwidth=2;
				Insets ins=new Insets(5,5,5,5);
				gbc.insets=ins;
				
				md_SubPan[i].add(txar[i],gbc);
				txar[i].setEditable(false);
				gbc.gridheight=1;
				gbc.gridwidth=1;
				for(int j=0;j<4;j++)
				{
					gbc.anchor=GridBagConstraints.WEST;
					if(j==0)
					{	
						gbc.gridx=0;
						gbc.gridy=2;
						chbx[i][j]=new Checkbox(j+1+") "+op1,chbxgp[i],false);
					}
					else if(j==1)
					{
						gbc.gridx=0;
						gbc.gridy=4;
						chbx[i][j]=new Checkbox(j+1+") "+op2,chbxgp[i],false);
					}
					else if(j==2)
					{
						gbc.gridx=0;
						gbc.gridy=6;
						chbx[i][j]=new Checkbox(j+1+") "+op3,chbxgp[i],false);
						
					}
					else
					{
						gbc.gridx=0;
						gbc.gridy=8;
						chbx[i][j]=new Checkbox(j+1+") "+op4,chbxgp[i],false);
					}
					chbx[i][j].setFont(new Font("sans-serif", Font.PLAIN, 20));
					md_SubPan[i].add(chbx[i][j],gbc);
				}
				
				md_Pan.add(md_SubPan[i],""+i);
			}
			
		}
	}
	
	private void up_PanAdd()
	{	
		
		clock_Pan=new Panel();
		clock_Pan.setSize(300,150);
		clock_Pan.setLayout(new GridLayout(1,1));
		
		for(int i=0;i<3;i++)
		{
		if(i==1)
		{
		Label  label=new Label();
		label.setForeground(Color.orange);
		label.setFont(new Font("sans-serif", Font.BOLD, 60));
		label.setText("QUIZ");
		up_Pan.add(label);
		}
		
		else
		up_Pan.add(new Label(""));
		}
		clock=new Label();
		clock_Pan.setForeground(Color.green);
		clock.setFont(new Font("sans-serif", Font.PLAIN, 40));
		Pattern wholeStr=Pattern.compile("Time:");
		Matcher findStr=wholeStr.matcher(savedString);
		findStr.find();
		int i=findStr.end();
		int j=i;
		while((int)(savedString.charAt(j))!=13)
		j++;
		interval=Integer.parseInt(savedString.substring(i,j))*60;	
	}
	void timeStart()
	{
	
		timer.scheduleAtFixedRate(new TimerTask() {
		int time=interval;
        public void run() {
		
			time=setInterval();
            clock.setText(time/3600+":"+time/60+":"+time%60);
		
			
        }
		}, 1000, 1000);
		clock_Pan.add(clock);
		up_Pan.add(clock_Pan);
	}
	private int setInterval() 
	{
		if (interval == 1)
       {
	   timer.cancel();
	   submit();
	   }
		return --interval;
	}
	public void actionPerformed(ActionEvent e)
	{
	String comm=e.getActionCommand();
	check();
	switch(comm)
		{
		case "Next": cd.next(md_Pan);
					break;
		case "Previous": cd.previous(md_Pan);
					break;
		case "Submit":submit();
					break;
		case "Start Test":closewelcome();
							welcomeClose=false;
							m_Frame.setVisible(true);
							timeStart();
							break;
		default:	
			for(int i=1;i<=SIZE;i++)
			{
			if(comm.equals(""+i))
				{
					cd.show(md_Pan,""+(i-1));
					break;
				}
			}
		}
	}
	public void windowClosing(WindowEvent e)
	{
		if(resultOpen)
		{	
			close();
			Window w=e.getWindow();
			w.setVisible(false);
			w.dispose();
			
			System.exit(1);
		}
		else if(welcomeClose)
		{
		System.exit(1);
		}
		else
		{
			submit();
			close();
		}
		
	}
	private int setCorrectScore()
	{
		Pattern wholeStr=Pattern.compile("Correct Answer:");
		Matcher findStr=wholeStr.matcher(savedString);
		findStr.find();
		int i=findStr.end();
		int j=i;
		
		while((int)(savedString.charAt(j))!=13)
		j++;
		return Integer.parseInt(savedString.substring(i,j));
	}
	private int setWrongScore()
	{
		Pattern wholeStr=Pattern.compile("Wrong Answer:");
		Matcher findStr=wholeStr.matcher(savedString);
		findStr.find();
		int i=findStr.end();
		int j=i;
		
		while((int)(savedString.charAt(j))!=13)
		j++;
		return Integer.parseInt(savedString.substring(i,j));
	}
	void submit()
	{	
		close();
		resultOpen=true;
		result_Frame=new Frame("Result");
		result_Frame.setLocation(10,10);
		result_Frame.setSize(1000,700);
		result_Frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		result_Frame.addWindowListener(this);
		int totalScore=0;
		String answered="";
		String correctAnswer="";
		for(int i=0;i<SIZE;i++)
		{
			boolean isAns=false;
			
			for(int j=0;j<4;j++)
			{
				if(chbx[i][j].getState())
				{
				answered=answered+(j+1);
				isAns=true;
				break;
				}
			}
			if(!isAns)
			answered=answered+"0";
		}
		
		File inpFile =new File("answer.txt");
		if(inpFile.exists())
		{	int ch=0;
			
			try
			{
				FileInputStream fin=new FileInputStream(inpFile);
				while((ch=fin.read())!=-1)
				{
					correctAnswer+=(char)ch;
				}
				fin.close();
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		
		}
		for(int i=0;i<SIZE;i++)
		{
			if(answered.charAt(i)=='0')
			{
				totalScore+=0;
			}
			else if(answered.charAt(i)==correctAnswer.charAt(i))
			{
				totalScore+=correctScore;
			}
			else
			{
				totalScore+=wrongScore;
			}
		}
		Label res=new Label("");
		res.setForeground(Color.green);
		res.setFont(new Font("sans-serif", Font.BOLD, 40));
		res.setText("You Scored: "+totalScore+" out of "+SIZE*correctScore);
		result_Frame.add(res,gbc);
		result_Frame.setVisible(true);
	}	
	void close()
	{
	Window w=(Window)m_Frame;
	w.setVisible(false);
	w.dispose();
	}
	void closewelcome()
	{
	Window w=(Window)welocme_Frame;
	w.setVisible(false);
	w.dispose();
	}
	void check()
	{
		for(int i=0;i<SIZE;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(chbx[i][j].getState())
					sd_btn[i].setBackground(Color.green);
			}
		}
				
	}
	
	void show()
	{
			welcomeClose=true;
			welocme_Frame=new Frame("welcome");
			welocme_Frame.setLocation(10,10);
			welocme_Frame.setSize(1000,700);
			welocme_Frame.setLayout(new GridBagLayout());
			GridBagConstraints gbc=new GridBagConstraints();
			welocme_Frame.addWindowListener(this);
			gbc.gridwidth=3;
			gbc.gridheight=3;
			Insets ins =new Insets(10,10,10,10);
			gbc.insets=ins;
			TextArea tx=new TextArea("",4,70,TextArea.SCROLLBARS_NONE);
			tx.setFont(new Font("sans-serif", Font.BOLD, 20));
			tx.setText("1. Number of questions= "+SIZE+"\n2. Time provided= "+interval/60+" minutes\n"+"3. Marks for Right Answer= "+correctScore+"\n4. Marks for Wrong Answer= "+wrongScore);
			tx.setEditable(false);
			welocme_Frame.add(tx,gbc);
			gbc.gridy=4;
			gbc.gridwidth=1;
			gbc.gridheight=1;
			Label yellowlb=new Label("");
			yellowlb.setBackground(Color.yellow);
			gbc.anchor=GridBagConstraints.EAST;
			welocme_Frame.add(yellowlb,gbc);
			gbc.gridy=5;
			Label greenlb=new Label("");
			greenlb.setBackground(Color.green);
			welocme_Frame.add(greenlb,gbc);
			gbc.gridy=4;
			gbc.gridx=1;
			gbc.anchor=GridBagConstraints.WEST;
			Label yellowlable=new Label("Unattempted Question");
			welocme_Frame.add(yellowlable,gbc);		
			gbc.gridy=5;
			gbc.gridx=1;
			Label greenlable=new Label("Attempted Question");
			welocme_Frame.add(greenlable,gbc);
			start=new Button ("Start Test");
			start.addActionListener(this);
			gbc.gridx=2;
			gbc.gridy=6;
			gbc.ipady=20;
			gbc.ipadx=100;
			welocme_Frame.add(start,gbc);
			welocme_Frame.setVisible(true);
		}
	
}	
