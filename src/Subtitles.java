
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
		if(((Times)this.StartTime).compare((Times)s.EndTime) > 0){
			return 1; // bigger
		}
		else if(((Times)s.StartTime).compare((Times)EndTime) < 0){
			return 0;
		}
		else
			return -1;
	}
	
	//method shift
	public void shift(int offset){
		//System.out.println("shifting by " + offset + ", st = " + StartTime.getTMS() + ", et = " + EndTime.getTMS() + ", For :" + Text);
		((Times)StartTime).setTMS(((Times)StartTime).getTMS() + offset);
		((Times)EndTime).setTMS(((Times)EndTime).getTMS() + offset);
	}
	
	public String toString()
	{
		String s = "StartTime: "+StartTime+", EndTime: "+EndTime+", Text: "+Text;
		return s;
	}

	
}
