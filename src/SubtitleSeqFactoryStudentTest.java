import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SubtitleSeqFactoryStudentTest {

	class TimeSt implements Time {
		private int hh;
		private int mm;
		private int ss;
		private int ms;

		public TimeSt(int hh, int mm, int ss, int ms) {
			this.hh = hh;
			this.mm = mm;
			this.ss = ss;
			this.ms = ms;
		}

		public TimeSt(String hh, String mm, String ss, String ms) {
			this.hh = Integer.parseInt(hh);
			this.mm = Integer.parseInt(mm);
			this.ss = Integer.parseInt(ss);
			this.ms = Integer.parseInt(ms);
		}

		@Override
		public int getHH() {
			return hh;
		}

		@Override
		public int getMM() {
			return mm;
		}

		@Override
		public int getSS() {
			return ss;
		}

		@Override
		public int getMS() {
			return ms;
		}

		@Override
		public void setHH(int hh) {
			this.hh = hh;
		}

		@Override
		public void setMM(int mm) {
			this.mm = mm;
		}

		@Override
		public void setSS(int ss) {
			this.ss = ss;
		}

		@Override
		public void setMS(int ms) {
			this.ms = ms;
		}
	}

	class SubtitleSt implements Subtitle {
		private Time startTime;
		private Time endTime;
		private String text;

		public SubtitleSt(Time startTime, Time endTime, String text) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.text = text;
		}

		@Override
		public Time getStartTime() {
			return startTime;
		}

		@Override
		public Time getEndTime() {
			return endTime;
		}

		@Override
		public String getText() {
			return text;
		}

		@Override
		public void setStartTime(Time startTime) {
			this.startTime = startTime;
		}

		@Override
		public void setEndTime(Time endTime) {
			this.endTime = endTime;
		}

		@Override
		public void setText(String text) {
			this.text = text;
		}
	}

	protected boolean areEqual(Time t1, Time t2) {
		if ((t1 == null) != (t2 == null)) {
			return false;
		}
		if (t1 == null) {
			return true;
		}

		return (t1.getHH() == t2.getHH()) && (t1.getMM() == t2.getMM()) && (t1.getSS() == t2.getSS()) && (t1.getMS() == t2.getMS());
	}

	protected boolean areEqual(Subtitle st1, Subtitle st2) {
		if ((st1 == null) != (st2 == null)) {
			return false;
		}
		if (st1 == null) {
			return true;
		}
		return areEqual(st1.getStartTime(), st2.getStartTime()) && areEqual(st1.getEndTime(), st2.getEndTime()) && st1.getText().equals(st2.getText());
	}

	protected boolean areEqual(List<Subtitle> l1, List<Subtitle> l2) {
		if (l1 == l2) {
			return true;
		}
		if ((l1 == null) || (l2 == null)) {
			return false;
		}
		if (l1.empty() != l2.empty()) {
			return false;
		}
		if (l1.empty()) {
			return true;
		}
		l1.findFirst();
		l2.findFirst();
		int cpt = 1;
		while (!l1.last() && !l2.last()) {
			if (!areEqual(l1.retrieve(), l2.retrieve())) {
				System.err.println("Lists differ at element " + cpt + " (starting from 1)");
				return false;
			}
			l1.findNext();
			l2.findNext();
			cpt++;
		}
		if (l1.last() != l2.last()) {
			System.err.println("Lists have different sizes");
			return false;
		}
		if (areEqual(l1.retrieve(), l2.retrieve())) {
			return true;
		} else {
			System.err.println("Lists differ at element " + cpt + " (starting from 1)");
			return false;
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	// #1 correct file -simple file
	@Test
	public void testLoad1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.loadSubtitleSeq("sample1Load.srt");

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "17", "040"), new TimeSt("00", "02", "18", "724"), "Monsieur Rom..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "25", "120"), new TimeSt("00", "02", "26", "531"), "Opar."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "27", "520"), new TimeSt("00", "02", "29", "409"), "INHALES DEEPLY"));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "29", "480"), new TimeSt("00", "02", "31", "721"), "We found it."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "40", "160"), new TimeSt("00", "02", "41", "446"), "Captain Moulle?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "46", "200"), new TimeSt("00", "02", "48", "089"), "Form your lines."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "48", "480"), new TimeSt("00", "02", "49", "527"), "(GUNS COCKING)"));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "55", "800"), new TimeSt("00", "02", "57", "609"), "Maxims ready, sir!"));

			assertNotNull("Method load(String) does not load a correct file", seq);
			assertTrue("Method load(String) not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// #2 wrong sequence number
	@Test
	public void testLoad2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.loadSubtitleSeq("sample2Load.srt");
			assertNull("Subtitle sequence number out of order", seq);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// #3 wrong format, missing -->
	@Test
	public void testLoad3() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.loadSubtitleSeq("sample3Load.srt");
			assertNull("incorrect file format", seq);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// #4 End time precede start time
	@Test
	public void testLoad4() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.loadSubtitleSeq("sample4Load.srt");
			assertNull("End time precede start time", seq);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddSubtitle1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt(0, 0, 1, 500), new TimeSt(0, 0, 3, 700), "Hello Pooh!"));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt(0, 0, 1, 500), new TimeSt(0, 0, 3, 700), "Hello Pooh!"));

			assertTrue("The method addSubtitle is not correctly implemented", areEqual(expected, seq.getSubtitles()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddSubtitle2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt(0, 0, 1, 500), new TimeSt(0, 0, 3, 700), "Hello Pooh!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt(0, 0, 2, 500), new TimeSt(0, 0, 5, 700), "Hello Pooh!"));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt(0, 0, 1, 500), new TimeSt(0, 0, 3, 700), "Hello Pooh!"));

			assertTrue("The method addSubtitle is not correctly implemented. Overlapping subtitles should not be added", areEqual(expected, seq.getSubtitles()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddSubtitle3() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt(0, 0, 1, 500), new TimeSt(0, 0, 3, 700), "Hello Pooh!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt(0, 0, 0, 500), new TimeSt(0, 0, 1, 200), "Hello Tigger!"));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt(0, 0, 0, 500), new TimeSt(0, 0, 1, 200), "Hello Tigger!"));
			expected.insert(new SubtitleSt(new TimeSt(0, 0, 1, 500), new TimeSt(0, 0, 3, 700), "Hello Pooh!"));

			assertTrue("The method addSubtitle is not correctly implemented. Subtitles must be ordered chronologically", areEqual(expected, seq.getSubtitles()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// time given is between start and end times of a subtitle
	@Test
	public void testGetSubtitlesTimeS1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "00", "35", "536"), new TimeSt("00", "00", "37", "746"), "[whistling]"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "03", "188"), new TimeSt("00", "01", "05", "732"), "[Winnie the Pooh theme song]"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "13", "407"), new TimeSt("00", "01", "16", "326"), "[male narrator] This could be\nthe room of any small boy."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "16", "577"), new TimeSt("00", "01", "18", "954"), "But, in fact, it's not."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "19", "079"), new TimeSt("00", "01", "24", "459"), "It is the room of one young boy\nin particular named Christopher Robin."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "24", "585"), new TimeSt("00", "01", "27", "713"), "Now, Christopher Robin\nhas a very active imagination,"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "27", "838"), new TimeSt("00", "01", "31", "425"), "not to mention the uncanny ability\nto collect things."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "31", "550"), new TimeSt("00", "01", "35", "053"), "Big things. Small things. Sticky things."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "35", "179"), new TimeSt("00", "01", "38", "974"), "But his favourite things\nare his stuffed animals."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "39", "308"), new TimeSt("00", "01", "41", "602"), "Ah! There they are now."));

			SubtitleSt expected = new SubtitleSt(new TimeSt("00", "01", "24", "585"), new TimeSt("00", "01", "27", "713"), "Now, Christopher Robin\nhas a very active imagination,");

			assertTrue("Method getSubtitle(Time) not working correctly", areEqual(expected, seq.getSubtitle(new TimeSt("00", "01", "25", "500"))));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// time given precedes the start time of the first subtitle
	@Test
	public void testGetSubtitlesTimeS2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "00", "35", "536"), new TimeSt("00", "00", "37", "746"), "[whistling]"));

			assertNull("Method getSubtitle(Time) not working correctly", seq.getSubtitle(new TimeSt("00", "00", "30", "536")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// time given follows the end time of the last subtitle
	@Test
	public void testGetSubtitlesTimeS3() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "31", "550"), new TimeSt("00", "01", "35", "053"), "Big things. Small things. Sticky things."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "35", "179"), new TimeSt("00", "01", "38", "974"), "But his favourite things\nare his stuffed animals."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "39", "308"), new TimeSt("00", "01", "41", "602"), "Ah! There they are now."));

			assertNull("Method getSubtitle(Time) not working correctly", seq.getSubtitle(new TimeSt("00", "01", "42", "602")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testShift1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "17", "040"), new TimeSt("00", "02", "18", "724"), "Monsieur Rom..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "25", "120"), new TimeSt("00", "02", "26", "531"), "Opar."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "27", "520"), new TimeSt("00", "02", "29", "409"), "INHALES DEEPLY"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "29", "480"), new TimeSt("00", "02", "31", "721"), "We found it."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "40", "160"), new TimeSt("00", "02", "41", "446"), "Captain Moulle?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "46", "200"), new TimeSt("00", "02", "48", "089"), "Form your lines."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "48", "480"), new TimeSt("00", "02", "49", "527"), "(GUNS COCKING)"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "55", "800"), new TimeSt("00", "02", "57", "609"), "Maxims ready, sir!"));
			seq.shift(1000);

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "18", "040"), new TimeSt("00", "02", "19", "724"), "Monsieur Rom..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "26", "120"), new TimeSt("00", "02", "27", "531"), "Opar."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "28", "520"), new TimeSt("00", "02", "30", "409"), "INHALES DEEPLY"));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "30", "480"), new TimeSt("00", "02", "32", "721"), "We found it."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "41", "160"), new TimeSt("00", "02", "42", "446"), "Captain Moulle?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "47", "200"), new TimeSt("00", "02", "49", "089"), "Form your lines."));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "49", "480"), new TimeSt("00", "02", "50", "527"), "(GUNS COCKING)"));
			expected.insert(new SubtitleSt(new TimeSt("00", "02", "56", "800"), new TimeSt("00", "02", "58", "609"), "Maxims ready, sir!"));

			assertTrue("Method shift(int) not working with positive shift", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testShift2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "17", "040"), new TimeSt("00", "02", "18", "724"), "Monsieur Rom..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "25", "120"), new TimeSt("00", "02", "26", "531"), "Opar."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "27", "520"), new TimeSt("00", "02", "29", "409"), "INHALES DEEPLY"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "29", "480"), new TimeSt("00", "02", "31", "721"), "We found it."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "40", "160"), new TimeSt("00", "02", "41", "446"), "Captain Moulle?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "46", "200"), new TimeSt("00", "02", "48", "089"), "Form your lines."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "48", "480"), new TimeSt("00", "02", "49", "527"), "(GUNS COCKING)"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "02", "55", "800"), new TimeSt("00", "02", "57", "609"), "Maxims ready, sir!"));
			seq.shift(-150000);

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "00", "00", "000"), new TimeSt("00", "00", "01", "721"), "We found it."));
			expected.insert(new SubtitleSt(new TimeSt("00", "00", "10", "160"), new TimeSt("00", "00", "11", "446"), "Captain Moulle?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "00", "16", "200"), new TimeSt("00", "00", "18", "089"), "Form your lines."));
			expected.insert(new SubtitleSt(new TimeSt("00", "00", "18", "480"), new TimeSt("00", "00", "19", "527"), "(GUNS COCKING)"));
			expected.insert(new SubtitleSt(new TimeSt("00", "00", "25", "800"), new TimeSt("00", "00", "27", "609"), "Maxims ready, sir!"));

			assertTrue("Method shift(int) not working with negative shift", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemove1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "25", "889"), new TimeSt("00", "13", "30", "853"), "Pooh realized that a pine cone\n would not win him the honey."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "30", "936"), new TimeSt("00", "13", "31", "937"), "Why not?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "32", "062"), new TimeSt("00", "13", "34", "940"), "Because it would have to be\nsomething special."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "35", "023"), new TimeSt("00", "13", "39", "444"), "Oh. Special, you say. Special."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "39", "528"), new TimeSt("00", "13", "41", "446"), "Think, think, think."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "41", "530"), new TimeSt("00", "13", "44", "074"), "No hurry, Pooh. Just take your time."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "44", "199"), new TimeSt("00", "13", "48", "620"), "Take my time. What a wonderful idea."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "49", "705"), new TimeSt("00", "13", "51", "248"), "Cuckoo, cuckoo."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "13", "51", "331"), new TimeSt("00", "13", "54", "001"), "- Thanks, Pooh.\n- Congratulations."));

			seq.remove("time");

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "13", "25", "889"), new TimeSt("00", "13", "30", "853"), "Pooh realized that a pine cone\n would not win him the honey."));
			expected.insert(new SubtitleSt(new TimeSt("00", "13", "30", "936"), new TimeSt("00", "13", "31", "937"), "Why not?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "13", "32", "062"), new TimeSt("00", "13", "34", "940"), "Because it would have to be\nsomething special."));
			expected.insert(new SubtitleSt(new TimeSt("00", "13", "35", "023"), new TimeSt("00", "13", "39", "444"), "Oh. Special, you say. Special."));
			expected.insert(new SubtitleSt(new TimeSt("00", "13", "39", "528"), new TimeSt("00", "13", "41", "446"), "Think, think, think."));
			expected.insert(new SubtitleSt(new TimeSt("00", "13", "49", "705"), new TimeSt("00", "13", "51", "248"), "Cuckoo, cuckoo."));
			expected.insert(new SubtitleSt(new TimeSt("00", "13", "51", "331"), new TimeSt("00", "13", "54", "001"), "- Thanks, Pooh.\n- Congratulations."));

			assertTrue("Method remove(String) not working correctly", areEqual(expected, seq.getSubtitles()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRemove2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "00", "35", "536"), new TimeSt("00", "00", "37", "746"), "[whistling]"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "03", "188"), new TimeSt("00", "01", "05", "732"), "[Winnie the Pooh theme song]"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "13", "407"), new TimeSt("00", "01", "16", "326"), "[male narrator] This could be\nthe room of any small boy."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "16", "577"), new TimeSt("00", "01", "18", "954"), "But, in fact, it's not."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "19", "079"), new TimeSt("00", "01", "24", "459"), "It is the room of one young boy\nin particular named Christopher Robin."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "24", "585"), new TimeSt("00", "01", "27", "713"), "Now, Christopher Robin\nhas a very active imagination,"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "01", "27", "838"), new TimeSt("00", "01", "31", "425"), "not to mention the uncanny ability\nto collect things."));

			seq.remove("christopher robin");

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "00", "35", "536"), new TimeSt("00", "00", "37", "746"), "[whistling]"));
			expected.insert(new SubtitleSt(new TimeSt("00", "01", "03", "188"), new TimeSt("00", "01", "05", "732"), "[Winnie the Pooh theme song]"));
			expected.insert(new SubtitleSt(new TimeSt("00", "01", "13", "407"), new TimeSt("00", "01", "16", "326"), "[male narrator] This could be\nthe room of any small boy."));
			expected.insert(new SubtitleSt(new TimeSt("00", "01", "16", "577"), new TimeSt("00", "01", "18", "954"), "But, in fact, it's not."));
			expected.insert(new SubtitleSt(new TimeSt("00", "01", "19", "079"), new TimeSt("00", "01", "24", "459"), "It is the room of one young boy\nin particular named Christopher Robin."));
			expected.insert(new SubtitleSt(new TimeSt("00", "01", "24", "585"), new TimeSt("00", "01", "27", "713"), "Now, Christopher Robin\nhas a very active imagination,"));
			expected.insert(new SubtitleSt(new TimeSt("00", "01", "27", "838"), new TimeSt("00", "01", "31", "425"), "not to mention the uncanny ability\nto collect things."));

			assertTrue("Method remove(String) not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRemove3() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "14", "930"), new TimeSt("00", "09", "16", "014"), "Here you are, Pooh."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "18", "100"), new TimeSt("00", "09", "20", "310"), "Oh, and make sure everyone can see them."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "20", "394"), new TimeSt("00", "09", "22", "271"), "Oh, I will, Christopher."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "22", "354"), new TimeSt("00", "09", "26", "024"), "♪ Come one, come two, come all\nEveryone heed the call"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "26", "149"), new TimeSt("00", "09", "29", "570"), "♪ There's a very important thing to do"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "30", "821"), new TimeSt("00", "09", "32", "614"), "♪ Drop what you're doing and come"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "32", "698"), new TimeSt("00", "09", "34", "157"), "♪ Bumpity-bumpity-bum"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "34", "241"), new TimeSt("00", "09", "37", "452"), "♪ There's a very important thing to do"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "38", "537"), new TimeSt("00", "09", "42", "583"), "♪ It's time to gather 'round\nThe work has just begun"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "42", "666"), new TimeSt("00", "09", "44", "835"), "♪ And when it's done\nthen you'll have found"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "44", "918"), new TimeSt("00", "09", "46", "253"), "♪ That you have had some fun"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "46", "336"), new TimeSt("00", "09", "48", "797"), "♪ With a monumentuous, consequentuous"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "48", "922"), new TimeSt("00", "09", "51", "717"), "♪ Very important thing to do ♪"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "53", "510"), new TimeSt("00", "09", "54", "511"), "[grunts]"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "09", "57", "472"), new TimeSt("00", "09", "58", "849"), "There we are."));

			seq.remove("♪");

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "09", "14", "930"), new TimeSt("00", "09", "16", "014"), "Here you are, Pooh."));
			expected.insert(new SubtitleSt(new TimeSt("00", "09", "18", "100"), new TimeSt("00", "09", "20", "310"), "Oh, and make sure everyone can see them."));
			expected.insert(new SubtitleSt(new TimeSt("00", "09", "20", "394"), new TimeSt("00", "09", "22", "271"), "Oh, I will, Christopher."));
			expected.insert(new SubtitleSt(new TimeSt("00", "09", "53", "510"), new TimeSt("00", "09", "54", "511"), "[grunts]"));
			expected.insert(new SubtitleSt(new TimeSt("00", "09", "57", "472"), new TimeSt("00", "09", "58", "849"), "There we are."));

			assertTrue("Method remove(String) not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSubtitlesString1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "20", "340"), new TimeSt("00", "47", "23", "427"), "So it is with great, great honour\nand a humble heart,"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "23", "510"), new TimeSt("00", "47", "27", "764"), "that I bestow this pot of honey\nupon our dear friend..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "27", "848"), new TimeSt("00", "47", "29", "099"), "...B'loon."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "29", "183"), new TimeSt("00", "47", "30", "642"), "Wonderful idea, Rabbit."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "30", "726"), new TimeSt("00", "47", "33", "187"), "- Hooray!\n- Hooray, B'loon!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "33", "812"), new TimeSt("00", "47", "37", "274"), "- Thank you, B'loon. Goodbye.\n- Hooray!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "37", "357"), new TimeSt("00", "47", "41", "486"), "[narrator] Pooh watched as B'loon took\nthe honey pot higher and higher,"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "41", "570"), new TimeSt("00", "47", "46", "366"), "until it was completely out of sight."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "46", "450"), new TimeSt("00", "47", "49", "203"), "Oh, bother."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "55", "334"), new TimeSt("00", "47", "57", "586"), "Sorry, Pooh."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "47", "57", "669"), new TimeSt("00", "48", "02", "508"), "Ever have one of those days\nwhere you just can't win, Eeyore?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "48", "02", "591"), new TimeSt("00", "48", "06", "220"), "Yep. I know how you feel."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "48", "10", "474"), new TimeSt("00", "48", "12", "684"), "- [rumbling]\n- Oh, yes."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "48", "12", "768"), new TimeSt("00", "48", "15", "062"), "I simply must find some honey."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "48", "15", "145"), new TimeSt("00", "48", "18", "315"), "[narrator] As Pooh continued searching,\nhe got hungrier..."));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "47", "23", "510"), new TimeSt("00", "47", "27", "764"), "that I bestow this pot of honey\nupon our dear friend..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "47", "27", "848"), new TimeSt("00", "47", "29", "099"), "...B'loon."));
			expected.insert(new SubtitleSt(new TimeSt("00", "48", "15", "145"), new TimeSt("00", "48", "18", "315"), "[narrator] As Pooh continued searching,\nhe got hungrier..."));

			assertTrue("Method getSubtitles(String) not working correctly", areEqual(expected, seq.getSubtitles("...")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSubtitlesString2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();

			expected.insert(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));

			assertTrue("Method getSubtitles(String) not working correctly", areEqual(expected, seq.getSubtitles("a Backson? ")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReplace1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));
			seq.replace("Backson", "Bigfoot");

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Bigfoot!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Bigfoot?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Bigfoot? What on earth is a Bigfoot?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Bigfoot? "));

			assertTrue("Method replace not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReplace2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));
			seq.replace("that\ncoming", "that\ngoing");

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ngoing through the bushes was..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));

			assertTrue("Method replace not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSubtitlesTimes1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));

			assertTrue("Method getSubtitles(fromTime,toTime) not working correctly", areEqual(expected, seq.getSubtitles(new TimeSt("00", "00", "00", "000"), new TimeSt("00", "50", "00", "000"))));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSubtitlesTimes2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();

			assertTrue("Method getSubtitles(fromTime,toTime) not working correctly", areEqual(expected, seq.getSubtitles(new TimeSt("00", "00", "00", "000"), new TimeSt("00", "30", "00", "000"))));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSubtitlesTimes3() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "11", "230"), new TimeSt("00", "46", "14", "149"), "Backson? What on earth is a Backson?"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "14", "233"), new TimeSt("00", "46", "15", "484"), "He wakes up babies!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "15", "567"), new TimeSt("00", "46", "16", "944"), "He swipes your stripes!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "17", "027"), new TimeSt("00", "46", "18", "570"), "He puts out the lights!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "18", "654"), new TimeSt("00", "46", "22", "199"), "Yes, and we thought he took you from us."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "22", "282"), new TimeSt("00", "46", "25", "911"), "What gave you the idea\nI was taken by a Backson? "));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "03", "722"), new TimeSt("00", "46", "06", "016"), "You found them, B'loon. Well done."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "06", "099"), new TimeSt("00", "46", "08", "894"), "It's Christopher Robin!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "08", "977"), new TimeSt("00", "46", "11", "146"), "How did you escape from the Backson?"));

			assertTrue("Method getSubtitles(fromTime,toTime) not working correctly", areEqual(expected, seq.getSubtitles(new TimeSt("00", "45", "59", "000"), new TimeSt("00", "46", "08", "977"))));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCut1() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.cut(new TimeSt("00", "45", "52", "000"), new TimeSt("00", "45", "54", "999"));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();

			expected.insert(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "52", "255"), new TimeSt("00", "45", "54", "549"), "[Christopher] Wait, everyone."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "55", "175"), new TimeSt("00", "45", "57", "135"), "It's only me."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "57", "219"), new TimeSt("00", "45", "59", "513"), "[all] Christopher Robin!"));

			assertTrue("Method cut not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCut2() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.cut(new TimeSt("00", "45", "51", "293"), new TimeSt("00", "45", "51", "376"));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "53", "628"), new TimeSt("00", "45", "55", "088"), "Back to the pit!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "55", "171"), new TimeSt("00", "45", "57", "465"), "[Christopher] Wait, everyone."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "58", "091"), new TimeSt("00", "46", "00", "051"), "It's only me."));
			expected.insert(new SubtitleSt(new TimeSt("00", "46", "00", "135"), new TimeSt("00", "46", "02", "429"), "[all] Christopher Robin!"));
			assertTrue("Method cut not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCut3() {
		try {
			SubtitleSeq seq = SubtitleSeqFactory.getSubtitleSeq();
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "45", "58", "175"), new TimeSt("00", "46", "00", "135"), "It's only me."));
			seq.addSubtitle(new SubtitleSt(new TimeSt("00", "46", "00", "219"), new TimeSt("00", "46", "02", "513"), "[all] Christopher Robin!"));
			seq.cut(new TimeSt("00", "45", "57", "600"), new TimeSt("00", "45", "58", "099"));

			LinkedList<Subtitle> expected = new LinkedList<Subtitle>();
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "48", "081"), new TimeSt("00", "45", "51", "293"), "But little did they know that\ncoming through the bushes was..."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "51", "376"), new TimeSt("00", "45", "53", "629"), "- [branches snapping]\n- [all gasp] Backson!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "53", "712"), new TimeSt("00", "45", "55", "172"), "Back to the pit!"));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "55", "255"), new TimeSt("00", "45", "57", "549"), "[Christopher] Wait, everyone."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "57", "675"), new TimeSt("00", "45", "59", "635"), "It's only me."));
			expected.insert(new SubtitleSt(new TimeSt("00", "45", "59", "719"), new TimeSt("00", "46", "02", "013"), "[all] Christopher Robin!"));

			assertTrue("Method cut not working correctly", areEqual(expected, seq.getSubtitles()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
