package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.*;

import User.DAO;
import User.DTO;

public class ChargeFrame extends JFrame implements ActionListener {
	static int charge_num;
	JScrollPane scrollpane;
	ImageIcon img;
	JTextField coin_field = new JTextField();
	JButton[] button = new JButton[6];
	int[] arr = { 1, 2, 3, 5, 10, 20 };
	DTO dto = new DTO();
	DAO dao = new DAO();
	static JTextField id_field = new JTextField();

	ChargeFrame(int num) {
		charge_num=num;
		Font font1 = new Font("맑은 고딕", Font.BOLD, 13);
		Font font2 = new Font("맑은 고딕", Font.BOLD, 16);

		img = new ImageIcon("Image/LoginFrame.png");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		setTitle("충전");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel.setLayout(null);
		setLocationRelativeTo(null);

		JTextField name_field = new JTextField(dao.getName(num));
		JTextField id_field = new JTextField(dao.getID(num));
		name_field.setBounds(30, 40, 150, 35);
		id_field.setBounds(210, 40, 150, 35);
		coin_field.setBounds(120, 165, 150, 35);
		name_field.setFont(font2);
		id_field.setFont(font2);
		coin_field.setFont(font2);
		panel.add(name_field);
		panel.add(id_field);
		panel.add(coin_field);
		name_field.setHorizontalAlignment(JTextField.CENTER);
		id_field.setHorizontalAlignment(JTextField.CENTER);
		coin_field.setHorizontalAlignment(JTextField.CENTER);
		name_field.setEditable(false);
		id_field.setEditable(false);
		coin_field.setEditable(false);
		
		JButton charge_button = new JButton("충전하기");
		JButton back_button = new JButton("Back");
		charge_button.setBounds(270, 100, 90, 40);
		back_button.setBounds(0, 0, 70, 30);
		charge_button.setFont(font1);
		back_button.setFont(font1);
		panel.add(charge_button);
		panel.add(back_button);
		
		int x=30, y=90, z=5;
		for(int i=0;i<3;i++) { // 1,2,3곡 버튼 구현 반복문
			button[i] = new JButton((i+1)+"곡");
			button[i].setBounds(x,y,65,25);
			button[i].setFont(font1);
			panel.add(button[i]);
			x=x+75;
		}
		
		x=30; y=125;
		for(int i=3;i<6;i++) { // 5,10,20곡 버튼 구현 반복문
			if(i!=5)
				button[i] = new JButton(z+"곡");
			else
				button[i] = new JButton((z+5)+"곡");
			button[i].setBounds(x,y,65,25);
			button[i].setFont(font1);
			panel.add(button[i]);
			x=x+75;
			z=z+5;
		}
		
		for (int i = 0; i < 6; i++)
			button[i].addActionListener(this);
		
		scrollpane = new JScrollPane(panel);
		setContentPane(scrollpane);

		setVisible(true);
		
		charge_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dao.setRemain(num, Integer.parseInt(coin_field.getText()));
				JOptionPane.showMessageDialog(null, "충전이 완료 되었습니다.");
				// setRemain이라는 DAO클래스에서 만든 메소드를 사용. 인자는 (스트링,인트)
				// coin_field 안에 있는 텍스트를 getText()로 가져온 후
				// Integer.parseInt() 를 씌워서 Int형으로 변환
				// 잔여 곡 불러오기
			}
		});
		
		back_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RoomFrame(num);
				setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		new ChargeFrame(charge_num);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<6;i++) {
			if (e.getSource() == button[i]) {
				coin_field.setText(String.valueOf(arr[i]));
				// coin_field 안에서 arr[i]를 String.valueOf()로 씌워서 String으로 변환 후
				// setText()로 텍스트를 적용
				coin_field.setVisible(true);
			}
		}
	}
}
