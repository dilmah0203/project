package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

import User.DAO;

public class ChooseRoom extends JFrame {
	static int room_num; // 메인 메소드에서 ChooseRoom의 정수형 인자를 저장하기 위한 변수
	JScrollPane scrollpane;
	ImageIcon img;
	LoginFrame loginframe;
	DAO dao;
	
	ChooseRoom(int num) {
		Font font1 = new Font("맑은 고딕", Font.BOLD, 13);
		
		if (num < 17) { // 스탠다드 룸(1~16번)에 관한 조건문
			img = new ImageIcon("Image/StandardRoom.png");
			setTitle(num + " 스탠다드 룸");
			room_num = num; // 전역 변수에 인자를 저장
		} else { // 패밀리 룸(17~21번)에 관한 조건문
			img = new ImageIcon("Image/FamilyRoom.png");
			setTitle(num + " 패밀리 룸");
			room_num = num;
		}
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
				
			}
		};

		setSize(650, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		background.setLayout(null);
		setLocationRelativeTo(null);
		scrollpane = new JScrollPane(background);
		setContentPane(scrollpane);
		setVisible(true);

		JLabel logout_label = new JLabel("로그아웃 시 비밀번호 입력");
		logout_label.setBounds(375, 30, 160, 30);
		logout_label.setFont(font1);
		logout_label.setForeground(Color.BLACK);
		background.add(logout_label);
		
		JPasswordField logout_field = new JPasswordField();
		logout_field.setBounds(365, 0, 180, 30);
		logout_field.setFont(font1);
		background.add(logout_field);
		
		JButton button = new JButton("입장하기");
		JButton logout_button = new JButton("로그아웃");
		JButton back_button = new JButton("Back");
		button.setBounds(280, 230, 90, 35);
		logout_button.setBounds(545, 0, 90, 30);
		back_button.setBounds(0, 0, 70, 30);
		button.setFont(font1);
		logout_button.setFont(font1);
		back_button.setFont(font1);
		background.add(button);
		background.add(logout_button);
		background.add(back_button);
		
		scrollpane = new JScrollPane(background);
		setContentPane(scrollpane);
		setVisible(true);
		
		Connection con = dao.getConnection(); //db와 연결하는 객체
		
		if (dao.getState("ONLINE", num)==false) {
			loginframe = new LoginFrame(num);
			loginframe.login_num=0;
		}
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dao.getState("ONLINE", num)==true) {
					new RoomFrame(num);
					setVisible(false);
				}
				else {
					loginframe.setVisible(true);
					JOptionPane.showMessageDialog(null, "로그인이 필요합니다.",
							"로그인 확인", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		});
		
		logout_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dao.getState("ONLINE", num)==false) {
					loginframe.setVisible(true);
					JOptionPane.showMessageDialog(null, "로그인이 필요합니다.",
							"로그인 확인", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(logout_field.getText().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해 주세요.",
							"비밀번호 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (dao.getLogin(dao.getID(num), logout_field.getText())==false) {
					JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.",
							"로그아웃 실패", JOptionPane.WARNING_MESSAGE);
					return;
				}
				else {
					JOptionPane.showMessageDialog(null, "로그아웃 성공");
					dao.resetUsingCoin(num);
					dao.setOffState(dao.getID(num));
					dao.setRoom(dao.getID(num), 0);
					setVisible(false);
					new SecondFrame();
				}
			}
		});
		
		back_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new SecondFrame();
				loginframe.setVisible(false);
			}
		}); 

	}

	public static void main(String[] args) {
		new ChooseRoom(room_num);

	}
}