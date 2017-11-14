
public class Time {
	
	private int HH;
	private int MM;
	private int SS;
	private int MS;
	
	public int getHH() {
		return HH;
	}
	public void setHH(int hh) {
		HH = hh;
	}
	public int getMM() {
		return MM;
	}
	public void setMM(int mm) {
		MM = mm;
	}
	public int getSS() {
		return SS;
	}
	public void setSS(int ss) {
		SS = ss;
	}
	public int getMS() {
		return MS;
	}
	public void setMS(int ms) {
		MS = ms;
	}
	
	public int compare(Time t){ // return 0 when equal, -1 when smaller than parameter, 1 when bigger than parameter.
		/*if(HH > t.HH){
			return 1;
		}
		else if(HH < t.HH){
			return -1;
		}
		else{
			if(MM > t.MM){
				return 1;
			}
			else if(MM < t.MM){
				return -1;
			}
			else{
				if(SS > t.SS){
					return 1;
				}
				else if(SS < t.SS){
					return -1;
				}
				else{
					if(MS > t.MS){
						return 1;
					}
					else if(MS < t.MS){
						return -1;
					}
					else{
						return 0;
					}
				}
			}
		}*/
		int OT = this.getTMS();
		int PT = t.getTMS(); 
		if(OT > PT){
			return 1;
		}
		else if(OT < PT){
			return -1;
		}
		else
			return 0;
	}
	
	//@utttu please check if this is correct !!
	public int getTMS(){// get the total of MS in the time
		return (MS + (SS*1000) + (MM*1000*60) + (HH*1000*60*60));
	}
	
	//@utttu please check if this is correct !!
	public void setTMS(int TMS){// set the total of MS in the time
		HH = TMS/1000/60/60;
		TMS -= HH*1000*60*60;
		MM = TMS/1000/60;
		TMS -= MM*1000*60;
		SS = TMS/1000;
		TMS -= SS*1000;
		MS = TMS;
	}
}
