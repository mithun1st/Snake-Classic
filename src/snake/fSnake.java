/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.*;

/**
 *
 * @author Mahadi Hassan
 */
public final class fSnake extends javax.swing.JFrame {

    /**
     * Creates new form fSnake
     */
    
    
    //----------------- xxxxxxxxxxxxxxxxxxxx ------------------ <>
    
    Scanner sc;
    int score,s=0;
    int speed=500;
    int fx=40,fy=0;
    String level="";
    int i=0,point=0,hp=0;
    ArrayList<JPanel> tail;
    int[] xx = new int[1000];
    int[] yy = new int[1000];
    int x=0,y=0,x1=0,x2=0,y1=0,y2=0;
    boolean r=false,l=false,u=false,d=false,start=true,hs;
   
    
    public void rightMove(){
        this.x+=20;
        if(x==600){
            x=0;
        }
        snake.setLocation(this.x, y);
        i=1;    
    }
    
    
    public void leftMove(){
        this.x-=20;
        if(x==-20){
            x=580;
        }
        snake.setLocation(this.x, y);
        //System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
        i=2;
    }
    
    
    public void upMove(){
        this.y-=20;
        if(y==-20){
            y=380;
        }
        snake.setLocation(this.x, y);
        //System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
        i=3;  
    }
    
    
    public void downMove(){
        this.y+=20;
        if(y==400){
            y=0;
        }
        snake.setLocation(this.x, y);
        //System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
        i=4;        
    }
    
    
    public void foodMove(){
        if( ( (fx==x && fy==y) || (x-fx==10 || fx-x==10) && (y-fy==10 || fy-y==10)) || ((x==fx && y-fy==10) || (x==fx && fy-y==10)) || ((y==fy && x-fx==10) || (y==fy && fx-x==10))){
            System.err.println("\t\tx= "+x+"\ty= "+y+"\n\t\tfx= "+fx+"\tfy= "+fy+"\n\tPoint= "+point+"\f");
            point++;
            s+=score;
            tailIncrement();
            fx= new sCls().random(10,570);
            fy= new sCls().random(10,370);
            food.setLocation(fx, fy);
        }
    }
    
    
    public void tailIncrement(){
        JPanel js =new JPanel();
        js.setBackground(Color.blue);
        js.setSize(17, 17);
        tail.add(js);
    }
    

