import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Cortex_M0_SysTick implements Cortex_M0_SysTick_Interface {

    int CVR, RVR, CSR;
    boolean enable, source, interrupt, interruptFlag, countFlag;

    private void tick(){
        if (enable) {
            if (CVR == 0) {
                CVR = RVR;
                return;
            }
            else{
                CVR--;
            }
            if (CVR==0) {
                countFlag = true;

                if (interruptFlag) {
                    interrupt=true;
                }
            }
            if (RVR==0) {
                setDisable();
            }
        }
    }

    @Override

    public void tickInternal() {
        if (source) {
            tick();
        }
    }

    @Override
    public void tickExternal() {
        if (!source) {
            tick();
        }
    }

    @Override
    public void setRVR(int value) {
        if(value>=0&&value<=16777215) {
            RVR=value;
        }
        else if(value<0) {
            RVR=16777216+value;
        }
        else if (value>16777215) {
            RVR=value-16777216;
        }
    }

    @Override
    public void setCVR(int value) {
        CVR = 0;
        countFlag=false;
    }

    @Override
    public void setCSR(int value) {
        CSR =  value;
    }

    @Override
    public void reset() {
        CSR=0;
    }

    @Override
    public void setEnable() {
        enable=true;
    }

    @Override
    public void setDisable() {
        enable=false;
    }

    @Override
    public void setSourceExternal() {
        source=false;
    }

    @Override
    public void setSourceInternal() {
        source=true;
    }

    @Override
    public void setInterruptEnable() {
        interruptFlag=true;
    }

    @Override
    public void setInterruptDisable() {
        interruptFlag=false;
    }

    //get zmienia rejest statusowy

    @Override
    public int getCVR() {
        return CVR;
    }

    @Override
    public int getRVR() {
        return RVR;
    }

    @Override
    public int getCSR() {
        countFlag=false;
        return CSR;
    }

    public int getCSR_value()
    {
        return CSR;
    }

    @Override
    public boolean getEnabled() {
        countFlag=false;
        return enable;
    }

    @Override
    public boolean getInterrupt() {
        countFlag=false;
        return interrupt;
    }

    @Override
    public boolean getSource() {
        countFlag=false;
        return source;
    }

    @Override
    public boolean getCountFlag() {
        boolean tmp = countFlag;
        countFlag=false;
        return tmp;
    }

    @Override
    public boolean isCountFlag() {
        return countFlag;
    }

    @Override
    public boolean isEnableFlag() {
        return enable;
    }

    @Override
    public boolean isInterruptFlag() {
        return interruptFlag;
    }

    @Override
    public boolean isInterrupt() {
        return interrupt;
    }

    public void playSound(String soundFile) {
        try {
            File audio = new File(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cortex_M0_SysTick systick = new Cortex_M0_SysTick();
        systick.setEnable();
        systick.tickInternal();
    }

}
