package view;

import javax.swing.*; //javax.swing.* 패키지에서 JFrame과 JButton을 가져옴.
import java.awt.*; //그래픽
import java.awt.event.*; //액션리스너



public class FirstFrame extends JFrame { 
	ImageIcon img;
	JButton button;
	
	FirstFrame() { //프레임에 만들 내용을 생성자 안에 작성
		img = new ImageIcon("Image/FirstFrame.png"); // 배경 이미지 로딩
		JPanel background = new JPanel() { //타이틀이 없는 판넬 생성()
			public void paintComponent(Graphics g) { // 이미지 그리기 위한 메소드
				super.paintComponent(g);
				g.drawImage(img.getImage(), 0, 0, null); //필요한 그리기 코드 작성
				setOpaque(true); // 그림을 표시하게 설정, 투명도 설정
				
			}
		};
		setTitle("코인노래방"); 
		setSize(1200, 750); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임의 x버튼을 활성화하여 닫기버튼이 실행가능해짐
		setLocationRelativeTo(null); // 창을 화면 상의 가운데에 정렬. null(기본값)은 가운데를 나타냄.
		
		background.setLayout(null); // 레이아웃 옵션을 사용하지 않음
		
		
		button = new JButton("ENTRANCE"); //문자열을 가진 버튼 생성
		button.setBounds(550, 400, 100, 50); 
		background.add(button); // 패널에 버튼 적용
		
		JScrollPane scrollpane = new JScrollPane(background); // 패널에 스크롤팬 변수 선언 및 생성
		setContentPane(scrollpane); // 컨텐트팬을 scrollpane 객체로 변경

		setVisible(true); // 화면에 프레임 출력
		
		button.addActionListener(new ActionListener() { // 버튼 클릭에 관한 메소드
			@Override // ActionListener메소드 사용을 위한 오버라이딩
			public void actionPerformed(ActionEvent e) {
				new SecondFrame(); // 버튼이 클릭되면 SecondFrame 클래스 호출
				setVisible(false); // 창 안보이게 하기
			}
		});
	}

	public static void main(String[] args) { 
		new FirstFrame(); // main에서 생성하면 창이 나타남
	}
}