package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import User.DAO;
import User.DTO;

public class AdminFrame extends JFrame implements MouseListener {
	DTO dto = new DTO();
	DAO dao = new DAO();
	JScrollPane scrollpane, scrollpane2;
	ImageIcon img;
	String[][] data = dao.getUsers();
	String[] headers = new String[] { "성명", "아이디", "잔여 코인", "사용 코인", "방", "상태" };
	JTable table = new JTable(data, headers);
	JTextField remain_field = new JTextField();
	JTextField room_field = new JTextField();
	String[] cell = new String[6];

	public void Refresh(JTable table) {
		table.setModel(new DefaultTableModel(dao.getUsers(), headers) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		}); // 셀 값 수정 불가

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel columnModels = table.getColumnModel();
		table.getTableHeader().setReorderingAllowed(false); // 테이블 컬럼 이동 방지
		columnModels.getColumn(0).setResizable(false); // 테이블 컬럼 크기 변경 불가
		columnModels.getColumn(1).setResizable(false);
		columnModels.getColumn(2).setResizable(false);
		columnModels.getColumn(3).setResizable(false);
		columnModels.getColumn(4).setResizable(false);
		columnModels.getColumn(5).setResizable(false);
		columnModels.getColumn(0).setPreferredWidth(120); // 테이블 컬럼 크기
		columnModels.getColumn(1).setPreferredWidth(120);
		columnModels.getColumn(2).setPreferredWidth(50);
		columnModels.getColumn(3).setPreferredWidth(50);
		columnModels.getColumn(4).setPreferredWidth(10);
		columnModels.getColumn(5).setPreferredWidth(40);

