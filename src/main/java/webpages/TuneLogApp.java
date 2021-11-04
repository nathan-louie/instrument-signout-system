package webpages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TuneLogApp
{
	private static final Logger log = LoggerFactory.getLogger(TuneLogApp.class);

	public static void main(String[] args)
	{
		SpringApplication.run(TuneLogApp.class, args);
	}
}
