import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JOptionPane;

import com.gis.pro.GeoSystem;
import com.gis.pro.Projection;
import com.vividsolutions.jts.geom.Coordinate;

// �������� ������ ������ �з�
public class excelData {
   Coordinate coord = new Coordinate();
   Coordinate convC;
   private String city; // �õ� || ��/��
   private String borough; // ���� || ��/��/��
   private String town; // ������ || ������ || ��/��/��(��)
   private String houseAddress; // ����
   private String roadpotID; // RoadPot ID
   private String buildingID; // �ǹ� ID
   private String buildingName; // �ǹ���
   private String UTMKX; // RoadPot X (UTMK) || �ǹ��߽� UTMKX
   private String UTMKY; // RoadPot Y (UTMK) || �ǹ����� UTMKY
   private String ruID; // LTE RU ID
   private String ruName; // LTE RU NAME
   private String rsrpAvg; // LTE RSRP ��� (RPOT) || LTE RSRP ��� (�ǹ�)
   private String sinrAvg; // LTE SINR ��� (RPOT) || LTE SINR ��� (�ǹ�)
   private String machID; // ���ID || �߰��ID
   private String machName; // ���� || �߰���
   private String nickName; // Nickname
   private String repeaterForm; // �߰������
   private String motherID; // LTE(1.8G)��ID
   private String motherName; // LTE(1.8G)�𱹸�
   private String machState; // ������
   private String ruService; // RU���񽺱���
   private String latitude; // ����
   private String longitude; // �浵
   private double UTMKLA;
   private double UTMKLO;
   private double potCount;
   private double distance;

   public double getDistance() {
      return distance;
   }

   public void setDistance(double distance) {
      this.distance = distance;
   }

   // �밡��
   excelData() {
      this.borough = null;
      this.buildingID = null;
      this.buildingName = null;
      this.city = null;
      this.houseAddress = null;
      this.latitude = null;
      this.longitude = null;
      this.machID = null;
      this.machName = null;
      this.machState = null;
      this.motherID = null;
      this.motherName = null;
      this.nickName = null;
      this.repeaterForm = null;
      this.roadpotID = null;
      this.rsrpAvg = null;
      this.ruID = null;
      this.ruName = null;
      this.ruService = null;
      this.sinrAvg = null;
      this.town = null;
      this.UTMKX = null;
      this.UTMKY = null;
   }

   // setter
   public void setData(String content, String inputData) {
      // content : �׸��
      // inputData : ������
      switch (content) {
      case "�õ�":
      case "��/��":
         this.city = inputData;
         break;
      case "����":
      case "��/��/��":
         this.borough = inputData;
         break;
      case "������":
      case "������":
      case "��/��/��(��)":
         this.town = inputData;
         break;
      case "����":
         this.houseAddress = inputData;
         break;
      case "RoadPot ID":
         this.roadpotID = inputData;
         break;
      case "�ǹ� ID":
         this.buildingID = inputData;
         break;
      case "�ǹ���":
         this.buildingName = inputData;
         break;
      case "RoadPot X (UTMK)":
      case "�ǹ��߽� UTMKX":
         this.UTMKX = inputData;
         this.coord.x = Double.parseDouble(inputData);
         break;
      case "RoadPot Y (UTMK)":
      case "�ǹ��߽� UTMKY":
         this.UTMKY = inputData;
         this.coord.y = Double.parseDouble(inputData);
         break;
      case "���ID":
      case "�߰��ID":
      case "LTE RU ID":
         this.ruID = inputData;
         break;
      case "LTE RU NAME":
         this.ruName = inputData;
         break;
      case "LTE RSRP ��� (RPOT)":
      case "LTE RSRP ��� (�ǹ�)":
         this.rsrpAvg = inputData;
         break;
      case "LTE SINR ��� (RPOT)":
      case "LTE SINR ��� (�ǹ�)":
         this.sinrAvg = inputData;
         break;
      case "����":
      case "�߰���":
         this.machName = inputData;
         break;
      case "Nickname":
         this.nickName = inputData;
         break;
      case "�߰������":
         this.repeaterForm = inputData;
         break;
      case "LTE(1.8G)��ID":
         this.motherID = inputData;
         break;
      case "LTE(1.8G)�𱹸�":
         this.motherName = inputData;
         break;
      case "������":
         this.machState = inputData;
         break;
      case "RU���񽺱���":
         this.ruService = inputData;
         break;
      case "����":
         this.latitude = inputData;
         break;
      case "�浵":
         this.longitude = inputData;
         break;
      }
   }

   public void setPotCount(double potCount) {
      this.potCount = potCount;
   }

   public void setRsrpAvg(String rsrpAvg) {
      this.rsrpAvg = rsrpAvg;
   }

