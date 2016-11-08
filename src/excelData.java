import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JOptionPane;

import com.gis.pro.GeoSystem;
import com.gis.pro.Projection;
import com.vividsolutions.jts.geom.Coordinate;

// 엑셀에서 가져올 데이터 분류
public class excelData {
   Coordinate coord = new Coordinate();
   Coordinate convC;
   private String city; // 시도 || 시/도
   private String borough; // 군구 || 시/군/구
   private String town; // 법정동 || 행정동 || 읍/면/동(리)
   private String houseAddress; // 번지
   private String roadpotID; // RoadPot ID
   private String buildingID; // 건물 ID
   private String buildingName; // 건물명
   private String UTMKX; // RoadPot X (UTMK) || 건물중심 UTMKX
   private String UTMKY; // RoadPot Y (UTMK) || 건물중점 UTMKY
   private String ruID; // LTE RU ID
   private String ruName; // LTE RU NAME
   private String rsrpAvg; // LTE RSRP 평균 (RPOT) || LTE RSRP 평균 (건물)
   private String sinrAvg; // LTE SINR 평균 (RPOT) || LTE SINR 평균 (건물)
   private String machID; // 장비ID || 중계기ID
   private String machName; // 장비명 || 중계기명
   private String nickName; // Nickname
   private String repeaterForm; // 중계기형상
   private String motherID; // LTE(1.8G)모국ID
   private String motherName; // LTE(1.8G)모국명
   private String machState; // 장비상태
   private String ruService; // RU서비스구분
   private String latitude; // 위도
   private String longitude; // 경도
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

   // 노가다
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
      // content : 항목명
      // inputData : 데이터
      switch (content) {
      case "시도":
      case "시/도":
         this.city = inputData;
         break;
      case "군구":
      case "시/군/구":
         this.borough = inputData;
         break;
      case "법정동":
      case "행정동":
      case "읍/면/동(리)":
         this.town = inputData;
         break;
      case "번지":
         this.houseAddress = inputData;
         break;
      case "RoadPot ID":
         this.roadpotID = inputData;
         break;
      case "건물 ID":
         this.buildingID = inputData;
         break;
      case "건물명":
         this.buildingName = inputData;
         break;
      case "RoadPot X (UTMK)":
      case "건물중심 UTMKX":
         this.UTMKX = inputData;
         this.coord.x = Double.parseDouble(inputData);
         break;
      case "RoadPot Y (UTMK)":
      case "건물중심 UTMKY":
         this.UTMKY = inputData;
         this.coord.y = Double.parseDouble(inputData);
         break;
      case "장비ID":
      case "중계기ID":
      case "LTE RU ID":
         this.ruID = inputData;
         break;
      case "LTE RU NAME":
         this.ruName = inputData;
         break;
      case "LTE RSRP 평균 (RPOT)":
      case "LTE RSRP 평균 (건물)":
         this.rsrpAvg = inputData;
         break;
      case "LTE SINR 평균 (RPOT)":
      case "LTE SINR 평균 (건물)":
         this.sinrAvg = inputData;
         break;
      case "장비명":
      case "중계기명":
         this.machName = inputData;
         break;
      case "Nickname":
         this.nickName = inputData;
         break;
      case "중계기형상":
         this.repeaterForm = inputData;
         break;
      case "LTE(1.8G)모국ID":
         this.motherID = inputData;
         break;
      case "LTE(1.8G)모국명":
         this.motherName = inputData;
         break;
      case "장비상태":
         this.machState = inputData;
         break;
      case "RU서비스구분":
         this.ruService = inputData;
         break;
      case "위도":
         this.latitude = inputData;
         break;
      case "경도":
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
      // content : 항목명
      switch (content) {
      case "시도":
      case "시/도":
         return this.city;
      case "군구":
      case "시/군/구":
         return this.borough;
      case "법정동":
      case "행정동":
      case "읍/면/동(리)":
         return this.town;
      case "번지":
         return this.houseAddress;
      case "RoadPot ID":
         return this.roadpotID;
      case "건물 ID":
         return this.buildingID;
      case "건물명":
         return this.buildingName;
      case "RoadPot X (UTMK)":
      case "건물중심 UTMKX":
         return this.UTMKX;
      case "RoadPot Y (UTMK)":
      case "건물중심 UTMKY":
         return this.UTMKY;
      case "장비ID":
      case "중계기ID":
      case "LTE RU ID":
         return this.ruID;
      case "LTE RU NAME":
         return this.ruName;
      case "LTE RSRP 평균 (RPOT)":
      case "LTE RSRP 평균 (건물)":
      case "RSRP 평균":
         return this.rsrpAvg;
      case "LTE SINR 평균 (RPOT)":
      case "LTE SINR 평균 (건물)":
         return this.sinrAvg;
      case "장비명":
      case "중계기명":
         return this.machName;
      case "Nickname":
         return this.nickName;
      case "중계기형상":
         return this.repeaterForm;
      case "LTE(1.8G)모국ID":
         return this.motherID;
      case "LTE(1.8G)모국명":
         return this.motherName;
      case "장비상태":
         return this.machState;
      case "RU서비스구분":
         return this.ruService;
      case "위도":
         return this.latitude;
      case "경도":
         return this.longitude;
      case "Pot 수":
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
         JOptionPane.showMessageDialog(null, "중계기ID " + this.ruID
               + "의 위경도가 정상적인지 확인해주세요.", "Error",
               JOptionPane.WARNING_MESSAGE);
         System.exit(1);
      }
      return Double.parseDouble(df.format(pos));
   }

   // 디버깅용
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