
public class Subtitles implements Subtitle{
	
	private Time StartTime;
	private Time EndTime;
	private String Text;
	
	public Subtitles(){
		StartTime = new Times();
		EndTime = new Times();
		Text="";
	}
	public Subtitles(Time st,Time et,String t){
		StartTime = st;
		EndTime = et;
		Text = t;
	}
	public Time getStartTime() {
		return StartTime;
	}
	public void setStartTime(Time startTime) {
		StartTime = startTime;
	}
	public Time getEndTime() {
		return EndTime;
	}
	public void setEndTime(Time endTime) {
		EndTime = endTime;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	
	public int compare(Subtitles s){//compare the two subtitles by StartTime 
		if(compare(this.StartTime,s.EndTime) > 0){
			return 1; // bigger
		}
		else if(compare(s.StartTime,this.EndTime) < 0){
			return 0;
		}
		else
			return -1;
	}
	
	//method shift
	public void shift(int offset){
		//System.out.println("shifting by " + offset + ", st = " + StartTime.getTMS() + ", et = " + EndTime.getTMS() + ", For :" + Text);
		setTMS(this.StartTime,getTMS(this.StartTime) + offset);
		setTMS(this.EndTime,getTMS(this.EndTime) + offset);
	}
	
	public int compare(Time h,Time t){ // return 0 when equal, -1 when smaller than parameter, 1 when bigger than parameter.
		int OT = getTMS(h);
		int PT = getTMS(t); 
		if(OT > PT){
			return 1;
		}
		else if(OT < PT){
			return -1;
		}
		else
			return 0;
	}
	
	public int getTMS(Time h){// get the total of MS in the time
		return (h.getMS() + (h.getSS()*1000) + (h.getMM()*1000*60) + (h.getHH()*1000*60*60));
	}
	
	public void setTMS(Time h,int TMS){// set the total of MS in the time
		h.setHH(TMS/1000/60/60);;
		TMS -= h.getHH()*1000*60*60;
		h.setMM(TMS/1000/60);
		TMS -= h.getMM()*1000*60;
		h.setSS(TMS/1000);
		TMS -= h.getSS()*1000;
		h.setMS(TMS);
	}
	
	
	public String toString()
	{
		String s = "StartTime: "+StartTime+", EndTime: "+EndTime+", Text: "+Text;
		return s;
	}

	
}
