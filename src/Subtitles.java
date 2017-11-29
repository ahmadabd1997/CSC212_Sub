
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
	
	public String toString()
	{
		String s = ""+(Times)StartTime+"/"+(Times)EndTime+"/"+getText();
		return s;
	}


	
}
