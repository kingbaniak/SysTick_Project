import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class Knob extends JComponent
                  implements MouseListener, MouseMotionListener {

    int c=0;
    int lx2,ly2;
    double theta=3*Math.PI/2;
    double value, i_value;
    ActionListener listener;
    double r_value;
    void addActionListener(ActionListener l) {
        listener = AWTEventMulticaster.add(listener,l);
    }
    void removeActionListener(ActionListener l) {
        listener = AWTEventMulticaster.remove(listener,l);
    }
    public Knob(){
        addMouseListener(this);
        addMouseMotionListener(this);
        repaint();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Dimension d = getSize();
        int x = d.width;
        int y = d.height-50;
        int r = Math.min(x,y);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.drawOval(x/2-r/2,y/2-r/2,r,r);

        g.drawLine(x/2,y/2,x/2-r/2,y/2);

        g.setColor(Color.blue);
        g.drawLine(x/2,y/2,x/2+(int)(r/2 * Math.cos(theta-Math.PI/2)),y/2+(int)(r/2 * Math.sin(theta-Math.PI/2)));
        r_value = Math.round(value*100.0)/100.0;
        i_value = Math.round((value*1000));
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(Double.toString(r_value), x/2-r/2, y/2+r/2+40);
    }
    public static void main(String[] args) {
        JFrame okno = new JFrame();
        okno.setSize(500, 400);
        okno.add(new Knob());
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /////////////// MOUSE LISTENER ////////////////////
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /////////////// MOUSE MOTION LISTENER ////////////////////

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Dimension d = getSize();
        int x = d.width;
        int y = d.height;

        lx2=e.getX();
        ly2=e.getY();

        int mxp = lx2 - x/2;
        int myp = y/2 - ly2;
        theta = Math.atan2(mxp, myp);
        if (theta+Math.PI/2>=0) {
            value = ((theta + Math.PI / 2) / Math.PI) / 2;
       }
        else{
            value=(2.5*Math.PI+theta)/(2*Math.PI);
        }

        if (listener == null)
            System.out.println(i_value);
        else
            listener.actionPerformed(
                    new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "tick")
            );

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public double getValue(){
        return i_value;
    }
}


