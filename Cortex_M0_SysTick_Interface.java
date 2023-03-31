public interface Cortex_M0_SysTick_Interface {


	void tickInternal();
     void tickExternal();

     void setRVR(int value);
     void setCVR(int value);
     void setCSR(int value);
	 void playSound(String soundFile);

     void reset();

     void setEnable();

	     void setDisable();
	     void setSourceExternal();
	     void setSourceInternal();
	     void setInterruptEnable();
	     void setInterruptDisable();


	     int getCVR();
	     int getRVR();
	     int getCSR();

	     boolean getEnabled();
	     boolean getInterrupt();
	     boolean getSource();
	     boolean getCountFlag();

	     boolean isCountFlag();
	     boolean isEnableFlag();
	     boolean isInterruptFlag();
	     boolean isInterrupt();





}

