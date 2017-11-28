
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
	
	public String toString()
	{
		String s = "Hourse(HH)= "+getHH()+", Minutes(MM)= "+getMM()+", Seconds(SS)= "+getSS()+", MilleSeconds(MS)= "+getMS();
		return s;
	}
}
