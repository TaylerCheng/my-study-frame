package com.cg.javacore.cstest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	
	public Socket s =null;
	public BufferedReader br = null;
	public BufferedReader br2 = null;
	public OutputStream os = null;
	
	public Client(int port){
		try {			
			s =new Socket("localhost", 9999);
            System.out.println("�ѽ������۳���������...");
            br2 = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			while (true) {
				//�ӿ���̨д����������
				System.out.print("�̹�:\t");				
				String requset = br2.readLine();
				//������������
				pw.println(requset);
							
				//��÷�������
				String response = br.readLine();
				System.out.println("�۳���:\t"+response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
				br.close();
				br2.close();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static void main(String[] args) {
		Client server = new Client(9999);
	}

}