		for (int i = 0; i < columnModels.getColumnCount(); i++) {
			columnModels.getColumn(i).setCellRenderer(center);
		} // 셀 값을 가운데 정렬 하기 위한 반복문
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 셀 다중 선택 불가
	}

	AdminFrame() {
		Font font1 = new Font("맑은 고딕", Font.BOLD, 13);
		Font font2 = new Font("맑은 고딕", Font.BOLD, 15);

		setTitle("관리자 모드");
		setSize(650, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel tablepanel = new JPanel();
		tablepanel.setBounds(0, 0, 500, 450);
		tablepanel.setLayout(null);
		tablepanel.setVisible(true);

		table.setRowHeight(30);
		table.setFont(font1);
		table.setAlignmentX(0);
		table.setSize(500, 450);
		table.setPreferredScrollableViewportSize(new Dimension(500, 450));

		scrollpane2 = new JScrollPane(table);
		scrollpane2.setBounds(65, 75, 500, 450);
		tablepanel.add(scrollpane2);
		scrollpane = new JScrollPane(tablepanel);
		setContentPane(scrollpane);
		Refresh(table);

		JLabel search_label = new JLabel("조회 : ");
		JLabel remain_label = new JLabel("잔여 코인 : ");
		JLabel room_label = new JLabel("방 : ");
		search_label.setBounds(65, 20, 50, 35);
		remain_label.setBounds(65, 540, 100, 35);
		room_label.setBounds(225, 540, 100, 35);
		search_label.setFont(font2);
		remain_label.setFont(font2);
		room_label.setFont(font2);
		tablepanel.add(search_label);
		tablepanel.add(remain_label);
		tablepanel.add(room_label);

		JTextField search_field = new JTextField();
		search_field.setBounds(115, 20, 385, 35);
		remain_field.setBounds(150, 540, 50, 35);
		room_field.setBounds(260, 540, 50, 35);
		search_field.setFont(font2);
		remain_field.setFont(font2);
		room_field.setFont(font2);
		remain_field.setHorizontalAlignment(JTextField.CENTER);
		room_field.setHorizontalAlignment(JTextField.CENTER);
		tablepanel.add(search_field);
		tablepanel.add(remain_field);
		tablepanel.add(room_field);

		JButton back_button = new JButton("탈출");
		JButton refresh_button = new JButton("갱신");
		JButton revise_button = new JButton("수정");
		JButton merge_button = new JButton("병합");
		JButton delete_button = new JButton("삭제");
		back_button.setBounds(0, 0, 60, 30);
		refresh_button.setBounds(505, 20, 60, 35);
		revise_button.setBounds(345, 540, 60, 35);
		merge_button.setBounds(425, 540, 60, 35);
		delete_button.setBounds(505, 540, 60, 35);
		back_button.setFont(font1);
		refresh_button.setFont(font1);
		revise_button.setFont(font1);
		merge_button.setFont(font1);
		delete_button.setFont(font1);
		tablepanel.add(back_button);
		tablepanel.add(refresh_button);
		tablepanel.add(revise_button);
		tablepanel.add(merge_button);
		tablepanel.add(delete_button);

		search_field.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String val = search_field.getText();
				TableRowSorter<TableModel> trs = new TableRowSorter<>(table.getModel());
				table.setRowSorter(trs);
				trs.setRowFilter(RowFilter.regexFilter(val));
			}
		});

		refresh_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remain_field.setText("");
				room_field.setText("");
				cell[0] = null;
				Refresh(table);
			}
		});

		cell[0] = null;

		revise_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cell[0] == null) {
					JOptionPane.showMessageDialog(null, "회원을 선택해주세요.", "회원 선택", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (remain_field.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "잔여 코인을 입력해 주세요.", "잔여 코인 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (room_field.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "방을 입력해 주세요.", "방 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (Integer.parseInt(remain_field.getText()) < 0 || Integer.parseInt(remain_field.getText()) > 255) {
					JOptionPane.showMessageDialog(null, "올바른 입력(0 ~ 255)이 아닙니다.", "부적절 입력",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (Integer.parseInt(room_field.getText()) < 0 || Integer.parseInt(room_field.getText()) > 21) {
					JOptionPane.showMessageDialog(null, "올바른 입력(0 ~ 21)이 아닙니다.", "부적절 입력", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (dao.checkRoom(Integer.parseInt(room_field.getText())) == true) {
					if (Integer.parseInt(room_field.getText()) != 0
							&& Integer.parseInt(room_field.getText()) != Integer.parseInt(cell[4])) {
						JOptionPane.showMessageDialog(null, "이미 사용중인 방입니다.", "방 중복", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				dao.ChangeRemain(cell[1], Integer.parseInt(remain_field.getText()));
				dao.setRoom(dao.getID(Integer.parseInt(cell[4])), Integer.parseInt(room_field.getText()));
				if (Integer.parseInt(room_field.getText()) == 0)
					dao.setOffState(cell[1]);
				else
					dao.setOnState(cell[1]);
				remain_field.setText("");
				room_field.setText("");
				cell[0] = null;
				Refresh(table);
			}
		});

		merge_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Integer.parseInt(cell[3]) == 0) {
					JOptionPane.showMessageDialog(null, "사용 코인이 없습니다.", "부적절 병합", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int result = JOptionPane.showConfirmDialog(null, "사용 코인 " + cell[3] + "개를 잔여 코인으로 병합하시겠습니까?", "병합 확인",
						JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					dao.MergeUsingCoin(cell[1]);
					remain_field.setText("");
					room_field.setText("");
					cell[0] = null;
					Refresh(table);
				}
				else {
					remain_field.setText("");
					room_field.setText("");
					cell[0] = null;
					Refresh(table);
				}
			}
		});

		delete_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, cell[0] + " (" + cell[1] + ") 회원을 삭제하시겠습니까?", "삭제 확인",
						JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					dao.deleteUser(cell[1]);
					remain_field.setText("");
					room_field.setText("");
					cell[0] = null;
					Refresh(table);
				}
				else {
					remain_field.setText("");
					room_field.setText("");
					cell[0] = null;
					Refresh(table);
				}
			}
		});
		
		back_button.addActionListener(new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent e) {
				new AdminLoginFrame(); 
				setVisible(false); 
			}
		});

		table.addMouseListener(this);

		setVisible(true);
	}

	public static void main(String[] args) {
		new AdminFrame();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		for (int i = 0; i < table.getColumnCount(); i++) {
			cell[i] = String.valueOf(table.getModel().getValueAt(row, i));
		}
		remain_field.setText(cell[2]);
		room_field.setText(cell[4]);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
