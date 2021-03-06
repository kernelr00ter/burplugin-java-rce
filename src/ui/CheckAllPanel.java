package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import enumeration.Version;





public class CheckAllPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField cmdJTextField;
	private JButton exeJButton;
	private JButton fileJButton;
	private JButton pythonJButton;
	private JTextPane textPane;
	private String host;
	private Version version;
	private String message="";  
	private  File targetFile; 

	public CheckAllPanel() {
		setSize(600, 460);

		JLabel cmdJLabel = new JLabel("批量文件路径");

		cmdJTextField = new JTextField();
		cmdJTextField.setColumns(7);
		fileJButton = new JButton("批量文件选择");
		pythonJButton = new JButton("python");
		Font f=new Font("宋体", Font.PLAIN, 10);
		pythonJButton.setFont(f);
		exeJButton = new JButton("执行");
		exeJButton.addActionListener(this);
		cmdJTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					new Thread(new Runnable() {
						@Override
						public void run() {
							request();
						}
					}).start();

				}
			}
		});
		
		fileJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				jButton1ActionActionPerformed(event);  
			}
		});
		
		
		
		pythonJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					pythonActionPerformed(event);
				} catch (IOException | BadLocationException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  
			}
			
		});
		textPane = new JTextPane();
		textPane.setText("******本软件仅限用于学习交流禁止用于任何非法行为******\n本版本支持weblogic反序列化漏洞命令执行及文件上传，elasticsearch java语言远程命令执行及文件上传\nelasticsearchgroov语言远程命令执行及文件上传\nstruts2-005、struts2-009、struts2-013、struts2-016、struts2-019、struts2-020、struts2-devmode、\nstruts2-032、struts2-033、struts2-037、struts2-045、struts2-048、struts2-052 除struts2-053全部RCE漏洞验证并支持批量验证。\nStruts2漏洞验证需要python环境并需要相关类库支持.点击python按钮初始化初始化python类库\n\n如果初始化失败请按照如下步骤安装类库，\n1、执行 $[python]/Scrips/easy_install pip\n2、requests模块 安装方法 pip install requests\n3、termcolor模块安装方法： pip install termcolor");
		textPane.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textPane);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addComponent(cmdJLabel)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cmdJTextField, GroupLayout.PREFERRED_SIZE, 360, Short.MAX_VALUE).addGap(20)
								.addComponent(pythonJButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(20)
								.addComponent(fileJButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE).addGap(20)
								.addComponent(exeJButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(cmdJLabel)
								.addComponent(cmdJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(pythonJButton)
										.addComponent(fileJButton)
								.addComponent(exeJButton))
						.addGap(10).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addContainerGap()));
		setLayout(groupLayout);
		setVisible(true);
	}
	//初始化python环境
	private void pythonActionPerformed(ActionEvent event) throws IOException, BadLocationException, InterruptedException{
		textPane.setText("开始安装  pip..........\n");
		String prefix = process("python -c \"import sys; print sys.prefix\" ");
		String eastInstall = prefix +"/Scripts/easy_install";
		
//		textPane.setText("开始安装  pip。。。。。。。。。。。");
		processWritePane(eastInstall+" pip");
		
		Thread.sleep(1000);
		System.out.println("开始安装  requests。。。。。。。。。。");
//		textPane.setText("开始安装  requests。。。。。。。。。。。");
		processWritePane("pip install requests ");
		
		Thread.sleep(1000);
//		textPane.setText("开始安装  termcolor。。。。。。。。。。。");
		System.out.println("开始安装  termcolor。。。。。。。。。。。");
		processWritePane("pip install  termcolor ");
		
	}
	
	private String process(String cmd) throws IOException{
		
		Process process = Runtime.getRuntime().exec(cmd);  
		InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        SimpleAttributeSet red = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,12);
        StyleConstants.setForeground(red, Color.RED);
        Document docs = textPane.getDocument();//获得文本对象
        String line = null;
        String str = null;
        
        try {
                while ((line = bufferedReader.readLine()) != null) {
                	System.out.println(line);
                	str =  line;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
		return str;
	}
	
	private String processWritePane(String cmd) throws IOException, BadLocationException{
		
		Process process = Runtime.getRuntime().exec(cmd);  
		InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        SimpleAttributeSet red = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,12);
        StyleConstants.setForeground(red, Color.RED);
        Document docs = textPane.getDocument();//获得文本对象
        String line = null;
        
        try {
                while ((line = bufferedReader.readLine()) != null) {
                	docs.insertString(docs.getLength(), line+"\n", attrset);//对文本进行追加
                	System.out.println(line);
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
		return line;
	}
	
	//读取文件  
    private void jButton1ActionActionPerformed(ActionEvent event) {  
        JFileChooser dlg = new JFileChooser();   
        /** 
         * 设置对话框类型,其打开方法也要对应正确，打开方法控制显示界面 
         */  
        dlg.setDialogType(JFileChooser.OPEN_DIALOG);  
        //设定好类型后和dlg.showOpenDialog(null)方法配套使用才会显示保存对话框  
//      dlg.setDialogType(JFileChooser.SAVE_DIALOG);  
        //设定好类型后和dlg.showDialog(null, "haha")方法配套使用，并且第一个按钮显示为第二个参数"haha".  
//      dlg.setDialogType(JFileChooser.CUSTOM_DIALOG);  
          
          
        //第一个参数父组件，第2 个参数是第一个按钮的名字（不再是打开或保存了）  
//      dlg.showDialog(null, "haha");  
       int value =  dlg.showOpenDialog(null);  
//      dlg.showSaveDialog(null);  
       if(value==JFileChooser.APPROVE_OPTION){
    	   File file=dlg.getSelectedFile(); 
           if(file==null){  
           	JOptionPane.showMessageDialog(this, "文件不存在");
           }else{  
               //得到这个文件后，想干嘛干嘛  
               System.out.println("getAbsolutePath--"+file.getAbsolutePath());  
               System.out.println("getName--"+file.getName());  
               System.out.println("isFile--"+file.isFile());  
               System.out.println("isDirectory--"+file.isDirectory());  
               System.out.println("getPath--"+file.getPath());  
               System.out.println("getParent--"+file.getParent());  
               cmdJTextField.setText(file.getPath());
               this.targetFile=file;  
               this.message=file.getPath();  
                 
           }  
       }else{
    	   return;
       }   
       
    } 

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == exeJButton) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					request();
				}
			}).start();

		}
	}

	private void request() {
		try {
			textPane.setText("");
			String command = cmdJTextField.getText().trim();
			if (host != null ) {

				textPane.setText("请稍等...\n");

//				ExploitService service = (ExploitService) Class
//						.forName("com.axtx.service.impl." + version.name() + "ExploitServiceImpl").newInstance();

//				textPane.setText(service.doExecuteCMD(host, command));
				if(version.name().equals("CheckAll")){
//					URL resource = getClass().getResource("/resource/s-scan.py");
					 InputStream sourceURL = getClass().getResourceAsStream("/resource/s-scan.py");
//					File file = new File(sourceURL);
//					readFileByLines(file);
					File acc = new File("e:/acc.py");
					if(!acc.exists()){
						copyFile(sourceURL,"e:/acc.py");
						Thread.sleep(500);
					}
					
//					String url = getClass().getClassLoader().getResource("/").getPath();
//					String url = resource.getPath();
					Process process = Runtime.getRuntime().exec("python "+ "e:/acc.py" +" "+host);  
					InputStream inputStream = process.getInputStream();
			        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			        SimpleAttributeSet attrset = new SimpleAttributeSet();
			        SimpleAttributeSet red = new SimpleAttributeSet();
			        StyleConstants.setFontSize(attrset,12);
			        StyleConstants.setForeground(red, Color.RED);
			        
			        
			        Document docs = textPane.getDocument();//获得文本对象
			        String line;
			        
			        try {
			                while ((line = bufferedReader.readLine()) != null) {
			                	System.out.println(line);
			                	if(line.startsWith("[32m")){
			                		line= line.replace("[32m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8");  
			                		docs.insertString(docs.getLength(), newStr+"\n", attrset);//对文本进行追加
			                	}else if(line.startsWith("[31m")){
			                		line= line.replace("[31m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8");  
			                		docs.insertString(docs.getLength(), newStr+"\n", red);//对文本进行追加
			                	}else{
			                		String newStr = new String(line.getBytes(), "UTF-8");  
			                		docs.insertString(docs.getLength(), newStr+"\n", red);//对文本进行追加
			                	}
			                	
			                	
			                }
			        } catch (IOException e) {
			                e.printStackTrace();
			        }
					process.waitFor();  
				}else if(version.name().equals("Batch")){
//					URL resource = getClass().getResource("/resource/s-scan.py");
					InputStream sourceURL = getClass().getResourceAsStream("/resource/s-scan.py");
					
//					File file = new File(resource.toURI());
//					readFileByLines(file);
					File acc = new File("e:/acc.py");
					if(!acc.exists()){
						copyFile(sourceURL,"e:/acc.py");
						Thread.sleep(500);
					}
					
					
					Process process = Runtime.getRuntime().exec("python "+ "e:/acc.py" +" -f " +" "+cmdJTextField.getText());
					InputStream inputStream = process.getInputStream();
			        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			        SimpleAttributeSet attrset = new SimpleAttributeSet();
			        SimpleAttributeSet red = new SimpleAttributeSet();
			        StyleConstants.setFontSize(attrset,12);
			        StyleConstants.setForeground(red, Color.RED);
			        Document docs = textPane.getDocument();//获得文本对象
			        String line;
			        
			        try {
			                while ((line = bufferedReader.readLine()) != null) {
			                	System.out.println(line);
			                	if(line.startsWith("[32m")){
			                		
			                		line= line.replace("[32m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8"); 
			                		docs.insertString(docs.getLength(), newStr+"\n", attrset);//对文本进行追加
			                	}else if(line.startsWith("[31m")){
			                		line= line.replace("[31m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8"); 
			                		docs.insertString(docs.getLength(), newStr+"\n", red);//对文本进行追加
			                	}else{
			                		line= line.replace("[32m", "").replace("[0m", "").replace("[36m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8"); 
			                		docs.insertString(docs.getLength(), newStr+"\n", attrset);//对文本进行追加
			                	}
			                	
			                	
			                }
			        } catch (IOException e) {
			                e.printStackTrace();
			        }
					process.waitFor(); 
				}

			} else {
				JOptionPane.showMessageDialog(this, "请输入执行命令");
			}
		} catch (Exception exp) {
			textPane.setText(exp.getMessage());
		}
	}

	public void setReqestUrl(String host) {
		this.host = host;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	 public static void readFileByLines(File file) {
//	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	                System.out.println("line " + line + ": " + tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
	 
	 
	 public static void copyFile( InputStream inStream, String newPath) { 
	       try { 
	           int bytesum = 0; 
	           int byteread = 0; 
//	               InputStream inStream = new FileInputStream(oldfile);      //读入原文件 
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               int length; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   bytesum += byteread;            //字节数 文件大小 
	                   System.out.println(bytesum); 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	       }  catch (Exception e) { 
	           System.out.println("复制单个文件操作出错"); 
	           e.printStackTrace(); 
	       } 
	   } 
}
