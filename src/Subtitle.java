
public class Subtitle {
	
	private Time StartTime;
	private Time EndTime;
	private String Text;
	
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
		return this.StartTime.compare(s.StartTime);
	}
	
}
