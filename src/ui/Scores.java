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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.List;
import domain.Player;

/**
 *
 * @author Nick
 */
public class Scores extends JFrame implements WindowListener{
    private Container container = this.getContentPane();
    private DefaultListModel lm = new DefaultListModel();
    private JList list = new JList(lm);
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel scorepanel = new JPanel();
    private Jewels parent;
    private ButtonGroup group = new ButtonGroup();
    private JRadioButton standard = new JRadioButton("Standard", true);
    private JRadioButton timed = new JRadioButton("Timed", false);
    private int type=1;
    
    
    public Scores(){
        super("High Scores");
        

    }
    public void init(){
        parent.setEnabled(false);
        container.add(scorepanel);
        group.add(standard);
        group.add(timed);
        scorepanel.add(standard);
        scorepanel.add(timed);
        scorepanel.add(list);
        scrollPane.getViewport().add(list);
        scorepanel.add(scrollPane, BorderLayout.EAST);
        list.setCellRenderer(new ListCellRenderer() {
            public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean bln, boolean bln1) {
                Player player = (Player) o;
                int index = i + 1;
                JLabel label = new JLabel(index + ". " + player.getName() + " - " + player.getScore());
                return label;
            }
        });
        
        standard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
               Scores.this.setType(1);
               lm.clear();
               Scores.this.addScores();
            }
        });

        timed.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e1) {
               Scores.this.setType(2);
               lm.clear();
               Scores.this.addScores();
            }
        });
        this.addScores();
        addWindowListener(this);
        setLocationRelativeTo(null);
        this.setSize(165, 250);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }
    
    
    public void setParent(Jewels parent){
        if(parent!=null){
            this.parent=parent;
        }
    }
    
    private void setType(int selected){
        if(selected==1||selected==2){
            this.type=selected;
        }
    }
    
    private int getTypeSelected(){
        return this.type;
    }
    
    private boolean isSelected(int type){
        boolean res = false;
        if(type == this.type){
            res=true;
        }
        return res;
    }

    private void addScores() {
        int type = this.getTypeSelected();
        List<Player> players = parent.getDb().getAllPlayers();
        Collections.sort(players);
        for(Player p: players){
            if(p.getType()==type){
                lm.addElement(p);
                scrollPane.getViewport().add(list);
                container.validate();
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Scores.this.parent.setEnabled(true);
        Scores.this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
