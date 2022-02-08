package view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import User.DAO;
import User.DTO;

public class RoomFrame extends JFrame implements ActionListener{
	static int room_num;
	JScrollPane scrollpane;
	ImageIcon img;
	JTextField coin_field = new JTextField();
	JButton[] button = new JButton[6];
	int[] arr = { 1, 2, 3, 5, 10, 20 };
	DAO dao;
	DTO dto;
	
	RoomFrame(int num) {
		Font font1 = new Font("맑은 고딕", Font.BOLD, 13);
		Font font2 = new Font("맑은 고딕", Font.BOLD, 16);
		
		if (num < 17) { 
			img = new ImageIcon("Image/StandardRoom.png");
			setTitle(num + " 스탠다드 룸");
			room_num = num; 
		} else { 
			img = new ImageIcon("Image/FamilyRoom.png");
			setTitle(num + " 패밀리 룸");
			room_num = num;
		}
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		setSize(650, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		background.setLayout(null);
		setLocationRelativeTo(null);
		scrollpane = new JScrollPane(background);
		setContentPane(scrollpane);
		setVisible(true);

		JTextField name_field = new JTextField(dao.getName(num));
		JTextField id_field = new JTextField(dao.getID(num));
		JTextField remain_field = new JTextField("잔여");
		JTextField using_field = new JTextField("사용");
		JTextField remainCoin_field = new JTextField(dao.getRemain(num));
		JTextField usingCoin_field = new JTextField(dao.getUsingCoin(num));
		
		name_field.setBounds(155, 0, 150, 35);
		id_field.setBounds(335, 0, 150, 35);
		remain_field.setBounds(70, 50, 70, 35);
		using_field.setBounds(70, 100, 70, 35);
		remainCoin_field.setBounds(150, 50, 90, 35);
		usingCoin_field.setBounds(150, 100, 90, 35);
		coin_field.setBounds(185, 340, 90, 35);
		name_field.setFont(font2);
		id_field.setFont(font2);
		remain_field.setFont(font2);
		using_field.setFont(font2);
		remainCoin_field.setFont(font2);
		usingCoin_field.setFont(font2);
		coin_field.setFont(font2);
		background.add(name_field);
		background.add(id_field);
		background.add(remain_field);
		background.add(using_field);
		background.add(remainCoin_field);
		background.add(usingCoin_field);
		background.add(coin_field);
		name_field.setHorizontalAlignment(JTextField.CENTER);
		id_field.setHorizontalAlignment(JTextField.CENTER);
		remain_field.setHorizontalAlignment(JTextField.CENTER);
		using_field.setHorizontalAlignment(JTextField.CENTER);
		remainCoin_field.setHorizontalAlignment(JTextField.CENTER);
		usingCoin_field.setHorizontalAlignment(JTextField.CENTER);
		coin_field.setHorizontalAlignment(JTextField.CENTER);
		name_field.setEditable(false);
		id_field.setEditable(false);
		remain_field.setEditable(false);
		using_field.setEditable(false);
		remainCoin_field.setEditable(false);
		usingCoin_field.setEditable(false);
		coin_field.setEditable(false);
		
		JButton use_button = new JButton("사용하기");
		JButton start_button = new JButton("시작하기");
		JButton charge_button = new JButton("충전하기");
		JButton back_button = new JButton("Back");
		use_button.setBounds(185, 260, 90, 35);
		start_button.setBounds(185, 300, 90, 35);
		charge_button.setBounds(545, 0, 90, 30);
		back_button.setBounds(0, 0, 70, 30);
		use_button.setFont(font1);
		start_button.setFont(font1);
		charge_button.setFont(font1);
		back_button.setFont(font1);
		background.add(use_button);
		background.add(start_button);
		background.add(charge_button);
		background.add(back_button);
		
		int x=115, y=255, z=5;
		for(int i=0;i<3;i++) {
			button[i] = new JButton((i+1)+"곡");
			button[i].setBounds(x,y,65,25);
			button[i].setFont(font1);
			background.add(button[i]);
			y=y+30;
		}
		
		x=280; y=255;
		for(int i=3;i<6;i++) {
			if(i!=5)
				button[i] = new JButton(z+"곡");
			else
				button[i] = new JButton((z+5)+"곡");
			button[i].setBounds(x,y,65,25);
			button[i].setFont(font1);
			background.add(button[i]);
			y=y+30;
			z=z+5;
		}
		
		for (int i = 0; i < 6; i++)
			button[i].addActionListener(this);
		
		scrollpane = new JScrollPane(background);
		setContentPane(scrollpane);
		setVisible(true);
		
		start_button.addActionListener(new ActionListener() { //시작 버튼을 눌렀을 때 액션처리
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(dao.getUsingCoin(num))==0) { //사용할 수 있는 코인이 0개일 때
					JOptionPane.showMessageDialog(null, "사용 가능한 곡이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}
				usingCoin_field.setText(String.valueOf(Integer.parseInt(usingCoin_field.getText())-1));
				dao.MinusUsingCoin(num);
			}
		});
		
		use_button.addActionListener(new ActionListener() { //사용하기 버튼을 눌렀을 때 액션처리
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, coin_field.getText()+"곡 사용하시겠습니까?", 
				"사용 확인", JOptionPane.YES_NO_OPTION);
				if(Integer.parseInt(dao.getRemain(num))==0) {
		               JOptionPane.showMessageDialog(null, "잔여 곡이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
		            }
				else if(result==0) { // 확인 버튼이 YES 값이면
					dao.setUsingCoin(num,Integer.parseInt(coin_field.getText()));
					dao.MinusRemain(num,Integer.parseInt(coin_field.getText()));
					// MinusRemain이라는 DAO클래스에서 만든 메소드를 사용. 인자는 (스트링,인트)
					// coin_field 안에 있는 텍스트를 getText()로 가져온 후
					// Integer.parseInt() 를 씌워서 Int형으로 변환
					// 사용하기를 누르면 잔여 곡에서 차감
					usingCoin_field.setText(String.valueOf(Integer.parseInt(usingCoin_field.getText())+Integer.parseInt(coin_field.getText())));
					// usingCoin_field = 기존 usingCoin_field 필드에 있는 곡 + coin_field
					remainCoin_field.setText(dao.getRemain(num));
					coin_field.setText("");
				}
			}
		});
		
		charge_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChargeFrame(num);
				setVisible(false);
			}
		});
		
		back_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChooseRoom(num);
				setVisible(false);
			}
		});
	}
	public static void main(String[] args) {
		new RoomFrame(room_num);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<6;i++) {
			if (e.getSource() == button[i]) {
				coin_field.setText(String.valueOf(arr[i]));
				coin_field.setVisible(true);
			}
		}
		
	}

}