    public void tail(){

        s1.setLocation(x1, y1);
        s2.setLocation(x2, y2);
        try {
            if(point>1){
                System.out.println("\t\t\t\t\t\tPointx= "+(point-1));
                
                for(int jj=0; jj<point-1; jj++){
                    jPanel1.add(tail.get(jj)).setLocation(xx[jj], yy[jj]);
                }
                
                for(int ii=999;ii>0;ii--){
                    xx[ii]=xx[ii-1];
                    yy[ii]=yy[ii-1];
                }
                
            }        
        } catch (Exception e) {
        }
        
        xx[0]=x2;
        yy[0]=y2;

        x2=x1;
        y2=y1;

        x1=x;
        y1=y;
        
    }
    
    
    public void gameOver(){ // Tail Bite
        for(int k=0;k<point;k++){
            if(xx[k]==x && yy[k]==y){
                point--;
                s-=score;
                r=false;
                l=false;
                u=false;
                d=false;
                start=false;
                snake.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Game Over !!!", "Tail Bite (", JOptionPane.WARNING_MESSAGE);
                if(s>hp){
                    hp=s;
                    hs=true;
                }
                store();
                this .dispose();
                new score().setVisible(true);
                if(hs){
                    JOptionPane.showMessageDialog(null,"You break the High Scores !","Congratulation )",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
        //1600-1000-500-100-40-

    public void level(){
        if(level.matches("Beginner")){
            score=2;
            speed=1600;
        }
        if(level.matches("Easy")){
            score=3;
            speed=1000;
        }
        if(level.matches("Normal")){
            score=5;
            speed=500;
        }
        if(level.matches("Hard")){
            score=7;
            speed=100;
        }
        if(level.matches("Extreme")){
            score=10;
            speed=40;
        }
    }
    
    public void store(){
        try {
            FileOutputStream file = new FileOutputStream("Score DataBase.txt");
            ObjectOutputStream o = new ObjectOutputStream(file);
            o.writeObject(s);
            o.writeObject(hp);
            o.writeObject(level);
            o.close();
        } catch (Exception e) {
        }
    }
    
    public void restore(){
        try {
            FileInputStream file = new FileInputStream ("Score DataBase.txt");
            ObjectInputStream o = new ObjectInputStream (file);
            o.readObject();
            hp=(int) o.readObject();
            level=(String) o.readObject();
            o.close();
        } catch (Exception e) {
        }
    }
    

    //------------------ xxxxxxxxxxxxxxxxxx -------------------- </>
    
    
    
    
    public fSnake() {
        initComponents();
        
        this.setLocationRelativeTo(this);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("snk.png")));

        this.setLocationRelativeTo(this);
        System.out.println("StarT\nEnter the Delay: "); 
        restore();
        level();
        tail=new ArrayList<>();
        hs=false;
        

//        sc=new Scanner(System.in);
//        speed = sc.nextInt();
        
        start=true;
        //snake move initialize <>
        l=false;
        u=false;
        d=false;
        r=true;
        Thread t = new Thread(){
            public void run(){
                for(;r;){
                    rightMove();
                    tail();
                    foodMove();
                    System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                    System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                    try{
                        sleep(speed);
                    }
                    catch(Exception e){
                    }
                    
                }
            }
        };
        if(r){
            t.start();
        }
        //snake moved initialized </>
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        snake = new javax.swing.JPanel();
        s2 = new javax.swing.JPanel();
        s1 = new javax.swing.JPanel();
        food = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Play Mode");
        setBounds(new java.awt.Rectangle(400, 100, 0, 0));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(166, 82, 5));
        jPanel1.setPreferredSize(new java.awt.Dimension(605, 405));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel1KeyReleased(evt);
            }
        });

        snake.setBackground(new java.awt.Color(0, 0, 102));
        snake.setForeground(new java.awt.Color(153, 0, 0));
        snake.setPreferredSize(new java.awt.Dimension(18, 18));

        javax.swing.GroupLayout snakeLayout = new javax.swing.GroupLayout(snake);
        snake.setLayout(snakeLayout);
        snakeLayout.setHorizontalGroup(
            snakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        snakeLayout.setVerticalGroup(
            snakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        s2.setBackground(new java.awt.Color(0, 0, 255));
        s2.setForeground(new java.awt.Color(153, 0, 0));
        s2.setPreferredSize(new java.awt.Dimension(18, 18));

        javax.swing.GroupLayout s2Layout = new javax.swing.GroupLayout(s2);
        s2.setLayout(s2Layout);
        s2Layout.setHorizontalGroup(
            s2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        s2Layout.setVerticalGroup(
            s2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        s1.setBackground(new java.awt.Color(0, 0, 153));
        s1.setForeground(new java.awt.Color(153, 0, 0));
        s1.setPreferredSize(new java.awt.Dimension(18, 18));

        javax.swing.GroupLayout s1Layout = new javax.swing.GroupLayout(s1);
        s1.setLayout(s1Layout);
        s1Layout.setHorizontalGroup(
            s1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        s1Layout.setVerticalGroup(
            s1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        food.setBackground(new java.awt.Color(255, 255, 0));
        food.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        food.setForeground(new java.awt.Color(153, 0, 153));

        javax.swing.GroupLayout foodLayout = new javax.swing.GroupLayout(food);
        food.setLayout(foodLayout);
        foodLayout.setHorizontalGroup(
            foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        foodLayout.setVerticalGroup(
            foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(s1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snake, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(food, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(544, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(food, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(snake, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(324, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
                

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //foodMove();
            System.out.println("sPACe");
        }
        
        //snake movement function//
        if(start){
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT && i!=2 && i!=1){
                l=false;
                u=false;
                d=false;
                r=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;r;){
                            rightMove();
                            tail();
                            foodMove();
                            gameOver();
                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(r){
                    t.start();
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_LEFT && i!=1 && i!=2){
                r=false;
                u=false;
                d=false;
                l=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;l;){
                            leftMove();
                            tail();
                            foodMove();
                            gameOver();

                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(l){
                    t.start();
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_UP && i!=4 && i!=3){
                r=false;
                l=false;
                d=false;
                u=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;u;){
                            upMove();
                            tail();
                            foodMove();
                            gameOver();
                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(u){
                    t.start();
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_DOWN && i!=3 && i!=4){
                r=false;
                l=false;
                u=false;
                d=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;d;){
                            downMove();
                            tail();
                            foodMove();
                            gameOver();
                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(d){
                    t.start();
                }
            }
            //
            if(evt.getKeyCode()==KeyEvent.VK_D && i!=2 && i!=1){
                l=false;
                u=false;
                d=false;
                r=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;r;){
                            rightMove();
                            tail();
                            foodMove();
                            gameOver();
                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(r){
                    t.start();
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_A && i!=1 && i!=2){
                r=false;
                u=false;
                d=false;
                l=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;l;){
                            leftMove();
                            tail();
                            foodMove();
                            gameOver();

                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(l){
                    t.start();
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_W && i!=4 && i!=3){
                r=false;
                l=false;
                d=false;
                u=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;u;){
                            upMove();
                            tail();
                            foodMove();
                            gameOver();
                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(u){
                    t.start();
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_S && i!=3 && i!=4){
                r=false;
                l=false;
                u=false;
                d=true;
                Thread t = new Thread(){
                    public void run(){
                        for(;d;){
                            downMove();
                            tail();
                            foodMove();
                            gameOver();
                            System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");
                            System.err.println(x1+"/"+y1+"\t"+x2+"/"+y2);
                            try{
                                sleep(speed);
                            }
                            catch(Exception e){
                            }
                        }
                    }
                };
                if(d){
                    t.start();
                }
            }
            //
        }
        
    }//GEN-LAST:event_formKeyReleased
// foodMove();
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:

//        s1.setLocation(x1, y1);
//        s2.setLocation(x2, y2);
//        s3.setLocation(x3, y3);
//        s4.setLocation(x4, y4);
//        
        //
//        if(evt.getKeyCode()==KeyEvent.VK_RIGHT && i!=2 && i!=1){
//            con=false;
//            con1=true;
//            Thread t = new Thread(){
//                public void run(){
//                    for(;con1;){
//                        rightMove();
//                        tail();
//                        foodMove();
//                        try{
//                            sleep(speed);
//                        }
//                        catch(Exception e){
//                        }
//                    }
//                }
//            };
//            if(con1){
//                t.start();
//            }
//        }
////        
//        if(evt.getKeyCode()==KeyEvent.VK_LEFT && i!=1 && i!=2){
//            con=false;
//            con1=true;
//            Thread t = new Thread(){
//                public void run(){
//                    for(;con1;){
//                        leftMove();
//                        tail();
//                        foodMove();
//                        try{
//                            sleep(speed);
//                        }
//                        catch(Exception e){
//                        }
//                    }
//                }
//            };
//            if(con1){
//                t.start();
//            }
//        }
////
//        if(evt.getKeyCode()==KeyEvent.VK_UP && i!=4 && i!=3){   
//            con=false;
//            con1=true;
//            Thread t = new Thread(){
//                public void run(){
//                    for(;con1;){
//                        upMove();
//                        tail();
//                        foodMove();
//                        try{
//                            sleep(speed);
//                        }
//                        catch(Exception e){
//                        }
//                    }
//                }
//            };
//            if(con1){
//                t.start();
//            }
//        }
////        
//        if(evt.getKeyCode()==KeyEvent.VK_DOWN && i!=3 && i!=4){
//            con=false;
//            con1=true;
//            Thread t = new Thread(){
//                public void run(){
//                    for(;con1;){
//                        downMove();
//                        tail();
//                        foodMove();
//                        try{
//                            sleep(speed);
//                        }
//                        catch(Exception e){
//                        }
//                    }
//                }
//            };
//            if(con1){
//                t.start();
//            }
//        }
//
//        
//        //tail();
//        
//        System.out.println("x= "+x+"\ty= "+y+"\nfx= "+fx+"\tfy= "+fy+"\f");     
//        System.out.println(x1+"/"+y1+"\t"+x2+"/"+y2+"\t"+x3+"/"+y3+"\t"+x4+"/"+y4);
        
    }//GEN-LAST:event_formKeyPressed

    private void jPanel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyReleased

    }//GEN-LAST:event_jPanel1KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(fSnake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fSnake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fSnake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fSnake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fSnake().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel food;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel s1;
    private javax.swing.JPanel s2;
    private javax.swing.JPanel snake;
    // End of variables declaration//GEN-END:variables
}
