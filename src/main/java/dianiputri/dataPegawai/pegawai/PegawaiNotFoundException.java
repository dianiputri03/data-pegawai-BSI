package dianiputri.dataPegawai.pegawai;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PegawaiNotFoundException extends RuntimeException{
    public PegawaiNotFoundException() {
        super("Pegawai not found");
    }
}
