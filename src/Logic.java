import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Logic {
	private JCheckBox ruCheckBox, lruiOutCheckBox, lruiInCheckBox;
	private JCheckBox rsrpCheckBox, potCheckBox, excelCheckBox;
	private JRadioButton rbtn1, rbtn2, rbtn3, rbtn4, rbtn5;
	private JTextField rsrpMinTF, rsrpMaxTF, potMinTF, potMaxTF, distanceTF1,
			distanceTF2;
	private JTextField jtx1, jtx2, jtx3, jtx4, jtx5;

	private ArrayList<excelData> save1 = new ArrayList<excelData>();
	private ArrayList<excelData> save2 = new ArrayList<excelData>();
	private ArrayList<excelData> save3 = new ArrayList<excelData>();

	private BinarySearchTree file1 = new BinarySearchTree();
	private BinarySearchTree file2 = new BinarySearchTree();
	private BinarySearchTree file3 = new BinarySearchTree();

	private Dimension screenSize;
	private JProgressBar logicBar;
	ExecutorService service = Executors.newSingleThreadExecutor();

	public Logic(JCheckBox ruCheckBox, JCheckBox lruiOutCheckBox,
			JCheckBox lruiInCheckBox, JCheckBox rsrpCheckBox,
			JCheckBox potCheckBox, JCheckBox excelCheckBox, JRadioButton rbtn1,
			JRadioButton rbtn2, JRadioButton rbtn3, JRadioButton rbtn4,
			JRadioButton rbtn5, JTextField rsrpMinTF, JTextField rsrpMaxTF,
			JTextField potMinTF, JTextField potMaxTF, JTextField distanceTF1,
			JTextField distanceTF2, JTextField jtx1, JTextField jtx2,
			JTextField jtx3, JTextField jtx4, JTextField jtx5) {

		this.ruCheckBox = ruCheckBox;
		this.lruiOutCheckBox = lruiOutCheckBox;
		this.lruiInCheckBox = lruiInCheckBox;
		this.rsrpCheckBox = rsrpCheckBox;
		this.potCheckBox = potCheckBox;
		this.excelCheckBox = excelCheckBox;
		this.rbtn1 = rbtn1;
		this.rbtn2 = rbtn2;
		this.rbtn3 = rbtn3;
		this.rbtn4 = rbtn4;
		this.rbtn5 = rbtn5;
		this.rsrpMinTF = rsrpMinTF;
		this.rsrpMaxTF = rsrpMaxTF;
		this.potMinTF = potMinTF;
		this.potMaxTF = potMaxTF;
		this.distanceTF1 = distanceTF1;
		this.distanceTF2 = distanceTF2;
		this.jtx1 = jtx1;
		this.jtx2 = jtx2;
		this.jtx3 = jtx3;
		this.jtx4 = jtx4;
		this.jtx5 = jtx5;

		init(file1, save1, file2, save2, file3, save3);

		Analysis(save1, save2, save3);

		modify(save1, save2, save3);

		if (excelCheckBox.isSelected()) {
			ExcelParser.writeExcelFile(save1);
		}
	}

	public ArrayList<excelData> getSave1() {
		return this.save1;
	}

	public ArrayList<excelData> getSave2() {
		return this.save2;
	}

	public ArrayList<excelData> getSave3() {
		return this.save3;
	}

	public void init(BinarySearchTree file1, ArrayList<excelData> save1,
			BinarySearchTree file2, ArrayList<excelData> save2,
			BinarySearchTree file3, ArrayList<excelData> save3) {

		if (ruCheckBox.isSelected()) {
			ExcelParser ex = new ExcelParser(jtx1.getText(), file1);
			inorder(file1.GetRoot(), save1);

			ExcelParser ex2 = new ExcelParser(jtx3.getText(), file2);
			inorder(file2.GetRoot(), save2);

		} else if (lruiOutCheckBox.isSelected()) {
			ExcelParser ex = new ExcelParser(jtx1.getText(), file1);
			inorder(file1.GetRoot(), save1);

			ExcelParser ex2 = new ExcelParser(jtx2.getText(), file2);
			inorder(file2.GetRoot(), save2);

			ExcelParser ex3 = new ExcelParser(jtx4.getText(), file3);
			inorder(file3.GetRoot(), save3);
		} else if (lruiInCheckBox.isSelected()) {
			ExcelParser ex = new ExcelParser(jtx1.getText(), file1);
			inorder(file1.GetRoot(), save1);

			ExcelParser ex2 = new ExcelParser(jtx2.getText(), file2);
			inorder(file2.GetRoot(), save2);

			ExcelParser ex3 = new ExcelParser(jtx5.getText(), file3);
			inorder(file3.GetRoot(), save3);
		}
	}

	public void inorder(TreeNode root, ArrayList<excelData> tmp) {
		if (root != null) {
			inorder(root.left, tmp);
			tmp.add(root.data);
			inorder(root.right, tmp);
		}
	}

	public double Distance_Calc(double T_lati, double T_long, double R_lati,
			double R_long) {
		double d2r = Math.PI / 180;
		double dLon = (R_long - T_long) * d2r;
		double dLat = (R_lati - T_lati) * d2r;
		double a = Math.pow(Math.sin(dLat / 2.0), 2) + Math.cos(T_lati * d2r)
				* Math.cos(R_lati * d2r) * Math.pow(Math.sin(dLon / 2.0), 2);
		double c = Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 2;
		double distance = c * 6378;
		return distance * 1000;
	}

	public void Analysis(ArrayList<excelData> tmp1, ArrayList<excelData> tmp2,
			ArrayList<excelData> tmp3) {

		if (ruCheckBox.isSelected()) {
			int one, two;
			one = two = 0;
			String str1 = tmp1.get(one).getRuID();
			String str2 = tmp2.get(two).getRuID();
			while (one < tmp1.size() - 1 && two < tmp2.size() - 1) {

				if (str1.equals(str2)) {
					if (str1.equals("")) {
						tmp1.remove(one);
						tmp2.remove(two);
						str1 = tmp1.get(one).getRuID();
						str2 = tmp2.get(two).getRuID();
					} else {
						two++;
						str2 = tmp2.get(two).getRuID();
					}
				} else {
					if (str1.compareTo(str2) < 0) {
						if (str1.equals("")) {
							tmp1.remove(one);
							str1 = tmp1.get(one).getRuID();
						} else {
							one++;
							str1 = tmp1.get(one).getRuID();
						}
					} else {
						tmp2.remove(two);
						str2 = tmp2.get(two).getRuID();
					}
				}
			}

		} else if (lruiOutCheckBox.isSelected()) {

		} else if (lruiInCheckBox.isSelected()) {

		}
	}

	public void modify(ArrayList<excelData> tmp1, ArrayList<excelData> tmp2,
			ArrayList<excelData> tmp3) {
		double distance = 0.0;
		double distance2 = 0.0;
		double cnt = 0.0;
		double sum = 0.0;
		double rsrp1 = 0.0;
		double rsrp2 = 0.0;
		double pot1 = 0.0;
		double pot2 = 0.0;
		if (rbtn1.isSelected()) {
			distance = 5000.0;
		} else if (rbtn2.isSelected()) {
			distance = 3000.0;
		} else if (rbtn3.isSelected()) {
			distance = 1000.0;
		} else if (rbtn4.isSelected()) {
			distance = 300.0;
		} else if (rbtn5.isSelected()) {
			distance = Double.parseDouble(distanceTF1.getText());
			distance2 = Double.parseDouble(distanceTF2.getText());
		}

		if (rsrpCheckBox.isSelected()) {
			rsrp1 = Double.parseDouble(rsrpMinTF.getText());
			rsrp2 = Double.parseDouble(rsrpMaxTF.getText());
		}
		if (potCheckBox.isSelected()) {
			pot1 = Double.parseDouble(potMinTF.getText());
			pot2 = Double.parseDouble(potMaxTF.getText());
		}

		if (ruCheckBox.isSelected()) {

			int one, two;
			one = two = 0;
			String nowID = tmp1.get(one).getRuID();
			while (one < tmp1.size()&& two < tmp2.size()) {

				if (nowID.compareTo(tmp2.get(two).getRuID()) < 0) {
					if (potCheckBox.isSelected()) {
						if (cnt >= pot2 || cnt < pot1) {
							tmp1.remove(one);
							int tmpindex = two - (int) cnt;
							for (int i = 0; i < (int) cnt; i++)
								tmp2.remove(tmpindex);
							cnt = 0.0;
							sum = 0.0;
							nowID = tmp1.get(one).getRuID();
							continue;
						} else {
							tmp1.get(one).setPotCount(cnt);
							tmp1.get(one).setRsrpAvg(Double.toString(sum / cnt));
							cnt = 0.0;
							sum = 0.0;
							one++;
							nowID = tmp1.get(one).getRuID();
							continue;
						}
					}
					tmp1.get(one).setPotCount(cnt);
					tmp1.get(one).setRsrpAvg(Double.toString(sum / cnt));
					cnt = 0.0;
					sum = 0.0;
					one++;
					nowID = tmp1.get(one).getRuID();
					continue;
				}
				double T_lati, T_long, R_lati, R_long;
				T_lati = tmp1.get(one).Translate_GPS(
						tmp1.get(one).getLatitude());
				T_long = tmp1.get(one).Translate_GPS(
						tmp1.get(one).getLongitude());
				R_lati = tmp2.get(two).getUTMKLA();
				R_long = tmp2.get(two).getUTMKLO();
				double Dist = Distance_Calc(T_lati, T_long, R_lati, R_long);
				// °Å¸®
				if (rbtn5.isSelected()) {
					if (Dist < distance || Dist >= distance2) {
						tmp2.remove(two);
						continue;
					}
				} else if(rbtn1.isSelected()||rbtn2.isSelected()||rbtn3.isSelected()||rbtn4.isSelected()){
					if (Dist < distance) {
						tmp2.remove(two);
						continue;
					}
				}
				tmp2.get(two).setDistance(Dist);
				double nowrsrp = Double.parseDouble(tmp2.get(two).getRsrpAvg());
				if (rsrpCheckBox.isSelected()) {

					if (nowrsrp >= rsrp2 || nowrsrp < rsrp1) {
						tmp2.remove(two);
						continue;
					}
				}
				sum += nowrsrp;
				cnt++;
				two++;
			}
			if (potCheckBox.isSelected()||rsrpCheckBox.isSelected()) {
				while (one < tmp1.size()) {
					tmp1.remove(one);
				}
			}
		}

	}

	/*public excelData select(String some, ArrayList<excelData> tmp1) {
		int n = 0;
		while (n < tmp1.size()) {
			if (tmp1.get(n).getRuID().equals(some)/*
												 * ||tmp1.get(n).getRuName().equals
												 * (some)
												 *//*)
				return tmp1.get(n);
			n++;
		}
		return null;
	}*/
}
