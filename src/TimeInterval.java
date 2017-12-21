public class TimeInterval implements Comparable<TimeInterval> {
	private Time startTime;
	private Time endTime;
	
	
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public TimeInterval(Time startTime, Time endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public int find(Time k) {
		if(compare(startTime,k) <= 0 && compare(endTime,k) >= 0)
			return 0;
		else if (compare(startTime,k) > 0)
			return -1;
		else 
			return 1;
		
	}
	public void shift(int k) {
		setTMS(startTime,getTMS(startTime)+k);
		setTMS(endTime,getTMS(endTime)+k);
	}
	
	@Override
	public int compareTo(TimeInterval that) {
		if (compare(startTime,that.endTime) > 0) {
			return 1;
		}
		if (compare(endTime,that.startTime) < 0) {
			return -1;
		}
		return 0;
	}
	
	public int getTMS(Time t){// get the total of MS in the time
		return (t.getMS() + (t.getSS()*1000) + (t.getMM()*1000*60) + (t.getHH()*1000*60*60));
	}
	public void setTMS(Time t,int TMS){// set the total of MS in the time
		t.setHH(TMS/1000/60/60);
		TMS -= t.getHH()*1000*60*60;
		t.setMM(TMS/1000/60);
		TMS -= t.getMM()*1000*60;
		t.setSS(TMS/1000);
		TMS -= t.getSS()*1000;
		t.setMS(TMS);
	}
	public int compare(Time s,Time e){ // return 0 when equal, -1 when smaller than parameter, 1 when bigger than parameter.
		int OT = getTMS(s);
		int PT = getTMS(e); 
		if(OT > PT){
			return 1;
		}
		else if(OT < PT){
			return -1;
		}
		else
			return 0;
	}
}
