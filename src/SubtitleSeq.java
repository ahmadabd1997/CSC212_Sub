// This interface represents a subtitle sequence.
public class SubtitleSeq {
	
	private LinkedList<Subtitle> list = new LinkedList<Subtitle>();
	
	// Add a subtitle.
	void addSubtitle(Subtitle st){
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				if(list.retrieve().getStartTime().compare(st.getStartTime()) < 0)
					list.findNext();
				else{
					list.insert(st);
					break;
				}
			}
		}
		else list.insert(st);
	}

	// Return all subtitles in their chronological order.
	List<Subtitle> getSubtitles(){
		return list;
	}

	// Return the subtitle displayed at the specified time, null if no 
	// subtitle is displayed.
	Subtitle getSubtitle(Time time){
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				Time startTime = list.retrieve().getStartTime();
				Time endTime = list.retrieve().getEndTime();
				// check if the given time less than current_end_time and greater than current_start_time
				if(startTime.compare(time) <= 0 && endTime.compare(time) >= 0){
					return list.retrieve();
				}
				list.findNext();
			} 
			// check for the last element in the list ^.^
			Time startTime = list.retrieve().getStartTime();
			Time endTime = list.retrieve().getEndTime();
			// check if the given time less than current_end_time and greater than current_start_time
			if(startTime.compare(time) <= 0 && endTime.compare(time) >= 0){
				return list.retrieve();
			}
		}
		return null;
	}

	// Return, in chronological order, all subtitles displayed between the
	// specified start and end times. The first element of this list is the
	// subtitle of which the display interval contains or otherwise comes
	// Immediately after startTime. The last element of this list is the
	// subtitle of which the display interval contains or otherwise comes
	// immediately before endTime.
	List<Subtitle> getSubtitles(Time startTime, Time endTime){
		LinkedList<Subtitle> tmplist = new LinkedList<Subtitle>();
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
			
			}
			
		}
		//return(List<Subtitle>) tmplist;
		return null;
	}

	// Return, in chronological order, all subtitles containing str as a
	// sub-string in their text.
	List<Subtitle> getSubtitles(String str);

	// Remove all subtitles containing str as a sub-string in their text.
	void remove(String str);

	// Replace str1 with str2 in all subtitles.
	void replace(String str1, String str2);

	// Shift the subtitles by offseting their start/end times with the specified
	// offset (in milliseconds). The value offset can be positive or negative.
	// Negative time is not allowed and must be replaced with 0. If the end time
	// becomes 0, the subtitle must be removed.
	void shift(int offset);

	// Cut all subtitles between the specified start and end times. The first
	// subtitle to be removed is the one for which the display interval contains
	// or otherwise comes immediately after startTime. The last subtitle to be
	// removed is the one for which the display interval contains or otherwise
	// comes immediately before endTime. The start and end times of all
	// subtitles must be adjusted to reflect the new time.
	void cut(Time startTime, Time endTime);
}

