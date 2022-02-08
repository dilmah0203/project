package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import User.DAO;
import User.DTO;

public class AdminLoginFrame extends JFrame {
	JScrollPane scrollpane;
	ImageIcon img;

	DTO dto = new DTO();
	DAO dao = new DAO();
	
	AdminLoginFrame() {
		Font font1 = new Font("맑은 고딕", Font.BOLD, 13);
		Font font2 = new Font("맑은 고딕", Font.BOLD, 15);

		img = new ImageIcon("Image/AdminLoginFrame.png");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		setTitle("관리자 로그인");
		setSize(410, 275);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
		setLocationRelativeTo(null);

		JLabel login_label = new JLabel("ADMIN LOGIN");
		JLabel id_label = new JLabel("아   이   디  :");
		JLabel pwd_label = new JLabel("비 밀 번 호  : ");
		login_label.setBounds(150, 5, 150, 45);
		id_label.setBounds(20, 50, 100, 25);
		pwd_label.setBounds(20, 85, 100, 25);
		login_label.setFont(font2);
		id_label.setFont(font1);
		pwd_label.setFont(font1);
		login_label.setForeground(Color.WHITE);
		id_label.setForeground(Color.WHITE);
		pwd_label.setForeground(Color.WHITE);
		panel.add(login_label);
		panel.add(id_label);
		panel.add(pwd_label);

		JTextField id_field = new JTextField();
		JPasswordField pwd_field = new JPasswordField();
		id_field.setBounds(115, 50, 180, 25);
		pwd_field.setBounds(115, 85, 180, 25);
		panel.add(id_field);
		panel.add(pwd_field);

		JButton back_button = new JButton("Back");
		JButton login_button = new JButton("로그인");
		JButton join_button = new JButton("관리자가입");
		back_button.setBounds(0, 0, 70, 30);
		login_button.setBounds(300, 50, 75, 60);
		join_button.setBounds(150, 150, 100, 50);
		back_button.setFont(font1);
		login_button.setFont(font1);
		join_button.setFont(font1);
		panel.add(back_button);
		panel.add(login_button);
		panel.add(join_button);
		
		scrollpane = new JScrollPane(panel);
		setContentPane(scrollpane);

		setVisible(true);
		
		login_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dao.getAdminLogin(id_field.getText(),pwd_field.getText())==false) {
					JOptionPane.showMessageDialog(null, "로그인 실패", 
							"로그인 실패", JOptionPane.WARNING_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "로그인 성공");
					new AdminFrame();
					setVisible(false);
				}
			}
		});
		
		join_button.addActionListener(new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent e) {
				if(dao.getAdminJoin()==true) {
					JOptionPane.showMessageDialog(null, "관리자가 존재합니다.", 
							"관리자 존재", JOptionPane.WARNING_MESSAGE);
				}
				else {
					new AdminJoinFrame(); 
					setVisible(false);
				}
			}
		});
		
		back_button.addActionListener(new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent e) {
				new SecondFrame(); 
				setVisible(false); 
			}
		});
	}

	public static void main(String[] args) {
		new AdminLoginFrame();
	}
}
