
public class Subtitle {
	
	private Time StartTime;
	private Time EndTime;
	private String Text;
	
	public Subtitle(){
		StartTime = new Time();
		EndTime = new Time();
		Text="";
	}
	public Subtitle(Time st,Time et,String t){
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
	
	public int compare(Subtitle s){//compare the two subtitles by StartTime 
		if(this.StartTime.compare(s.EndTime) > 0){
			return 1; // bigger
		}
		else if(s.StartTime.compare(EndTime) < 0){
			return 0;
		}
		else
			return -1;
	}
	
	//method shift
	public void shift(int offset){
		//System.out.println("shifting by " + offset + ", st = " + StartTime.getTMS() + ", et = " + EndTime.getTMS() + ", For :" + Text);
		StartTime.setTMS(StartTime.getTMS() + offset);
		EndTime.setTMS(EndTime.getTMS() + offset);
	}
	
	public String toString()
	{
		String s = "StartTime: "+StartTime+", EndTime: "+EndTime+", Text: "+Text;
		return s;
	}
	//search for sub-string
	
}
