import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUITick {
    private JCheckBox chbEnabled;
    private JPanel MainPanel;
    private JPanel KnobPanel;
    private JCheckBox chbTickInt;
    private JPanel TickPanel;
    private JTextField CVRin;
    private JTextField RVRin;
    private JTextField CSRin;
    private JButton setCVRButton;
    private JButton setRVRButton;
    private JButton setCSRButton;
    private JLabel CVRout;
    private JLabel RVRout;
    private JLabel CSRout;
    private JRadioButton sourceInternalRadioButton;
    private JRadioButton sourceExternalRadioButton;
    private JButton TickButton;
    private JLabel Enable_on;
    private JLabel Source_on;
    private JLabel Count_on;
    private JLabel Interrupt_on;
    private JLabel isInterrupt_on;
    private JRadioButton BURSTRadioButton;
    private JRadioButton CONTINOUSRadioButton;
    private JComboBox BurstValue;
    private JButton gonButton;

    private int value;
    Knob knob = new Knob();
    JFrame window = new JFrame();
    Cortex_M0_SysTick stick = new Cortex_M0_SysTick();
    Generator generator = new Generator();


    public GUITick(){
        window.setMinimumSize(new Dimension(1000,1000));
        generator.start();
        generator.addActionListener(e->{
            stick.tickInternal();
            Get_values();
        });

        generator.setMode(PulseSource.CONTINOUS_MODE);
        generator.startGeneration();
        generator.setPulseCount(100);
        KnobPanel.add(knob);
        knob.addActionListener(e->{
            value = (int) knob.getValue();
            generator.setPulseDelay(value);
            Get_values();
        });

        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.add(MainPanel);

        chbEnabled.setSelected(true);
        sourceInternalRadioButton.setSelected(true);
        stick.setSourceInternal();
        stick.setEnable();

//WPISANA WARTOSC

        setCVRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stick.setCVR(Integer.parseInt(CVRin.getText()));
                Get_values();
            }
        });

        setRVRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stick.setRVR(Integer.parseInt(RVRin.getText()));
                Get_values();
            }
        });
        setCSRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stick.setCSR(Integer.parseInt(CSRin.getText()));
                Get_values();
            }
        });

//CHECKBOXY
        chbTickInt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chbTickInt.isSelected()) {
                    stick.setInterruptEnable();
                    stick.playSound("C:\\Users\\kinga\\Desktop\\studia\\java\\generator\\src\\winxpshutdown.wav");
                }
                else stick.setInterruptDisable();
            }
        });

        chbEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chbEnabled.isSelected()) stick.setEnable();
                else stick.setDisable();
            }
        });
        sourceInternalRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sourceInternalRadioButton.isSelected()) stick.setSourceInternal();
            }
        });
        sourceExternalRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sourceExternalRadioButton.isSelected()) stick.setSourceExternal();
            }
        });

//GUZIK OD TICK
        TickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sourceExternalRadioButton.isSelected())
                {
                    stick.tickExternal();
                    Get_values();
                }
            }
        });


        BURSTRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generator.setMode(PulseSource.BURST_MODE);
            }
        });

        CONTINOUSRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generator.setMode(PulseSource.CONTINOUS_MODE);
            }
        });

        BurstValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mod = BurstValue.getSelectedIndex();
                if(mod==0) generator.setPulseCount(100);
                if(mod==1) generator.setPulseCount(10);
            }
        });
        gonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generator.startGeneration();
                int mod = BurstValue.getSelectedIndex();
                if (mod == 0) generator.setPulseCount(100);
                if (mod == 1) generator.setPulseCount(10);
            }
        });
    }

//USTAWIANIE WARTOSCI
    public void Get_values()
    {
        RVRout.setText(String.valueOf(stick.getRVR()));
        CVRout.setText(String.valueOf(stick.getCVR()));
        CSRout.setText(String.valueOf(stick.getCSR_value()));
        Enable_on.setText(String.valueOf(stick.isEnableFlag()));
        Count_on.setText(String.valueOf(stick.isCountFlag()));
        Source_on.setText(String.valueOf(stick.getSource()));
        isInterrupt_on.setText(String.valueOf(stick.isInterrupt()));
        Interrupt_on.setText(String.valueOf(stick.isInterruptFlag()));
    }

    public static void main(String[] args) {
        new GUITick();
    }

}