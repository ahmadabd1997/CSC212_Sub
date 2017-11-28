
public class Times implements Time{
	
	private int HH;
	private int MM;
	private int SS;
	private int MS;
	
	public Times(){
		HH=MM=SS=MS=0;
	}
	public Times(int h,int m,int s, int ms){
		this.HH=h;
		this.MM=m;
		this.SS=s;
		this.MS=ms;
	}
	public Times(String hh, String mm, String ss, String ms) {
		this.HH = Integer.parseInt(hh);
		this.MM = Integer.parseInt(mm);
		this.SS = Integer.parseInt(ss);
		this.MS = Integer.parseInt(ms);
	}

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
	
	/*public int compare(Times t){ // return 0 when equal, -1 when smaller than parameter, 1 when bigger than parameter.
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
	
	public int getTMS(){// get the total of MS in the time
		return (getMS() + (getSS()*1000) + (getMM()*1000*60) + (getHH()*1000*60*60));
	}
	
	public void setTMS(int TMS){// set the total of MS in the time
		HH = TMS/1000/60/60;
		TMS -= HH*1000*60*60;
		MM = TMS/1000/60;
		TMS -= MM*1000*60;
		SS = TMS/1000;
		TMS -= SS*1000;
		MS = TMS;
	}*/
	
	public String toString()
	{
		String s = "Hourse(HH)= "+getHH()+", Minutes(MM)= "+getMM()+", Seconds(SS)= "+getSS()+", MilleSeconds(MS)= "+getMS();
		return s;
	}
}
