import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Loading extends Thread
{
   JLabel idLabel;
   Dimension screenSize;
   JFrame frm;
   JPanel myPanel;
   int cnt;
   boolean flag;
   public Loading(){
//      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      frm = new JFrame("Loading...");
      frm.setBounds(500,500, 320,100);
//      frm.setSize(320, 100);
//      frm.setLocation((screenSize.width - 300) / 2, (screenSize.height - 300) / 2);
      cnt = 0;
      idLabel = new JLabel("데이터 분석중입니다");
      this.myPanel = new JPanel();
      idLabel.setVisible(true);
      this.myPanel.add(idLabel);
      this.myPanel.setVisible(true);
      frm.add(myPanel);
      flag = true;
      
   }
   
   public JFrame getJframe(){
	   return this.frm;
   }

   @Override
   public void run(){
      System.out.println("스레드 : "+flag);
      while(flag){
         cnt++;
         String msg = "데이터 분석중입니다";
         System.out.println(msg);
         if(cnt % 4 != 0){
            for(int i = 0; i<cnt; i++)
               msg = msg.concat(".");
         }else{
            cnt = 0;
         }
         idLabel.setText(msg);
         myPanel.repaint();
         frm.repaint();
            try {
               sleep(700);
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
      }
   }
   public void showNotice(){
	   frm.setVisible(true);
   }
   public void endNotice(){
      flag = false;
      frm.setVisible(false);
   }
   
}