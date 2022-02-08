package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import User.DTO;
import User.DAO;

public class JoinFrame extends JFrame {
	JScrollPane scrollpane;
	ImageIcon img;
	DTO dto = new DTO();
	DAO dao = new DAO();
	ChooseRoom num;
	static int loginCnt, join_num;
	
	JoinFrame(int num) {
		join_num=num;
		Font font1 = new Font("맑은 고딕", Font.BOLD, 13); //폰트 추가
		Font font2 = new Font("맑은 고딕", Font.BOLD, 15);

		img = new ImageIcon("Image/JoinFrame.png"); //JoinFrame 배경 설정
		JPanel panel = new JPanel() { //gui를 위한 패널 생성
			public void paintComponent(Graphics g) {
				g.drawImage(img.getImage(), 0, 0, null); //img를 불러와 출력함. 시작지점은(0,0)
				setOpaque(false); //투명도 설정
				super.paintComponent(g);
			}
		};

		setTitle("회원가입"); //프레임 제목
		setSize(425, 500); //전체 화면 크기
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //윈도우 창(JFrame) 종료 시 하나의 프레임만 종료 됨
		panel.setLayout(null); //레이아웃 지정 안함
		setLocationRelativeTo(null); //윈도우 창을 화면 가운데로 설정

		JLabel join_label = new JLabel("JOIN"); //텍스트 사용을 위해 JLabel 변수 선언
		JLabel name_label = new JLabel("성         명  :");
		JLabel id_label = new JLabel("아   이   디  :");
		JLabel pwd_label = new JLabel("비 밀 번 호  : ");
		JLabel checkpwd_label = new JLabel("비밀번호확인 : ");
		JLabel errorpwd_label = new JLabel("비밀번호가 맞지 않습니다.");
		join_label.setBounds(190, 15, 100, 45); //JLable 위치 설정
		name_label.setBounds(20, 85, 100, 25);
		id_label.setBounds(20, 155, 100, 25);
		pwd_label.setBounds(20, 225, 100, 25);
		checkpwd_label.setBounds(15, 295, 100, 25);
		errorpwd_label.setBounds(30, 325, 100, 25);
		join_label.setFont(font2); //위에서 설정한 폰트 적용
		name_label.setFont(font1);
		id_label.setFont(font1);
		pwd_label.setFont(font1);
		checkpwd_label.setFont(font1);
		errorpwd_label.setFont(font1);
		join_label.setForeground(Color.BLACK); //폰트의 색상 적용
		name_label.setForeground(Color.BLACK);
		id_label.setForeground(Color.BLACK);
		pwd_label.setForeground(Color.BLACK);
		checkpwd_label.setForeground(Color.BLACK);
		errorpwd_label.setForeground(Color.RED);
		panel.add(join_label); //패널에 라벨 적용
		panel.add(name_label);
		panel.add(id_label);
		panel.add(pwd_label);
		panel.add(checkpwd_label);

		JTextField name_field = new JTextField(); //타이핑한 글씨가 보이는 JTextField
		JTextField id_field = new JTextField();
		JPasswordField pwd_field = new JPasswordField(); // 타이핑한 글씨가 보이지 않는 JPasswordField
		JPasswordField checkpwd_field = new JPasswordField();
		name_field.setBounds(115, 85, 180, 25); //JTextField 위치 설정
		id_field.setBounds(115, 155, 180, 25);
		pwd_field.setBounds(115, 225, 180, 25);
		checkpwd_field.setBounds(115, 295, 180, 25);
		panel.add(name_field); //패널에 라벨 적용
		panel.add(id_field);
		panel.add(pwd_field);
		panel.add(checkpwd_field);

		JButton back_button = new JButton("Back"); //뒤로 가기 버튼
		JButton checklogin_button = new JButton("중복 확인"); //중복 확인 버튼
		JButton join_button = new JButton("회원가입"); //회원가입 버튼
		back_button.setBounds(0, 0, 70, 30); //JButton 위치 설정
		checklogin_button.setBounds(300, 155, 100, 25);
		join_button.setBounds(160, 360, 100, 50);
		back_button.setFont(font1); //위에서 설정한 폰트 적용
		checklogin_button.setFont(font1);
		join_button.setFont(font1);
		panel.add(back_button); //패널에 라벨 적용
		panel.add(checklogin_button);
		panel.add(join_button);

		scrollpane = new JScrollPane(panel); //JScrollPane에 panel을 생성자를 이용하여 추가
		setContentPane(scrollpane);

		setVisible(true); //창을 보이게 함

		dao.createTable(); //DAO클래스의 createTable메소드를 실행하여 테이블 생성 
		checklogin_button.addActionListener(new ActionListener() { //중복확인 버튼을 눌렀을 시 이벤트처리
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dao.checkID(id_field.getText())==true) { //DAO클래스의 checkID메소드를 불러와 입력한id필드와 기존db상에 회원가입 되어있는 아이디와 중복 시
					JOptionPane.showMessageDialog(null, "이미 사용중인 아이디입니다.", //알림창을 띄워주는 함수, 출력할 문자열
							"아이디 중복", JOptionPane.WARNING_MESSAGE); //제목표시줄에 나타날 제목과 메세지종류 설정 : 경고메세지
					loginCnt=0;
				}
				else {
					JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
					loginCnt=1;
				}
			}
		});
		
		join_button.addActionListener(new ActionListener() { //회원가입 버튼을 눌렀을 시 이벤트처리
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name_field.getText().trim().length()==0) { //이름 필드에서 공백 제거(trim)후 길이값이 0일경우(이름을 입력하지 않은 경우)
					JOptionPane.showMessageDialog(null, "성명을 입력해 주세요.",
							"성명 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(id_field.getText().trim().length()==0) { //아이디 필드에서 공백 제거(trim)후 길이값이 0일경우(아이디를 입력하지 않은 경우)
					JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요.",
							"아이디 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(loginCnt==0) {
					JOptionPane.showMessageDialog(null, "아이디 중복 확인이 필요합니다.",
							"아이디 중복 확인", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(pwd_field.getText().trim().length()==0) { //비밀번호 필드에서 공백 제거(trim)후 길이값이 0일경우(비밀번호를 입력하지 않은 경우)
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해 주세요.",
							"비밀번호 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(checkpwd_field.getText().trim().length()==0) { //비밀번호 확인 필드에서 공백 제거(trim)후 길이값이 0일경우
					JOptionPane.showMessageDialog(null, "비밀번호 확인을 입력해 주세요.",
							"비밀번호 확인 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(!(pwd_field.getText().trim().equals(checkpwd_field.getText().trim()))) { //비밀번호란과 비밀번호확인란이 같지 않을 경우
					JOptionPane.showMessageDialog(null, "비밀번호가 같지 않습니다.",
							"비밀번호 확인", JOptionPane.WARNING_MESSAGE);
					return;
				}
				dto.setName(name_field.getText()); //DTO클래스의 메소드에 값을 전달
				dto.setID(id_field.getText());
				dto.setPW(pwd_field.getText());
				loginCnt=0; //loginCnt를 0으로 초기화
				new LoginFrame(num);
				setVisible(false);
				try {
					dao.createUser(dto.getName(), dto.getID(), dto.getPW());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		back_button.addActionListener(new ActionListener() { //뒤로가기 버튼 클릭시 액션처리
			@Override
			public void actionPerformed(ActionEvent e) {
				loginCnt=0;
				new LoginFrame(num);
				setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		new JoinFrame(join_num);
	}
}