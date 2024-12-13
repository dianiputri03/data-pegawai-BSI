package dianiputri.dataPegawai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PegawaiApplication {

	private static final Logger log = LoggerFactory.getLogger(PegawaiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PegawaiApplication.class, args);
	}
}
