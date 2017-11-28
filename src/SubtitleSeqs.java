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
				if(compare(list.retrieve(),st) < 0) {
					list.findNext();
					}
				else if(compare(list.retrieve(),st) == 0){
					return;
				}
				else{
					list.insertbefore(st);
					added = true;
					break;
				}
			}
			if(!added){
				if(compare(list.retrieve(),st) < 0) {
					added = true;
					list.insert(st);
				}
				else if(compare(list.retrieve(),st) == 0){
					return;
				}
				else{
					added = true;
					list.insertbefore(st);
				}
			}
		}
		else {
			list.insert(st);
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
				Time startTime = (list.retrieve()).getStartTime();
				Time endTime = (list.retrieve()).getEndTime();
				// check if the given time less than current_end_time and greater than current_start_time
				if(compare(startTime,time) <= 0 && compare(endTime,time) >= 0){
					return list.retrieve();
				}
				list.findNext();
			} 
			// check for the last element in the list ^.^
			Time startTime = (list.retrieve()).getStartTime();
			Time endTime = (list.retrieve()).getEndTime();
			// check if the given time less than current_end_time and greater than current_start_time
			if(compare(startTime,time) <= 0 && compare(endTime,time) >= 0){
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
				if(compare(list.retrieve().getEndTime(),startTime) >= 0){ // inside the interval
					while(compare(list.retrieve().getStartTime(),endTime) <= 0){
						
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
				shift(list.retrieve(),offset);// call the shift method in subtitle class
				if(getTMS(list.retrieve().getStartTime())< 0)
					list.retrieve().setStartTime(new Times());
				if(getTMS(list.retrieve().getEndTime()) <= 0) // check if we need to remove the subtitle
					list.remove();
				else
					list.findNext();
			}
			shift(list.retrieve(),offset); // call the shift method in subtitle class
			if(getTMS(list.retrieve().getEndTime()) <= 0) // check if we need to remove the subtitle
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
		int TMS = getTMS(endTime) - getTMS(startTime);
		
		if(!list.empty()){
			boolean need_shift = true;
			list.findFirst();
			while(!list.last()){
				//System.out.println("end.compare(start) = " + list.retrieve().getEndTime().compare(startTime));
				if(compare(list.retrieve().getEndTime(),startTime) >= 0){// inside the interval
					while (compare(list.retrieve().getStartTime(),endTime) <= 0){
						
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
					shift(list.retrieve(),-TMS-1);
					list.findNext();
				}
				shift(list.retrieve(),-TMS-1);
			}
			
		}
	}
	
	public int compare(Subtitle h,Subtitle s){//compare the two subtitles by StartTime 
		if(compare(h.getStartTime(),s.getEndTime()) > 0){
			return 1; // bigger
		}
		else if(compare(s.getStartTime(),h.getEndTime()) < 0){
			return 0;
		}
		else
			return -1;
	}
	
	//method shift
	public void shift(Subtitle s,int offset){
		//System.out.println("shifting by " + offset + ", st = " + StartTime.getTMS() + ", et = " + EndTime.getTMS() + ", For :" + Text);
		setTMS(s.getStartTime(),getTMS(s.getStartTime()) + offset);
		setTMS(s.getEndTime(),getTMS(s.getEndTime()) + offset);
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

