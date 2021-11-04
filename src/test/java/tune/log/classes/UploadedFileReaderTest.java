package tune.log.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UploadedFileReaderTest
{
	@Test
	void testParseOne()
	{
		try {
			InputStream is = IOUtils
					.toInputStream(
							"studentId,password,firstName,lastName\n" + "101159366,198148,Menachem,Magana\n"
									+ "199358208,762095,Olivia,Diaz\n" + "744710617,051457,Lenny,Dunn\n"
									+ "292258518,696132,Maison,Rosario\n" + "479407712,600181,Ruben,Sheppard\n"
									+ "621846129,012535,Thea,Mullins\n" + "874473897,368327,Lexi,Tran\n"
									+ "583798532,066433,Safwan,Whitley\n" + "408545124,758334,Sannah,Amos\n"
									+ "278242949,490412,Kofi,Woods\n" + "401589944,579420,Hadassah,Hassan\n"
									+ "143301695,131389,Leah,Bullock\n" + "581011792,123894,Izabelle,Delacruz",
							"UTF-8");
			UploadedFileReader<Student> ufr = new UploadedFileReader<Student>(is, Student.class);
			List<Student> list = ufr.parse();
			int result = list.size();
			int expected = 13;
			assertEquals(expected, result);

		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0, 1);
		}
	}

	@Test
	void testParseTwo()
	{
		try {
			InputStream is = IOUtils.toInputStream("studentId,password,firstName,lastName\n", "UTF-8");
			UploadedFileReader<Student> ufr = new UploadedFileReader<Student>(is, Student.class);
			List<Student> list = ufr.parse();
			int result = list.size();
			int expected = 0;
			assertEquals(expected, result);

		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0, 1);
		}
	}
}
