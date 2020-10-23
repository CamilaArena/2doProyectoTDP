package logica;

import java.util.Timer;

public class Reloj {

	private Timer timer;
	private String strHours;
	private String strMins;
	private String strSecs;


	public Reloj() {
		timer=new Timer();
	}
	public Timer getTimer() {
		return timer;
	}
	public String getTime(int sec) {
		int hours=0;
		int remainderOfHours=0;
		int minutes=0;
		int seconds=0;
		if(sec>=3600) {
			hours=sec/3600;
			remainderOfHours=sec%3600;
			if(remainderOfHours>=60) {
				minutes=remainderOfHours/60;
				seconds=remainderOfHours%60;
			}
			else
				seconds=remainderOfHours;
		}
		else
			if(sec>=60) {
				hours=0;
				minutes=sec/60;
				seconds=sec%60;
			}
			else
				if(sec<60) {
					hours=0;
					minutes=0;
					seconds=sec;
				}

		if(seconds<10)
			strSecs= "0"+Integer.toString(seconds);
		else
			strSecs=Integer.toString(seconds);

		if(minutes<10)
			strMins="0"+Integer.toString(minutes);
		else
			strMins=Integer.toString(minutes);

		if(hours<10)
			strHours="0"+Integer.toString(hours);
		else
			strHours=Integer.toString(hours);

		String time= strHours+":"+strMins+":"+strSecs;

		return time;
	}
	
	public String getHora() {
		return strHours;
	}
	
	public String getMin() {
		return strMins;
	}
	
	public String getSeg() {
		return strSecs;
	}
}
