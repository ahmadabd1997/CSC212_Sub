// This interface represents a subtitle sequence.
public class SubtitleSeqs implements SubtitleSeq{
	
	private LinkedList<Subtitle> list = new LinkedList<Subtitle>();
	
	// Add a subtitle. 
	// ^.^ Done ^.^ it add the new Subtitle in the right place in the list so no need for sort method ^.^
	public void addSubtitle(Subtitle st){
		boolean added = false;
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				if(((Subtitles)list.retrieve()).compare((Subtitles)st) < 0) {
					list.findNext();
					}
				else if(((Subtitles)list.retrieve()).compare((Subtitles)st) == 0){
					return;
				}
				else{
					list.insertbefore((Subtitles)st);
					added = true;
					break;
				}
			}
			if(!added){
				if(((Subtitles)list.retrieve()).compare((Subtitles)st) < 0) {
					added = true;
					list.insert(st);
				}
				else if(((Subtitles)list.retrieve()).compare((Subtitles)st) == 0){
					return;
				}
				else{
					added = true;
					list.insertbefore((Subtitles)st);
				}
			}
		}
		else {
			list.insert((Subtitles)st);
		}
}
	// Return all subtitles in their chronological order.
	public List<Subtitle> getSubtitles(){
		return list;
	}

	// Return the subtitle displayed at the specified time, null if no 
	// subtitle is displayed.
	public Subtitle getSubtitle(Time time){
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				Time startTime = ((Subtitles)list.retrieve()).getStartTime();
				Time endTime = ((Subtitles)list.retrieve()).getEndTime();
				// check if the given time less than current_end_time and greater than current_start_time
				if(((Times)startTime).compare((Times)time) <= 0 && ((Times)endTime).compare((Times)time) >= 0){
					return list.retrieve();
				}
				list.findNext();
			} 
			// check for the last element in the list ^.^
			Time startTime = ((Subtitles)list.retrieve()).getStartTime();
			Time endTime = ((Subtitles)list.retrieve()).getEndTime();
			// check if the given time less than current_end_time and greater than current_start_time
			if(((Times)startTime).compare((Times)time) <= 0 && ((Times)endTime).compare((Times)time) >= 0){
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
	public List<Subtitle> getSubtitles(Time startTime, Time endTime){ // need check !!
		LinkedList<Subtitle> tmplist = new LinkedList<Subtitle>();
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				if(((Times)list.retrieve().getEndTime()).compare((Times)startTime) >= 0){ // inside the interval
					while(((Times)list.retrieve().getStartTime()).compare((Times)endTime) <= 0){
						
						tmplist.insert(list.retrieve());
						if(!list.last())
							list.findNext();
						else
							break;
					}
					break;
				}
				else{ // not inside the interval
					list.findNext();
				}
			}
		}
		return tmplist;
	}

	// Return, in chronological order, all subtitles containing str as a
	// sub-string in their text.
	public List<Subtitle> getSubtitles(String str)
	{
		LinkedList<Subtitle> l = new LinkedList<Subtitle>();
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				if(list.retrieve().getText().contains(str))
					l.insert(list.retrieve());
				list.findNext();
			}
			if(list.retrieve().getText().contains(str))
				l.insert(list.retrieve());
		}
		return l;
	}

	// Remove all subtitles containing str as a sub-string in their text.
	public void remove(String str) {
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				if(list.retrieve().getText().contains(str))
					list.remove();
				else		//needed else because remove method already moving to next
					list.findNext();
			}
			if(list.retrieve().getText().contains(str))
				list.remove();
		}
	}

	// Replace str1 with str2 in all subtitles.
	public void replace(String str1, String str2) {
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				if(list.retrieve().getText().contains(str1))
					list.retrieve().setText(list.retrieve().getText().replaceAll(str1,str2));
				list.findNext();
			}
			if(list.retrieve().getText().contains(str1))
				list.retrieve().setText(list.retrieve().getText().replaceAll(str1,str2));
		}
	}

	// Shift the subtitles by offseting their start/end times with the specified
	// offset (in milliseconds). The value offset can be positive or negative.
	// Negative time is not allowed and must be replaced with 0. If the end time
	// becomes 0, the subtitle must be removed.
	public void shift(int offset) {
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				((Subtitles)list.retrieve()).shift(offset);// call the shift method in subtitle class
				if(((Times)list.retrieve().getStartTime()).getTMS()< 0)
					list.retrieve().setStartTime(new Times());
				if(((Times)list.retrieve().getEndTime()).getTMS() <= 0) // check if we need to remove the subtitle
					list.remove();
				else
					list.findNext();
			}
			((Subtitles)list.retrieve()).shift(offset); // call the shift method in subtitle class
			if(((Times)list.retrieve().getEndTime()).getTMS() <= 0) // check if we need to remove the subtitle
				list.remove();
		}
	}

	// Cut all subtitles between the specified start and end times. The first
	// subtitle to be removed is the one for which the display interval contains
	// or otherwise comes immediately after startTime. The last subtitle to be
	// removed is the one for which the display interval contains or otherwise
	// comes immediately before endTime. The start and end times of all
	// subtitles must be adjusted to reflect the new time.
	public void cut(Time startTime, Time endTime) {
		int TMS = ((Times)endTime).getTMS() - ((Times)startTime).getTMS();
		
		if(!list.empty()){
			boolean need_shift = true;
			list.findFirst();
			while(!list.last()){
				//System.out.println("end.compare(start) = " + list.retrieve().getEndTime().compare(startTime));
				if(((Times)list.retrieve().getEndTime()).compare((Times)startTime) >= 0){// inside the interval
					while(((Times)list.retrieve().getStartTime()).compare((Times)endTime) <= 0){
						
						if(list.last()){// if the endTime is bigger than the last subtitle
							list.remove();
							need_shift = false;
							break;
						}
						else
							list.remove();
					}
					break;
				}
				else{ // not inside the interval
					list.findNext();
				}
			}
			if(need_shift){
				while(!list.last()){
					((Subtitles)list.retrieve()).shift(-TMS-1);
					list.findNext();
				}
				((Subtitles)list.retrieve()).shift(-TMS-1);
			}
			
		}
	}
	public String toString(){
		String s="The Sequence: \n";
		if(!list.empty()){
			list.findFirst();
			while(!list.last()){
				s+="\n" +list.retrieve().toString();
				list.findNext();
				
			}
			s+="\n" +list.retrieve().toString();
		}
		return s;
	}

}

