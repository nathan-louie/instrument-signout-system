package tune.log.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class UploadedFileReader<T>
{
	private InputStream file;
	private Class clazz;

	/**
	 * Constructor for the CSV file parser.
	 * 
	 * @param file  the InputStream file to parse
	 * @param clazz the Class of the bean to convert into
	 */
	public UploadedFileReader(InputStream file, Class clazz)
	{
		this.file = file;
		this.clazz = clazz;
	}

	/**
	 * Parse the CSV file into the bean.
	 * 
	 * @return the list of beans
	 */
	public List<T> parse()
	{
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(file));
			List<T> list = new CsvToBeanBuilder<T>(reader).withType(clazz).withIgnoreLeadingWhiteSpace(true).build()
					.parse();

			reader.close();

			return list;
		} catch (Exception e) {
			return null;
		}
	}
}
