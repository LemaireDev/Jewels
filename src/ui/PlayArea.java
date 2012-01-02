/** Copyright 2011 Nick Lemaire

This file is part of Jewels.

    Jewels is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation version 3.

    Jewels is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Jewels.  If not, see <http://www.gnu.org/licenses/>.
*/
package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Nick
 */
public abstract class PlayArea extends JFrame implements ActionListener {
    public Container c = this.getContentPane();
    public JPanel area = new JPanel();
    public Jewels parent;
    public ArrayList<Field> fields = new ArrayList();
    public int indexa, indexb;
    public Timer timer;
    public int score;
    public JLabel scorelabel = new JLabel();
    public GridBagConstraints con = new GridBagConstraints();
 //   private boolean highlight=false;
    
    public void template(){
        this.setTitle("Jewels");
        parent.setEnabled(false);

        area.setLayout(new GridBagLayout());
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Field field = new Field();
                fields.add(field);
                field.setPreferredSize(new Dimension(70, 70));
                field.setBackground(Color.LIGHT_GRAY);
                Jewel jewel = this.getRandomJewel();
                field.setJewel(jewel);
                field.setBorder(BorderFactory.createLineBorder (Color.DARK_GRAY, 2));
                field.addMouseListener(new FieldMouseListener());
                con.fill = GridBagConstraints.HORIZONTAL;
                con.gridx = j;
                con.gridy = i;
                area.add(field, con);
            }
        }
        c.add(area, BorderLayout.CENTER);
        
        scorelabel.setText("Score: "+this.getScore());
        scorelabel.setFont(new Font("Verdana",Font.BOLD,21));
        con.fill = GridBagConstraints.HORIZONTAL;
        con.gridx = 0;
        con.gridy = 9;
        con.gridwidth=3;
        area.add(scorelabel, con);
        this.preparePlayArea();
        this.init();
    }
    
    public void setParent(Jewels parent){
        if(parent!=null){
            this.parent=parent;
        }
    }
    
    abstract public void init();

    public Jewel getRandomJewel() {
        Jewel res=null;
        int n = (int)(7.0 * Math.random());
        if(n == 0){
            res=new Jewel("blue");
        } else if(n == 1){
            res=new Jewel("red");
        } else if(n == 2){
            res=new Jewel("yellow");
        } else if(n == 3){
            res=new Jewel("green");
        } else if(n == 4){
            res=new Jewel("white");
        } else if(n == 5){
            res=new Jewel("purple");
        } else if(n == 6){
            res=new Jewel("orange");
        }
        return res;
    }
    
    public void highlightOptions(int index){
        if(index>=0||index<fields.size()-1){
            int[] options = this.getOptions(index);
            for(int a: options){
                if(a>-1){
                   fields.get(a).setBorder(BorderFactory.createLineBorder (Color.DARK_GRAY, 4));
                }
            }
        }
    }
    
    public boolean getSelectionMade(){
        boolean res = false;
        for(Field f: fields){
            if(f.getIsSelected()){
                res=true;
            }
        }
        return res;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void setScore(int score){
        if(score>=0){
            this.score=score;
        }
    }
    
    public void setNotSelected(){
        for(Field f: fields){
            f.setIsSelected(false);
        }
    }
    
    public int[] getOptions(int index){
        int[] res = null;
        int aantal = 0;
        int a=-1;
        int b=-1;
        int c=-1;
        int d=-1;
        if(index>=0||index<fields.size()-1){
            if(index-7>0){
                aantal++;
                a=index-8;
            }
            if(index+8<64){
                aantal++;
                b=index+8;
            }
            if((index+1)%8!=0){
                aantal++;
                c=index+1;
            }
            if(index%8!=0){
                aantal++;
                d=index-1;
            }
        }
        res = new int[]{a,b,c,d};
        return res;
    }
    
    public boolean combinationPresent(){
        boolean res = false;
        for(int i=0;i<fields.size();i++){
            if(i%8!=0&&(i+1)%8!=0){
                if(fields.get(i).getColor().equals(fields.get(i-1).getColor())&&fields.get(i).getColor().equals(fields.get(i+1).getColor())){
                    res = true;
                }
                
            }
            if(i-8>=0&&i+8<=63){
                if(fields.get(i).getColor().equals(fields.get(i-8).getColor())&&fields.get(i).getColor().equals(fields.get(i+8).getColor())){
                    res = true;
                }
            }
        }
        return res;
    }
    
    public void checkCombinations(){
        for(int i=0;i<fields.size();i++){
            if(i%8!=0&&(i+1)%8!=0){
                if(fields.get(i).getColor().equals(fields.get(i-1).getColor())&&fields.get(i).getColor().equals(fields.get(i+1).getColor())){
                    fields.get(i).setMarkedForDel(true);
                    fields.get(i-1).setMarkedForDel(true);
                    fields.get(i+1).setMarkedForDel(true);
                }
                
            }
            if(i-8>=0&&i+8<=63){
                if(fields.get(i).getColor().equals(fields.get(i-8).getColor())&&fields.get(i).getColor().equals(fields.get(i+8).getColor())){
                    fields.get(i).setMarkedForDel(true);
                    fields.get(i-8).setMarkedForDel(true);
                    fields.get(i+8).setMarkedForDel(true);
                }
            }
        }
        for(int i=0;i<fields.size();i++){
            if(fields.get(i).isMarkedForDel()){
                this.setScore(this.getScore()+10);
                this.scorelabel.setText("Score: "+this.getScore());
                fields.get(i).clearJewel();
                fields.get(i).setMarkedForDel(false);
            }
        }
        this.repaint();
    }
    
    public void dropJewels(){
       for(int j=0;j<8;j++){
        for(int i=0;i<fields.size();i++){
            if(i+8<64&&fields.get(i).getHasJewel()&&!fields.get(i+8).getHasJewel()){
                fields.get(i+8).setJewel(fields.get(i).getJewel());
                fields.get(i).clearJewel();
            }
         }
       }
       this.repaint();
    }
    
    public void fill(){
        if(!this.isFilled()){
            for(Field a: fields){
                if(!a.getHasJewel()){
                 Jewel jewel = this.getRandomJewel();
                 a.setJewel(jewel);
                 //this.repaint();
                 //this.revalidate();
                }
            }
        }
    }
    
    public void update(){
        if(this.combinationPresent()){
            while(this.combinationPresent()||!this.isFilled()){
                this.checkCombinations();
                this.dropJewels();
                this.fill();
            }
        }
        if(!this.checkPossibleCombo()){
            JOptionPane.showMessageDialog(null,"No more moves!");
            PlayArea.this.processWindowEvent(
                new WindowEvent(PlayArea.this, WindowEvent.WINDOW_CLOSING));
            timer.stop();
        }
    }
    
    public boolean isFilled(){
        boolean res = true;
        for(Field a: fields){
            if(!a.getHasJewel())
                res=false;
        }
        return res;
    }
    
    public boolean checkFieldCombo(int indexa, int indexb){
        boolean res = false;
        if(indexb%8!=0&&(indexb+1)%8!=0&&indexb-1!=indexa&&indexb+1!=indexa){
            if(fields.get(indexa).getColor().equals(fields.get(indexb-1).getColor())&&fields.get(indexa).getColor().equals(fields.get(indexb+1).getColor())){
                res=true;
            }
        }
        if((indexb+2)%8!=0&&(indexb+1)%8!=0&&indexb+1!=indexa){
            if(fields.get(indexa).getColor().equals(fields.get(indexb+1).getColor())&&fields.get(indexa).getColor().equals(fields.get(indexb+2).getColor())){
                res=true;
            }
        }
        if((indexb)%8!=0&&(indexb-1)%8!=0&&indexb-1!=indexa){
            if(fields.get(indexa).getColor().equals(fields.get(indexb-1).getColor())&&fields.get(indexa).getColor().equals(fields.get(indexb-2).getColor())){
                res=true;
            }
        }
        if(indexb-8>=0&&indexb+8<=63&&indexb-8!=indexa){
            if(fields.get(indexa).getColor().equals(fields.get(indexb-8).getColor())&&fields.get(indexa).getColor().equals(fields.get(indexb+8).getColor())){
                res=true;
            }
        }
        if(indexb-16>=0&&indexb-8!=indexa){
            if(fields.get(indexa).getColor().equals(fields.get(indexb-8).getColor())&&fields.get(indexa).getColor().equals(fields.get(indexb-16).getColor())){
                res=true;
            }
        }
        if(indexb+16<=63&&indexb+8!=indexa){
            if(fields.get(indexa).getColor().equals(fields.get(indexb+8).getColor())&&fields.get(indexa).getColor().equals(fields.get(indexb+16).getColor())){
                res=true;
            }
        }
        return res;
    }
    
    public boolean checkPossibleCombo(){
        boolean res=false;
        for(int i=0;i<fields.size();i++){
            for(int j=0;j<this.getOptions(i).length;j++){
                int[] options = this.getOptions(i);
                if(options[j]>-1){
                    if(checkFieldCombo(i,options[j])){
                        res=true;
                    }
                }
            }
        }
        return res;
    }
    
    public void moveJewels(int indexa, int indexb){
        int[] options = this.getOptions(indexa);
        boolean ok = false;
        for(int i=0;i<options.length;i++){
            if(options[i]==indexb){
                ok=true;
            }
        }
        if(ok){
            if(this.checkFieldCombo(indexa, indexb)||this.checkFieldCombo(indexb, indexa)){
                Jewel jewela = fields.get(indexa).getJewel();
                Jewel jewelb = fields.get(indexb).getJewel();
                fields.get(indexa).clearJewel();
                fields.get(indexb).clearJewel();
                fields.get(indexa).setJewel(jewelb);
                fields.get(indexb).setJewel(jewela);
                for(Field a: fields){
                    a.setNotAnimated();
                }
            }
        }
    }
    
    public void checkCombinationsNoScore(){
        for(int i=0;i<fields.size();i++){
            if(i%8!=0&&(i+1)%8!=0){
                if(fields.get(i).getColor().equals(fields.get(i-1).getColor())&&fields.get(i).getColor().equals(fields.get(i+1).getColor())){
                    fields.get(i).setMarkedForDel(true);
                    fields.get(i-1).setMarkedForDel(true);
                    fields.get(i+1).setMarkedForDel(true);
                }
                
            }
            if(i-8>=0&&i+8<=63){
                if(fields.get(i).getColor().equals(fields.get(i-8).getColor())&&fields.get(i).getColor().equals(fields.get(i+8).getColor())){
                    fields.get(i).setMarkedForDel(true);
                    fields.get(i-8).setMarkedForDel(true);
                    fields.get(i+8).setMarkedForDel(true);
                }
            }
        }
        for(int i=0;i<fields.size();i++){
            if(fields.get(i).isMarkedForDel()){
                fields.get(i).clearJewel();
                fields.get(i).setMarkedForDel(false);
            }
        }
        this.repaint();
    }
    
    public void preparePlayArea(){
        if(this.combinationPresent()){
            while(this.combinationPresent()||!this.isFilled()){
                this.checkCombinationsNoScore();
                this.dropJewels();
                this.fill();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        this.update();
        this.repaint();
    }

     class FieldMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent me) {                 
        }

        public void mousePressed(MouseEvent me) {
            for(Field a: fields){
                a.setNotAnimated();
            }
            Field field = (Field) me.getComponent();
            field.setAnimated();

            for(Field a: fields){
                a.setBorder(BorderFactory.createLineBorder (Color.DARK_GRAY, 2));
            }
            if(PlayArea.this.getSelectionMade()){
                indexb =PlayArea.this.fields.indexOf(field);
                PlayArea.this.moveJewels(indexa, indexb);
                PlayArea.this.setNotSelected();
            } else for(Field a: fields){
                field.setIsSelected(true);
                indexa = PlayArea.this.fields.indexOf(field);
            }
//            if(jewels.contains(jewel)&&highlight==true){
//                PlayArea.this.highlightOptions(jewels.indexOf(jewel));
//            }
        }

        public void mouseReleased(MouseEvent me) {
        }

        public void mouseEntered(MouseEvent me) {
        }

        public void mouseExited(MouseEvent me) {
        }
    }

}