   // getters
   public String getData(String content) {
      // content : �׸��
      switch (content) {
      case "�õ�":
      case "��/��":
         return this.city;
      case "����":
      case "��/��/��":
         return this.borough;
      case "������":
      case "������":
      case "��/��/��(��)":
         return this.town;
      case "����":
         return this.houseAddress;
      case "RoadPot ID":
         return this.roadpotID;
      case "�ǹ� ID":
         return this.buildingID;
      case "�ǹ���":
         return this.buildingName;
      case "RoadPot X (UTMK)":
      case "�ǹ��߽� UTMKX":
         return this.UTMKX;
      case "RoadPot Y (UTMK)":
      case "�ǹ��߽� UTMKY":
         return this.UTMKY;
      case "���ID":
      case "�߰��ID":
      case "LTE RU ID":
         return this.ruID;
      case "LTE RU NAME":
         return this.ruName;
      case "LTE RSRP ��� (RPOT)":
      case "LTE RSRP ��� (�ǹ�)":
      case "RSRP ���":
         return this.rsrpAvg;
      case "LTE SINR ��� (RPOT)":
      case "LTE SINR ��� (�ǹ�)":
         return this.sinrAvg;
      case "����":
      case "�߰���":
         return this.machName;
      case "Nickname":
         return this.nickName;
      case "�߰������":
         return this.repeaterForm;
      case "LTE(1.8G)��ID":
         return this.motherID;
      case "LTE(1.8G)�𱹸�":
         return this.motherName;
      case "������":
         return this.machState;
      case "RU���񽺱���":
         return this.ruService;
      case "����":
         return this.latitude;
      case "�浵":
         return this.longitude;
      case "Pot ��":
         return Double.toString(this.potCount);
      default:
         return null;
      }
   }

   public String getCity() {
      return city;
   }

   public String getBorough() {
      return borough;
   }

   public String getTown() {
      return town;
   }

   public String getHouseAddress() {
      return houseAddress;
   }

   public String getRoadpotID() {
      return roadpotID;
   }

   public String getBuildingID() {
      return buildingID;
   }

   public String getBuildingName() {
      return buildingName;
   }

   public String getUTMKX() {
      return UTMKX;
   }

   public String getUTMKY() {
      return UTMKY;
   }

   public String getRuID() {
      return ruID;
   }

   public String getRuName() {
      return ruName;
   }

   public String getRsrpAvg() {
      return rsrpAvg;
   }

   public String getSinrAvg() {
      return sinrAvg;
   }

   public String getMachID() {
      return machID;
   }

   public String getMachName() {
      return machName;
   }

   public String getNickName() {
      return nickName;
   }

   public String getRepeaterForm() {
      return repeaterForm;
   }

   public String getMotherID() {
      return motherID;
   }

   public String getMotherName() {
      return motherName;
   }

   public String getMachState() {
      return machState;
   }

   public String getRuService() {
      return ruService;
   }

   public String getLatitude() {
      return latitude;
   }

   public String getLongitude() {
      return longitude;
   }

   public double getUTMKLA() {
      Projection prj = new Projection();
      prj.SetSrcType(com.gis.pro.GeoEllips.kBessel1984,
            com.gis.pro.GeoSystem.kUtmKR);
      prj.SetDstType(com.gis.pro.GeoEllips.kBessel1984,
            com.gis.pro.GeoSystem.kGeographic);
      convC = prj.Conv(coord);

      return convC.y;
   }

   public double getUTMKLO() {
      Projection prj = new Projection();
      prj.SetSrcType(com.gis.pro.GeoEllips.kBessel1984,
            com.gis.pro.GeoSystem.kUtmKR);
      prj.SetDstType(com.gis.pro.GeoEllips.kBessel1984,
            com.gis.pro.GeoSystem.kGeographic);
      convC = prj.Conv(coord);
      return convC.x;
   }

   public double getPotCount() {
      return potCount;
   }

   double Translate_GPS(String S) {
      if (S.equals("0") || S.length() < 1 || S == null || S.equals("false"))
         return 0;
      NumberFormat df = new DecimalFormat("#.000000");
      String T[] = S.split("-");
      double pos = 0;
      try {
         pos = Double.parseDouble(T[0])
               + (Double.parseDouble(T[1]) / (double) 60)
               + (Double.parseDouble(T[2]) / (double) 3600);
      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "�߰��ID " + this.ruID
               + "�� ���浵�� ���������� Ȯ�����ּ���.", "Error",
               JOptionPane.WARNING_MESSAGE);
         System.exit(1);
      }
      return Double.parseDouble(df.format(pos));
   }

   // ������
   public void printData() {
      System.out.print(this.borough + " ");
      System.out.print(this.buildingID + " ");
      System.out.print(this.buildingName + " ");
      System.out.print(this.city + " ");
      System.out.print(this.houseAddress + " ");
      System.out.print(this.latitude + " ");
      System.out.print(this.longitude + " ");
      System.out.print(this.machID + " ");
      System.out.print(this.machName + " ");
      System.out.print(this.machState + " ");
      System.out.print(this.motherID + " ");
      System.out.print(this.motherName + " ");
      System.out.print(this.nickName + " ");
      System.out.print(this.repeaterForm + " ");
      System.out.print(this.roadpotID + " ");
      System.out.print(this.rsrpAvg + " ");
      System.out.print(this.ruID + " ");
      System.out.print(this.ruName + " ");
      System.out.print(this.ruService + " ");
      System.out.print(this.sinrAvg + " ");
      System.out.print(this.town + " ");
      System.out.print(this.UTMKX + " ");
      System.out.println(this.UTMKY);
   }
}