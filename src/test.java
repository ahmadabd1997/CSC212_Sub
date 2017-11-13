
public class test {

	public static void main(String[] args) {
		SubtitleSeq s = new SubtitleSeq();
		s= SubtitleSeqFactory.loadSubtitleSeq("winnie-the-pooh-2011.srt");
//		s.getSubtitles().findFirst();
		//System.out.println(s.getSubtitles().retrieve().getText());
		System.out.println(s.getSubtitles().retrieve().getStartTime().getSS());
	}

}
