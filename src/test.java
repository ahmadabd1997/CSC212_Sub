
public class test {

	public static void main(String[] args) {
		SubtitleSeq s = new SubtitleSeq();
		s= SubtitleSeqFactory.loadSubtitleSeq("winnie-the-pooh-2011.srt");
		s.getSubtitles().findFirst();
		//s.getSubtitles().findNext();
		//s.getSubtitles().findNext();
		//System.out.println(s.getSubtitles().retrieve().getText());
		//System.out.println(s.getSubtitles().retrieve().getStartTime().getSS());
		//System.out.println(s.getSubtitles().last());
		/*System.out.println(s.getSubtitles().retrieve().getText() + "\n" + s.getSubtitles().retrieve().getStartTime().getTMS());
		s.shift(500);
		System.out.println(s.getSubtitles().retrieve().getText() + "\n" + s.getSubtitles().retrieve().getStartTime().getTMS());
		*/
		Time st = new Time();
		st.setTMS(60000);
		Time et = new Time();
		et.setTMS(77078);
		
		s.cut(st, et);
		
		if(!s.getSubtitles().empty()){ // printing all subtitles and there Total MS (TMS)
			s.getSubtitles().findFirst();
			while(!s.getSubtitles().last()){
				System.out.println(s.getSubtitles().retrieve().getText() + "\n" + s.getSubtitles().retrieve().getStartTime().getTMS() + "-" + s.getSubtitles().retrieve().getEndTime().getTMS());
				s.getSubtitles().findNext();
			}
			System.out.println(s.getSubtitles().retrieve().getText() + "\n" + s.getSubtitles().retrieve().getStartTime().getTMS() + "-" + s.getSubtitles().retrieve().getEndTime().getTMS());
		}
	}

}
