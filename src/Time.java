
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
		if(HH > t.HH){
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
		}
	}
}
