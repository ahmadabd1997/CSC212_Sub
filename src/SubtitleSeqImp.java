
public class SubtitleSeqImp implements SubtitleSeq {
	SortedBST<TimeInterval,Subtitle> SeqTree = new SortedBST<TimeInterval,Subtitle> ();
	// Add a subtitle.
		public void addSubtitle(Subtitle st) {
			if(!SeqTree.full()) {
				TimeInterval newTime = new TimeInterval(st.getStartTime(),st.getEndTime());
				SeqTree.insert(newTime, st);
			}
		};

		// Return all subtitles in their chronological order.
		public List<Subtitle> getSubtitles(){
			List<Subtitle> result = new LinkedList<Subtitle>();
			SeqTree.findFirst();
			while(!SeqTree.last()) {
				result.insert(SeqTree.retrieve().second);
				SeqTree.findNext();
			}
			result.insert(SeqTree.retrieve().second);
			return result;
		}

		// Return the subtitle displayed at the specified time, null if no
		// subtitle is displayed.
		public Subtitle getSubtitle(Time time) {
			SeqTree.findFirst();
			while(SeqTree.retrieve().first.find(time) != 0)
				if(!SeqTree.last())
					SeqTree.findNext();
				else
					break;
			if(SeqTree.retrieve().first.find(time) == 0)
				return SeqTree.retrieve().second;
			else
				return null;
			
		}

		// Return the number of nodes in the search path for finding the subtitle.
		public int nbNodesInSearchPath(Time c) {
			SeqTree.findFirst();
			TimeInterval tt = new TimeInterval(c,c) ;
			/*while(!SeqTree.last()) {
				if(SeqTree.retrieve().first.find(c) == 0)
					break;
				SeqTree.findNext();
			}
			if(SeqTree.retrieve().first.find(c) != 0)
				return 0;
				*/
			return SeqTree.nbNodesInSearchPath(tt);
		}

		// Return, in chronological order, all subtitles displayed between the
		// specified start and end times. The first element of this list is the
		// subtitle of which the display interval contains or otherwise comes
		// Immediately after startTime. The last element of this list is the
		// subtitle of which the display interval contains or otherwise comes
		// immediately before endTime.
		public List<Subtitle> getSubtitles(Time startTime, Time endTime){
			SeqTree.findFirst();
			List<Subtitle> result = new LinkedList<Subtitle>();
			while(!SeqTree.last()) { 
				if(SeqTree.retrieve().first.find(startTime) == -1 || SeqTree.retrieve().first.find(startTime) == 0)
					if(SeqTree.retrieve().first.find(endTime) != -1)
						result.insert(SeqTree.retrieve().second);
				SeqTree.findNext();
			}
			if(SeqTree.retrieve().first.find(startTime) == -1 || SeqTree.retrieve().first.find(startTime) == 0)
				if(SeqTree.retrieve().first.find(endTime) != -1)
					result.insert(SeqTree.retrieve().second);
			return result;
		}

		// Shift the subtitles by offseting their start/end times with the specified
		// offset (in milliseconds). The value offset can be positive or negative.
		// Negative time is not allowed and must be replaced with 0. If the end time
		// becomes 0, the subtitle must be removed.
		
		public void shift(int offset) {
			offset /= 2;
			if(!SeqTree.empty()) {
				SeqTree.findFirst();
				while(!SeqTree.last()) {
					TimeInterval k = SeqTree.retrieve().first;
					Subtitle T = SeqTree.retrieve().second;
					shift(T,offset);
					k.shift(offset);
					if(getTMS(SeqTree.retrieve().second.getEndTime()) < 0) {
						SeqTree.remove();
						continue;
					}
					if(getTMS(SeqTree.retrieve().second.getStartTime()) < 0) {
						setTMS(SeqTree.retrieve().second.getStartTime(),0);	
						k.setStartTime(new TimeTest(0,0,0,0));
					}
						
					SeqTree.updateKey(k);
					
					SeqTree.findNext();
				}
				TimeInterval k = SeqTree.retrieve().first;
				Subtitle T = SeqTree.retrieve().second;
				shift(T,offset);
				k.shift(offset);
				if(getTMS(SeqTree.retrieve().second.getEndTime()) < 0) {
					SeqTree.remove();
				}else {
					if(getTMS(SeqTree.retrieve().second.getStartTime()) < 0) {
						setTMS(SeqTree.retrieve().second.getStartTime(),0);	
						k.setStartTime(new TimeTest(0,0,0,0));
					}
					SeqTree.updateKey(k);
				}
				
			}
			
			
		}
		
		//Des
		public void shift(Subtitle s,int offset){
			//System.out.println("shifting by " + offset + ", st = " + StartTime.getTMS() + ", et = " + EndTime.getTMS() + ", For :" + Text);
			
			setTMS(s.getStartTime(),getTMS(s.getStartTime()) + offset);
			setTMS(s.getEndTime(),getTMS(s.getEndTime()) + offset);
			
				
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
