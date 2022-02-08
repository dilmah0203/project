package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import User.DAO;
import User.DTO;

public class LoginFrame extends JFrame {
	static int login_num;
	JScrollPane scrollpane;
	ImageIcon img;
	DTO dto = new DTO();
	DAO dao = new DAO();
	static JTextField id_field = new JTextField(); 
	
	public static int getLoginNum(int num) {
		if(login_num==num)
			return num;
		return 0;
	}
	
	LoginFrame(int num) {
		Font font1 = new Font("맑은 고딕", Font.BOLD, 13); 
		Font font2 = new Font("맑은 고딕", Font.BOLD, 15);

		img = new ImageIcon("Image/LoginFrame.png");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		setTitle("로그인");
		setSize(400, 275);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel.setLayout(null);
		setLocationRelativeTo(null);

		JLabel login_label = new JLabel("LOGIN"); 
		JLabel id_label = new JLabel("아   이   디  :");
		JLabel pwd_label = new JLabel("비 밀 번 호  : ");
		login_label.setBounds(175, 5, 100, 45);
		id_label.setBounds(20, 50, 100, 25);
		pwd_label.setBounds(20, 85, 100, 25);
		login_label.setFont(font2); // 폰트 적용
		id_label.setFont(font1);
		pwd_label.setFont(font1);
		login_label.setForeground(Color.BLACK); 
		id_label.setForeground(Color.BLACK);
		pwd_label.setForeground(Color.BLACK);
		panel.add(login_label); 
		panel.add(id_label);
		panel.add(pwd_label);

		JPasswordField pwd_field = new JPasswordField(); // 타이핑한 글씨가 보이지 않는 JPasswordField
		id_field.setBounds(115, 50, 180, 25);
		pwd_field.setBounds(115, 85, 180, 25);
		panel.add(id_field);
		panel.add(pwd_field);

		JButton login_button = new JButton("로그인");
		JButton join_button = new JButton("회원가입");
		login_button.setBounds(300, 50, 75, 60);
		join_button.setBounds(150, 150, 100, 50);
		login_button.setFont(font1);
		join_button.setFont(font1);
		panel.add(login_button);
		panel.add(join_button);
		
		scrollpane = new JScrollPane(panel);
		setContentPane(scrollpane);

		setVisible(true);
		
		login_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(id_field.getText().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요.",
							"아이디 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(pwd_field.getText().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해 주세요.",
							"비밀번호 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(dao.getLogin(id_field.getText(),pwd_field.getText())==false) {
					JOptionPane.showMessageDialog(null, "로그인 실패", 
							"로그인 실패", JOptionPane.WARNING_MESSAGE);
					return;
				}
				else {
					JOptionPane.showMessageDialog(null, "로그인 성공");
					setVisible(false);
					login_num=num;
					dao.setOnState(id_field.getText());
					dao.setRoom(id_field.getText(), num);
				}
			}
		});
		
		join_button.addActionListener(new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent e) {
				new JoinFrame(num); 
				setVisible(false);
			}
		});
	}

	public static void main(String[] args) { //로그인프레임 메인 메서드에 실행시키기위해 login_num을 생성하여 위에서 받은 인자를 넣어줌
		new LoginFrame(login_num);
	}
}
