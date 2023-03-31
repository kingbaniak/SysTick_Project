import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AWTEventMulticaster;
import javax.sound.sampled.*;

public class Generator extends Thread implements PulseSource {
    private final byte BURST_MODE = 0;
    private final byte CONTINOUS_MODE = 1;
    private byte MODE = 0;

    private int DELAY = 1000;
    private int PULSE = 100;

    private boolean alive;
    private boolean on;
    ActionListener listener;

    public void addActionListener(ActionListener l) {
       listener = AWTEventMulticaster.add(listener,l);
    }
    public void removeActionListener(ActionListener l) {
        listener = AWTEventMulticaster.remove(listener,l);
    }

    @Override
    public void startGeneration() {
        on = true;
    }

    @Override
    public void setMode(byte mode) {
        MODE = mode;
    }

    @Override
    public byte getMode() {
        return MODE;
    }

    @Override
    public void stopGeneration() {
        on = false;
    }

    @Override
    public void setPulseDelay(int time) {
        DELAY = time;
    }

    @Override
    public int getPulseDelay() {
        return DELAY;
    }

    @Override
    public void setPulseCount(int burst) {
        PULSE = burst;
    }

    public void run() {
        alive = true;
        while (alive) {
            if (on) {
                if(MODE==BURST_MODE){
                    PULSE--;
                    if (PULSE<=0)
                        on=false;
                }
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (listener == null)
                    System.out.println("Tick");
                else
                    listener.actionPerformed(
                            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "tick")
                    );
            } else {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void killThread(){
        alive=false;
    }
    public boolean checkOn()
    {
        return on;
    }

    public static void main(String[] args) {
        JFrame okno = new JFrame();
        JButton button = new JButton("Wywolaj Tick");
        Generator g = new Generator();
        g.start();
        g.addActionListener(e->{
            System.out.println("inny tick");
        });
        button.addActionListener(e-> {
            if(g.checkOn())
                g.stopGeneration();
            else
                g.startGeneration();
        });
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.add(button);
        okno.setSize(400,500);
        okno.setVisible(true);
    }
}