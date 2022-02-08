package view;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import User.DAO;
import User.DTO;

public class SecondFrame extends JFrame implements ActionListener {
	ImageIcon img;
	JButton[] button_img = new JButton[21]; // 21크기 배열의 버튼 변수 선언 및 생성(방이 21개이므로)
	JButton admin_button;
	DAO dao;
	DTO dto;

	ImageIcon img1 = new ImageIcon("Image/StandardSeat_off.png"); // 버튼 기본 이미지
	ImageIcon img2 = new ImageIcon("Image/StandardSeat_on.png"); // 버튼에 마우스가 올라갈 때 이미지
	ImageIcon img3 = new ImageIcon("Image/FamillySeat_off.png"); // 버튼 기본 이미지
	ImageIcon img4 = new ImageIcon("Image/FamillySeat_on.png"); // 버튼에 마우스가 올라갈 때 이미지
	ImageIcon img5 = new ImageIcon("Image/Exit.png"); // 비상구 이미지

	SecondFrame() {
		img = new ImageIcon("Image/SecondFrame.png");
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
				
			}
		};
		admin_button = new JButton("", img5); // 들어갈 문자열 없음, 비상구 버튼 이미지
		admin_button.setBounds(565, 625, 75, 75);
		background.add(admin_button);
		
		setTitle("방 현황"); // SecondFrame 제목 설정
		setSize(1200, 750); // 전체 화면 크기 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 윈도우 창(JFrame) 종료 시 프로세스까지 종료 됨
		background.setLayout(null); // 레이아웃 지정 안함
		setLocationRelativeTo(null); // 윈도우 창을 화면 가운데로 설정

		// 이중 반복문 사용한 방(버튼)구현
		int x = 50, y = 550, cnt = 0, num = 0; // x좌표 초기값, y좌표 초기값, 반복문에 사용할 cnt,num 변수 선언 및 초기화

		for (int i = 0; i < 7; i++) { // 총 7가지 경우를 수행하는 큰 반복문
			if (i >= 1 && i < 5) { // 6~11번 방을 위한 조건문
				num = 2; // cnt가 4~5, 6~7, 8~9, 10~11 간격을 가지기 위한 값
				x = 250 * i; // x값이 각각 250,500,750,1000
				cnt = (2 * i) + 2; // cnt값이 각각 4,6,8,10
				y = 450; // y좌표를 450으로 초기화
				if (i == 2 || i == 4) // 6,10번 방에서 각각 7,11번 방으로 넘어가기 위한 조건문
					x = x - 150; // i가 2 또는 4일 경우 x좌표는 각각 500-150, 1000-150
			} else if (i == 0) // 1~4번 방을 위한 조건문
				num = 4; // cnt가 0~3 간격을 가지기 위한 값
			else if (i == 5) { // 13~16번 방을 위한 조건문
				num = 4; // cnt가 12~15 간격을 가지기 위한 값
				x = 1050;
				y = 550;
				cnt = cnt + 2; // 12번 방에서 13번 방으로 넘어가기 위해 cnt 값 증가
			} else if (i == 6) { // 17~21번 방을 위한 조건문
				num = 5; // cnt가 16~20 간격을 가지기 위한 값
				x = 50;
				y = 150;
				cnt = cnt + 4; // 16번 방에서 17번 방으로 넘어가기 위해 cnt 값 증가
			}
			for (int j = cnt; j < cnt + num; j++) { // 각각 4,2,2,2,2,4,5번으로 나뉘는, 총 21번의 반복을 수행하기 위한 작은 반복문
				if (i == 6) { // 17~21번 방(패밀리 룸)의 구현을 위한 조건문
					if (dao.getState("ONLINE", j + 1) == false) // DAO클래스의 getState메소드 호출
						button_img[j] = new JButton(
								"<HTML><body><center>" + (j + 1) + "<br>" + "패밀리<br>룸</center></body></HTML>", img3);
					// j+1번째, 버튼, 배열, 생성(텍스트, 이미지 삽입)
					else
						button_img[j] = new JButton(
								"<HTML><body><center>" + (j + 1) + "<br>" + "패밀리<br>룸</center></body></HTML>", img4);

					button_img[j].setHorizontalTextPosition(JButton.CENTER); // 버튼을 수평 기준 가운데 정렬
					button_img[j].setRolloverIcon(img4); // 버튼에 마우스가 올라갈 때 img4로 이미지 변환
					button_img[j].setBounds(x, y, 200, 100); // 위치 설정
					x = x + 225; // x좌표가 +225만큼 이동
				} else { // 1~16번 방(스탠다드 룸)의 구현을 위한 조건문
					if (dao.getState("ONLINE", j + 1) == false)
						button_img[j] = new JButton(
								"<HTML><body><center>" + (j + 1) + "<br>" + "스탠다드<br>룸</center></body></HTML>", img1);
					else
						button_img[j] = new JButton(
								"<HTML><body><center>" + (j + 1) + "<br>" + "스탠다드<br>룸</center></body></HTML>", img2);
					button_img[j].setHorizontalTextPosition(JButton.CENTER);
					button_img[j].setRolloverIcon(img2);
					button_img[j].setBounds(x, y, 100, 100);
					y = y - 100; // y좌표가 -100만큼 이동
				}
				button_img[j].setBorderPainted(false); // 버튼 테두리 설정 해제
				background.add(button_img[j]);
			}
		}

		for (int i = 0; i < 21; i++)
			button_img[i].addActionListener(this); // 액션 메소드 버튼 적용
		admin_button.addActionListener(this);

		JScrollPane scrollpane = new JScrollPane(background);
		setContentPane(scrollpane);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 21; i++) {
			if (e.getSource() == button_img[i]) { //e.getSource() : 이벤트 발생 시 해당되는 변수를 불러옴
				new ChooseRoom(i + 1); // 버튼이 클릭되면 ChooseRoom 클래스 호출
				setVisible(false);
				break; // (i + 1) 인자는 방 호수를 전달하기 위함
			}

			if (e.getSource() == admin_button) {
				new AdminLoginFrame();
				setVisible(false);
				break;
			}
		}
	}

	public static void main(String[] args) {
		new SecondFrame();
	}
}