import java.io.*;


public class SubtitleSeqFactory {

	// Return an empty subtitles sequence 
	public static SubtitleSeq getSubtitleSeq() {
		SubtitleSeq s = new SubtitleSeq();
		return s;
	}

	// Load a subtitle sequence from an SRT file. If the file does not exist or
	// is corrupted (incorrect format), null is returned.
	public static SubtitleSeq loadSubtitleSeq(String fileName) {
		try {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		SubtitleSeq ss = new SubtitleSeq();
		String line;
		int counter =0; 		//using counter to count the lines we read in a single sequence
		int seqNumber=0;
		int subNumber=0;	 	//test usage
		Time ts = new Time(); 	//starting time
		Time te = new Time(); 	//ending time
		String text="";		 	//add subtitle text to it
		while((line = in.readLine()) != null)
		{
			if(counter ==0)
			{
				seqNumber++;
				subNumber=Integer.parseInt(line);  // saving the subtitle number for testing
				if(seqNumber!=subNumber)
					return null;
				counter++;
			}
			else if(counter ==1)
			{
				//setting starting time.
				ts = new Time();
				ts.setHH(Integer.parseInt(line.substring(0, 2)));
				ts.setMM(Integer.parseInt(line.substring(3, 5)));
				ts.setSS(Integer.parseInt(line.substring(6, 8)));
				ts.setMS(Integer.parseInt(line.substring(9, 12)));
				//setting ending time.
				te = new Time();
				te.setHH(Integer.parseInt(line.substring(17, 19)));
				te.setMM(Integer.parseInt(line.substring(20, 22)));
				te.setSS(Integer.parseInt(line.substring(23, 25)));
				te.setMS(Integer.parseInt(line.substring(26, 29)));
				
				counter++;
			}
			else if(counter==2)
			{
				text=line; 		//save first line of the subtitle	
				counter++;
			}
			else if(counter==3)
			{
				if(!line.equals("")) 		// check if there are more lines for the subtitle.
				{
					text+="\n"+line;
					continue;	
				}
				// after reading 1 subtitle add its info.
				Subtitle s = new Subtitle();
				s.setStartTime(ts);
				s.setEndTime(te);
				s.setText(text);
				ss.addSubtitle(s); 		// add the subtitle to subtitles group.
				counter++;
			}
			
			counter = counter %4;
		}
		//Extra step for last element.
		Subtitle s = new Subtitle();
		s.setStartTime(ts);
		s.setEndTime(te);
		s.setText(text);
		ss.addSubtitle(s); 		// add the subtitle to subtitles group.
		in.close();
		return ss;
		} catch(Exception e)
		{
			return null;
		}
	}
}
