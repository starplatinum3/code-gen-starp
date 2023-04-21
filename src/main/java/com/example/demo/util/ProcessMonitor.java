package com.example.demo.util;

import org.springframework.messaging.MessagingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

//import javax.mail.Address;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

/**
 * monitor process
 * 监听进程
 */
public class ProcessMonitor {


	String processName="";
	// intially assign boolean variable with value as true
//	volatile boolean thread_stop = true;
	volatile boolean threadRunning = true;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public  void monitor()
//			throws IOException, InterruptedException
	{
//		ThreP
//		线程池
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				try{
//				while (true) {
					while (threadRunning) {
						System.out.println("扫描进程 "+processName);
						Process p = Runtime.getRuntime().exec("tasklist /FI \"PID eq " + processName + "\"");
						InputStream fis = p.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(fis));
						if (br.readLine() == null) {
							//进程没了发邮件提醒
//							ProcessMonitor monitorProcess = new ProcessMonitor();
//							monitorProcess.Email();
//							System.exit(0);
//						java  线程池 打开的线程 停止
//							threadRunning=false;
//							runnable.i
						}else{
							int waitSec=30;
							System.out.println("开始关机 等待 "+waitSec+"秒 之后关机");
							threadRunning=false;
//							这个线程开始了 ，关机
							ProcessUtil.shutdown(waitSec);
						}
//						60秒  一分钟进行一次
						Thread.sleep(60 * 1000);
					}
				}catch (Exception e){
					e.printStackTrace();
				}

			}
		};
		ThreadPoolFactory.getExecutorService().execute(runnable);

//		ThreadPoolFactory.getExecutorService().execute(()->{
//			try{
////				while (true) {
//				while (threadRunning) {
//					Process p = Runtime.getRuntime().exec("tasklist /FI \"PID eq " + processName + "\"");
//					InputStream fis = p.getInputStream();
//					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//					if (br.readLine() == null) {
//						//进程没了发邮件提醒
//						ProcessMonitor monitorProcess = new ProcessMonitor();
//						monitorProcess.Email();
//						System.exit(0);
////						java  线程池 打开的线程 停止
//						threadRunning=false;
//					}
//					Thread.sleep(60 * 1000);
//				}
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//
//		});

	}

	public static void main(String[] args) throws IOException, InterruptedException, MessagingException {
//		while (true) {
//			Process p = Runtime.getRuntime().exec("tasklist /FI \"PID eq " + args[0] + "\"");
//			InputStream fis = p.getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//			if (br.readLine() == null) {
//				//进程没了发邮件提醒
//				ProcessMonitor monitorProcess = new ProcessMonitor();
//				monitorProcess.Email();
//				System.exit(0);
//			}
//			Thread.sleep(60 * 1000);
//		}
	}
//	public void Email() throws UnsupportedEncodingException, MessagingException {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.qq.com");
//		props.put("mail.smtp.auth", "true");
//			PopupAuthenticator auth = new PopupAuthenticator();
//			Session session = Session.getInstance(props, auth);
//			session.setDebug(true);
//			MimeMessage message = new MimeMessage(session);
//			Address addressFrom = new InternetAddress(
//			PopupAuthenticator.mailuser, "啊啊");//
//			Address addressTo = new InternetAddress("123456@qq.com", "LUO");//发送给谁
//			message.setSubject("QQ农场外挂已经关闭");//题目
//			message.setText("QQ农场外挂已经关闭");//邮件内容
//			message.setFrom(addressFrom);
//			message.addRecipient(Message.RecipientType.TO, addressTo);
//			message.saveChanges();
//			Transport transport = session.getTransport("smtp");
//			transport.connect("smtp.qq.com", PopupAuthenticator.mailuser,
//			PopupAuthenticator.password);
//			transport.send(message);
//			transport.close();
//			System.out.println("sent suc");
//	}
//
//	class PopupAuthenticator extends Authenticator {
//		public static final String mailuser = "10000@qq.com";//需要登录自己的邮箱
//		public static final String password = "123456";//自己邮箱密码
//
//		public PasswordAuthentication getPasswordAuthentication() {
//			return new PasswordAuthentication(mailuser, password);
//		}
//	}
}
