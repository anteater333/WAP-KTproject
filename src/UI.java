import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class UI {

   // 변수 선언
   private Dimension screenSize;
   private JPanel detailSearchPanel, mapPanel, dataPanel1, dataPanel2, dataPanel3, dataPanel4, dataPanel5,
         distancePanel, distanceNumberPanel, distanceCustomPanel;
   private JTextField detailSearchTF;
   private JCheckBox mapCheckbox, rsrpCheckBox, potCheckBox, excelCheckBox;
   private static JFrame jf;
   private JTable resultTable;
   private map mapJF;
   private String RawFileName, TargetFileName;
   private JTextField jtx1, jtx2, jtx3, jtx4, jtx5;
   private excelData centerData; // 기지국의 ID 또는 국소명
   private ArrayList<excelData> save1Result; // 실행버튼 후 어레이리스트
   private ArrayList<excelData> save2Result; // exeResult에서 ID 또는 국소명을 검색한 것에
   // 대한 결과의 어레이 리스트
   private Logic lgc;

   public UI() {
      init();
   }

   // 변수와 맵 초기화 함수
   private void init() {
      GUI();
      save1Result = new ArrayList<excelData>();
      save2Result = new ArrayList<excelData>();
   }

   // GUI 생성 함수
   private void GUI() {
      JFrame Jf = new JFrame("KT");
      Jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Jf.setSize(screenSize.width * 2 / 3, screenSize.height * 3 / 4);
      Jf.setLayout(new BorderLayout(10, 10));

      // 위쪽 Panel GUI
      JPanel top = GUI_TOP();
      JPanel west = new JPanel();
      west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
      west.setBorder(new CompoundBorder(new EmptyBorder(0, 15, 5, 2),
            BorderFactory.createTitledBorder(new BevelBorder(BevelBorder.RAISED), "KT 데이터 입력")));

      // 좌측 상단의 위치검증 GUI
      JPanel locationPanel = Location_Border();
      locationPanel
            .setBorder(new CompoundBorder(new EmptyBorder(0, 15, 5, 2), BorderFactory.createTitledBorder("위치 검증")));

      // 좌측 하단의 상세표시 GUI
      JPanel detailPanel = new JPanel(new GridLayout(2, 1));
      detailPanel
            .setBorder(new CompoundBorder(new EmptyBorder(3, 5, 0, 5), BorderFactory.createTitledBorder("상세 표시")));
      detailSearchPanel = new JPanel();
      JLabel resultSearchLabel = new JLabel("ID 또는 국소명 : ");
      resultSearchLabel.setFont(new Font("Times", Font.BOLD, 15));
      detailSearchPanel.add(resultSearchLabel);
      detailSearchTF = new JTextField(10);
      detailSearchTF.setFont(new Font("Times", Font.BOLD, 15));
      detailSearchTF.addKeyListener(new KeyListener() {
         public void keyTyped(KeyEvent e) {
         }

         public void keyReleased(KeyEvent e) {
         }

         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
               Search_ActionListener();
            }
         }
      });
      detailSearchPanel.add(detailSearchTF);
      JButton searchButton = new JButton("검색");
      searchButton.setFont(new Font("Times", Font.BOLD, 13));
      searchButton.setPreferredSize(new Dimension(65, 30));
      searchButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            Search_ActionListener();
         }
      });
      detailSearchPanel.add(searchButton);

      mapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      mapCheckbox = new JCheckBox("지도 보기");
      mapCheckbox.setSelected(true);
      mapCheckbox.setFont(new Font("Times", Font.BOLD, 13));
      mapPanel.add(mapCheckbox);
      detailPanel.add(mapPanel);
      detailPanel.add(detailSearchPanel);
      west.add(locationPanel);
      west.add(detailPanel);

      // 오른쪽 테이블
      JPanel table = new JPanel(new BorderLayout());
      table.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 10), BorderFactory.createTitledBorder("검색 테이블")));
      String col[] = { "번호", "RoadPot_ID", "Ru_ID", "Ru_Name", "거리" };
      DefaultTableModel tableModel = new DefaultTableModel(col, 0);
      resultTable = new JTable(tableModel);
      int colWidth[] = { 100, 100, 100, 100, 100 };
      for (int i = 0; i < colWidth.length; i++)
         resultTable.getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
      resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      resultTable.setFont(new Font("Times", Font.PLAIN, 13));
      resultTable.setRowHeight(22);
      resultTable.addMouseListener(new MouseListener() {
         public void mouseReleased(MouseEvent arg0) {
         }

         public void mousePressed(MouseEvent arg0) {
         }

         public void mouseExited(MouseEvent arg0) {
         }

         public void mouseEntered(MouseEvent arg0) {
         }

         public void mouseClicked(MouseEvent arg0) {
            if (!mapJF.isActive())
               mapJF.loadClickEvent(resultTable.getSelectedRow());
         }
      });
      JScrollPane scollPane = new JScrollPane(resultTable);
      table.add(scollPane, BorderLayout.CENTER);
      Jf.getContentPane().add(top, BorderLayout.NORTH);
      Jf.getContentPane().add(west, BorderLayout.WEST);
      Jf.getContentPane().add(table, BorderLayout.CENTER);
      Jf.setVisible(true);
      Jf.setExtendedState(Jf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
   }

   // 상단 panel의 GUI
   private JPanel GUI_TOP() {
      JPanel top = new JPanel(new GridLayout(0, 3));
      top.setBorder(new EmptyBorder(10, 30, -2, 30));
      JLabel topLeftLabel = new JLabel("KT");
      topLeftLabel.setFont(new Font("Times", Font.BOLD, 20));
      JLabel topMidLabel = new JLabel("Geo WiNG");
      topMidLabel.setFont(new Font("Times", Font.BOLD, 30));
      topMidLabel.setForeground(Color.red);
      JLabel topRightLabel = new JLabel("부산네트워크운용본부", SwingConstants.RIGHT);
      topRightLabel.setFont(new Font("Times", Font.BOLD, 20));
      top.add(topLeftLabel);
      top.add(topMidLabel);
      top.add(topRightLabel);
      return top;
   }

   // west panel의 위치검증 panel GUI
   private JPanel Location_Border() {
      JPanel location = new JPanel();
      GridBagLayout gbl = new GridBagLayout();
      location.setLayout(gbl);
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;

      // 기지국
      dataPanel1 = new JPanel();
      JLabel jl1 = newLabel("기지국", Font.BOLD, 16, Color.LIGHT_GRAY, 110);
      jl1.setForeground(Color.blue);
      JTextField jtx1 = newTextfield(18, Font.PLAIN, 13);
      jtx1.setEditable(true);
      JButton btn1 = newJButton("...");
      btn1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            FileDialog fd = openFile();
            TargetFileName = fd.getDirectory() + fd.getFile();
            jtx1.setText(TargetFileName);
         }
      });
      dataPanel1.add(jl1);
      dataPanel1.add(jtx1);
      dataPanel1.add(btn1);

      // 중계기
      dataPanel2 = new JPanel();
      JLabel jl2 = newLabel("중계기", Font.BOLD, 16, Color.LIGHT_GRAY, 110);
      jl2.setForeground(Color.blue);
      JTextField jtx2 = newTextfield(18, Font.PLAIN, 13);
      jtx2.setEditable(false);
      JButton btn2 = newJButton("...");
      btn2.setEnabled(false);
      btn2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            FileDialog fd = openFile();
            TargetFileName = fd.getDirectory() + fd.getFile();
            jtx2.setText(TargetFileName);
         }
      });
      dataPanel2.add(jl2);
      dataPanel2.add(jtx2);
      dataPanel2.add(btn2);

      // RU_WiNG_Outdoor_RoadPot
      dataPanel3 = new JPanel();
      JCheckBox ruCheckBox = new JCheckBox("RU_WiNG_Outdoor_RoadPot : ");
      ruCheckBox.setSelected(true);
      JTextField jtx3 = newTextfield(18, Font.PLAIN, 13);
      jtx3.setEditable(true);
      JButton btn3 = newJButton("...");
      btn3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            FileDialog fd = openFile();
            TargetFileName = fd.getDirectory() + fd.getFile();
            jtx3.setText(TargetFileName);
         }
      });
      dataPanel3.add(ruCheckBox);
      dataPanel3.add(jtx3);
      dataPanel3.add(btn3);

      // LRIU_WiNG_Outdoor_RoadPot
      dataPanel4 = new JPanel();
      JCheckBox lruiOutCheckBox = new JCheckBox("LRIU_WiNG_Outdoor_RoadPot : ");
      lruiOutCheckBox.setSelected(false);
      JTextField jtx4 = newTextfield(18, Font.PLAIN, 13);
      jtx4.setEditable(false);
      JButton btn4 = newJButton("...");
      btn4.setEnabled(false);
      btn4.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            FileDialog fd = openFile();
            TargetFileName = fd.getDirectory() + fd.getFile();
            jtx4.setText(TargetFileName);
         }
      });
      dataPanel4.add(lruiOutCheckBox);
      dataPanel4.add(jtx4);
      dataPanel4.add(btn4);

      // LRIU_WiNG_Indoor_BuildingPot
      dataPanel5 = new JPanel();
      JCheckBox lruiInCheckBox = new JCheckBox("LRIU_WiNG_Indoor_BuildingPot : ");
      lruiInCheckBox.setSelected(false);
      JTextField jtx5 = newTextfield(18, Font.PLAIN, 13);
      jtx5.setEditable(false);
      JButton btn5 = newJButton("...");
      btn5.setEnabled(false);
      btn5.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            FileDialog fd = openFile();
            TargetFileName = fd.getDirectory() + fd.getFile();
            jtx5.setText(TargetFileName);
         }
      });
      dataPanel5.add(lruiInCheckBox);
      dataPanel5.add(jtx5);
      dataPanel5.add(btn5);

      ruCheckBox.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (ruCheckBox.isSelected()) {
               jtx1.setEditable(true);
               jtx2.setEditable(false);
               jtx3.setEditable(true);
               jtx4.setEditable(false);
               jtx5.setEditable(false);
               lruiOutCheckBox.setSelected(false);
               lruiInCheckBox.setSelected(false);
               btn1.setEnabled(true);
               btn2.setEnabled(false);
               btn3.setEnabled(true);
               btn4.setEnabled(false);
               btn5.setEnabled(false);
               jtx1.setText("");
               jtx2.setText("");
               jtx3.setText("");
               jtx4.setText("");
               jtx5.setText("");
            }
         }
      });
      lruiOutCheckBox.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (lruiOutCheckBox.isSelected()) {
               jtx1.setEditable(true);
               jtx2.setEditable(true);
               jtx3.setEditable(false);
               jtx4.setEditable(true);
               jtx5.setEditable(false);
               ruCheckBox.setSelected(false);
               lruiInCheckBox.setSelected(false);
               btn1.setEnabled(true);
               btn2.setEnabled(true);
               btn3.setEnabled(false);
               btn4.setEnabled(true);
               btn5.setEnabled(false);
               jtx1.setText("");
               jtx2.setText("");
               jtx3.setText("");
               jtx4.setText("");
               jtx5.setText("");
            }
         }
      });
      lruiInCheckBox.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (lruiInCheckBox.isSelected()) {
               jtx1.setEditable(true);
               jtx2.setEditable(true);
               jtx3.setEditable(false);
               jtx4.setEditable(false);
               jtx5.setEditable(true);
               ruCheckBox.setSelected(false);
               lruiOutCheckBox.setSelected(false);
               btn1.setEnabled(true);
               btn2.setEnabled(true);
               btn3.setEnabled(false);
               btn4.setEnabled(false);
               btn5.setEnabled(true);
               jtx1.setText("");
               jtx2.setText("");
               jtx3.setText("");
               jtx4.setText("");
               jtx5.setText("");
            }
         }
      });
      ruCheckBox.addMouseListener(new MouseListener() {
         @Override
         public void mouseReleased(MouseEvent e) {
         }

         @Override
         public void mousePressed(MouseEvent e) {
         }

         @Override
         public void mouseExited(MouseEvent e) {
         }

         @Override
         public void mouseEntered(MouseEvent e) {
         }

         @Override
         public void mouseClicked(MouseEvent e) {
            ruCheckBox.setSelected(true);
         }
      });
      lruiOutCheckBox.addMouseListener(new MouseListener() {
         @Override
         public void mouseReleased(MouseEvent e) {
         }

         @Override
         public void mousePressed(MouseEvent e) {
         }

         @Override
         public void mouseExited(MouseEvent e) {
         }

         @Override
         public void mouseEntered(MouseEvent e) {
         }

         @Override
         public void mouseClicked(MouseEvent e) {
            lruiOutCheckBox.setSelected(true);
         }
      });
      lruiInCheckBox.addMouseListener(new MouseListener() {
         @Override
         public void mouseReleased(MouseEvent e) {
         }

         @Override
         public void mousePressed(MouseEvent e) {
         }

         @Override
         public void mouseExited(MouseEvent e) {
         }

         @Override
         public void mouseEntered(MouseEvent e) {
         }

         @Override
         public void mouseClicked(MouseEvent e) {
            lruiInCheckBox.setSelected(true);
         }
      });

      // 오차범위 거리 설정
      distancePanel = new JPanel();
      distancePanel.setLayout(new GridLayout(2, 1));
      JLabel distanceLabel = newLabel("오차범위", Font.BOLD, 13, Color.gray, 85);
      distanceNumberPanel = new JPanel();
      distanceNumberPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      JRadioButton rbtn1 = new JRadioButton("5km 이상");
      rbtn1.setSelected(true);
      JRadioButton rbtn2 = new JRadioButton("3km 이상");
      JRadioButton rbtn3 = new JRadioButton("1km 이상");
      JRadioButton rbtn4 = new JRadioButton("300m 이상");
      rbtn1.setFont(new Font("Times", Font.BOLD, 13));
      rbtn2.setFont(new Font("Times", Font.BOLD, 13));
      rbtn3.setFont(new Font("Times", Font.BOLD, 13));
      rbtn4.setFont(new Font("Times", Font.BOLD, 13));
      distanceNumberPanel.add(distanceLabel);
      distanceNumberPanel.add(rbtn1);
      distanceNumberPanel.add(rbtn2);
      distanceNumberPanel.add(rbtn3);
      distanceNumberPanel.add(rbtn4);
      distanceCustomPanel = new JPanel();
      distanceCustomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      JRadioButton rbtn5 = new JRadioButton("사용자 정의 : ");
      String blankStr = "                            ";
      JLabel blanklbl = new JLabel(blankStr);
      JTextField distanceTF1 = new JTextField(3);
      JLabel distancelbl = new JLabel("≤  거리＜");
      JTextField distanceTF2 = new JTextField(3);
      distanceTF1.setEditable(false);
      distanceTF2.setEditable(false);
      rbtn1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            distanceTF1.setText("");
            distanceTF2.setText("");
            distanceTF1.setEditable(false);
            distanceTF2.setEditable(false);
            rbtn2.setSelected(false);
            rbtn3.setSelected(false);
            rbtn4.setSelected(false);
            rbtn5.setSelected(false);
         }
      });
      rbtn2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            distanceTF1.setText("");
            distanceTF2.setText("");
            distanceTF1.setEditable(false);
            distanceTF2.setEditable(false);
            rbtn1.setSelected(false);
            rbtn3.setSelected(false);
            rbtn4.setSelected(false);
            rbtn5.setSelected(false);
         }
      });
      rbtn3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            distanceTF1.setText("");
            distanceTF2.setText("");
            distanceTF1.setEditable(false);
            distanceTF2.setEditable(false);
            rbtn1.setSelected(false);
            rbtn2.setSelected(false);
            rbtn4.setSelected(false);
            rbtn5.setSelected(false);
         }
      });
      rbtn4.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            distanceTF1.setText("");
            distanceTF2.setText("");
            distanceTF1.setEditable(false);
            distanceTF2.setEditable(false);
            rbtn1.setSelected(false);
            rbtn2.setSelected(false);
            rbtn3.setSelected(false);
            rbtn5.setSelected(false);
         }
      });
      rbtn5.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (rbtn5.isSelected()) {
               distanceTF1.setEditable(true);
               distanceTF2.setEditable(true);
               rbtn1.setSelected(false);
               rbtn2.setSelected(false);
               rbtn3.setSelected(false);
               rbtn4.setSelected(false);
            } else {
               distanceTF1.setText("");
               distanceTF2.setText("");
               distanceTF1.setEditable(false);
               distanceTF2.setEditable(false);
               rbtn1.setSelected(false);
               rbtn2.setSelected(false);
               rbtn3.setSelected(false);
               rbtn4.setSelected(false);
            }
         }
      });
      distanceCustomPanel.add(blanklbl);
      distanceCustomPanel.add(rbtn5);
      distanceCustomPanel.add(distanceTF1);
      distanceCustomPanel.add(distancelbl);
      distanceCustomPanel.add(distanceTF2);

      distancePanel.add(distanceNumberPanel);
      distancePanel.add(distanceCustomPanel);

      // RSRP
      JPanel rsrpPanel = new JPanel();
      rsrpCheckBox = new JCheckBox(" RSRP : ");
      rsrpCheckBox.setSelected(false);
      JTextField rsrpMinTF = new JTextField(3);
      JLabel rsrpLabel = new JLabel(" ≤  RSRP＜  ");
      JTextField rsrpMaxTF = new JTextField(3);
      rsrpPanel.add(rsrpCheckBox);
      rsrpPanel.add(rsrpMinTF);
      rsrpPanel.add(rsrpLabel);
      rsrpPanel.add(rsrpMaxTF);
      rsrpMinTF.setEditable(false);
      rsrpMaxTF.setEditable(false);
      rsrpCheckBox.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (rsrpCheckBox.isSelected()) {
               rsrpMinTF.setEditable(true);
               rsrpMaxTF.setEditable(true);
            } else {
               rsrpMaxTF.setText("");
               rsrpMinTF.setText("");
               rsrpMinTF.setEditable(false);
               rsrpMaxTF.setEditable(false);
            }
         }
      });

      // Pot 수
      JPanel potPanel = new JPanel();
      potCheckBox = new JCheckBox(" Pot 수 : ");
      potCheckBox.setSelected(false);
      JTextField potMinTF = new JTextField(3);
      JLabel potLabel = new JLabel("  ≤    Pot ＜  ");
      JTextField potMaxTF = new JTextField(3);
      potPanel.add(potCheckBox);
      potPanel.add(potMinTF);
      potPanel.add(potLabel);
      potPanel.add(potMaxTF);
      potMinTF.setEditable(false);
      potMaxTF.setEditable(false);
      potCheckBox.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (potCheckBox.isSelected()) {
               potMinTF.setEditable(true);
               potMaxTF.setEditable(true);
            } else {
               potMinTF.setText("");
               potMaxTF.setText("");
               potMinTF.setEditable(false);
               potMaxTF.setEditable(false);
            }
         }
      });

      // Excel 데이터 저장
      JPanel excelPanel = new JPanel();
      excelPanel.setPreferredSize(new Dimension(excelPanel.getWidth(), 25));
      excelCheckBox = new JCheckBox("Excel 데이터 저장");
      excelCheckBox.setFont(new Font("Times", Font.BOLD, 13));
      excelCheckBox.setSelected(true);
      excelPanel.add(excelCheckBox);
      // 실행 버튼
      JPanel exePanel = new JPanel();
      exePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
      JButton exeBtn = newJButton("실 행");
      exeBtn.setFont(new Font("Times", Font.BOLD, 13));
      exeBtn.setPreferredSize(new Dimension(70, 28));
      exePanel.add(exeBtn);
      exeBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (rsrpMinTF.isEditable() && (rsrpMinTF.getText().isEmpty() || rsrpMaxTF.getText().isEmpty())) {
               JOptionPane.showMessageDialog(null, "빈 칸에 값을 입력하십시오.");
            } else if (potMinTF.isEditable() && (potMinTF.getText().isEmpty() || potMaxTF.getText().isEmpty())) {
               JOptionPane.showMessageDialog(null, "빈 칸에 값을 입력하십시오.");
            } else if (distanceTF1.isEditable()
                  && (distanceTF1.getText().isEmpty() || distanceTF2.getText().isEmpty())) {
               JOptionPane.showMessageDialog(null, "빈 칸에 값을 입력하십시오.");
            } else {

               save1Result = null;
               save2Result = null;
               lgc = null;

               lgc = new Logic(ruCheckBox, lruiOutCheckBox, lruiInCheckBox, rsrpCheckBox, potCheckBox,
                     excelCheckBox, rbtn1, rbtn2, rbtn3, rbtn4, rbtn5, rsrpMinTF, rsrpMaxTF, potMinTF, potMaxTF,
                     distanceTF1, distanceTF2, jtx1, jtx2, jtx3, jtx4, jtx5);

               save1Result = lgc.getSave1();
               save2Result = lgc.getSave2();
               
               JOptionPane.showMessageDialog(null, "작업이 완료되었습니다.");
            }
         }
      });

      location.add(dataPanel1);
      location.add(dataPanel2);
      location.add(dataPanel3);
      location.add(dataPanel4);
      location.add(dataPanel5);
      location.add(distancePanel);
      location.add(rsrpPanel);
      location.add(potPanel);
      location.add(excelPanel);
      location.add(exePanel);

      addGrid(gbl, c, dataPanel1, 0, 0, 1, 1, 0, 0);
      addGrid(gbl, c, dataPanel2, 0, 1, 1, 1, 0, 0);
      addGrid(gbl, c, dataPanel3, 0, 2, 1, 1, 0, 0);
      addGrid(gbl, c, dataPanel4, 0, 3, 1, 1, 0, 0);
      addGrid(gbl, c, dataPanel5, 0, 4, 1, 1, 0, 0);
      addGrid(gbl, c, distancePanel, 0, 5, 1, 1, 0, 0);
      addGrid(gbl, c, rsrpPanel, 0, 6, 1, 1, 0, 0);
      addGrid(gbl, c, potPanel, 0, 7, 1, 1, 0, 0);
      addGrid(gbl, c, excelPanel, 0, 8, 1, 2, 0, 0);
      addGrid(gbl, c, exePanel, 0, 10, 1, 1, 0, 0);

      return location;
   }

   // label 속성까지 설정하여 생성하는 함수
   private JLabel newLabel(String str, int weight, int size, Color color, int Dx) {
      JLabel jl = new JLabel("  " + str + "  ", JLabel.CENTER);
      jl.setFont(new Font("Times", weight, size));
      jl.setBorder(new BevelBorder(BevelBorder.LOWERED));
      jl.setBackground(color);
      jl.setPreferredSize(new Dimension(Dx, 30));
      return jl;
   }

   // textfield 속성까지 설정하여 생성하는 함수
   private JTextField newTextfield(int size, int weight, int fontSize) {
      JTextField jtf = new JTextField(size);
      jtf.setEditable(false);
      jtf.setBackground(Color.WHITE);
      jtf.setFont(new Font("Times", weight, fontSize));
      return jtf;
   }

   // button 속성까지 설정하여 생성하는 함수
   private JButton newJButton(String str) {
      JButton jt = new JButton(str);
      jt.setFont(new Font("Times", Font.BOLD, 13));
      jt.setBorder(new EtchedBorder());
      return jt;
   }

   // 파일을 검색하는 프레임을 띄우는 함수
   private FileDialog openFile() {
      Frame f = new Frame();
      FileDialog fileD = new FileDialog(f, "Open", FileDialog.LOAD);
      fileD.setFile("*.xls");
      fileD.setVisible(true);
      return fileD;
   }

   // 그리드백 설정
   private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c, int gridx, int gridy, int gridwidth,
         int gridheight, int weightx, int weighty) {
      gbc.gridx = gridx;
      gbc.gridy = gridy;
      gbc.gridwidth = gridwidth;
      gbc.gridheight = gridheight;
      gbc.weightx = weightx;
      gbc.weighty = weighty;
      gbl.setConstraints(c, gbc);
   }

   private void Search_ActionListener() {
      if (detailSearchTF.getText().isEmpty()) {
         JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.");
      } else {
         DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
         ArrayList<excelData> saveTmpResult;
         String str = detailSearchTF.getText().trim();
         centerData = select(str, save1Result);
         saveTmpResult = resultSelect(str, save2Result);
         model.setNumRows(0);
         Export_JTable(model, saveTmpResult);
         DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 테이블셀
         // 렌더러
         // 객체를
         // 생성.
         dtcr.setHorizontalAlignment(SwingConstants.CENTER);
         for (int i = 0; i < resultTable.getColumnCount(); i++)
            resultTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);
         if (mapCheckbox.isSelected()) {
            Visible_Map(centerData, saveTmpResult);
         }
      }
   }

   private void Visible_Map(excelData center, ArrayList<excelData> result) {
      mapJF = new map();
      mapJF.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      mapJF.setSize(screenSize.width / 3, (screenSize.height * 3 / 4) - 95);
      mapJF.setVisible(true);
      mapJF.loadURL(screenSize, center, result);
   }

   private excelData select(String centerData, ArrayList<excelData> save1) {
      int n = 0;
      while (n < save1.size()) {
         if ((save1.get(n).getRuID().equals(centerData)) || (save1.get(n).getMachName().equals(centerData)))
            return save1.get(n);
         n++;
      }
      return null;
   }

   private ArrayList<excelData> resultSelect(String id, ArrayList<excelData> save2) {
      int n = 0;
      ArrayList<excelData> res = new ArrayList<excelData>();
      while (n < save2.size()) {
         if ((save2.get(n).getRuID().equals(id)) || (save2.get(n).getRuName().equals(id))) {
            res.add(save2.get(n));
         }

         n++;
      }
      return res;
   }

   private void Export_JTable(DefaultTableModel model, ArrayList<excelData> exeResult) {
      String[] dd = new String[5];
      for (int i = 0; i < exeResult.size(); i++) {
         excelData res = exeResult.get(i);
         dd[0] = String.valueOf(i + 1);
         dd[1] = res.getRoadpotID();
         dd[2] = res.getRuID();
         dd[3] = res.getRuName();
         dd[4] = Double.toString(res.getDistance());
         model.addRow(dd);
      }
   }

   public static void main(String[] args) {
      // new Loading();
      new UI();
   }

}