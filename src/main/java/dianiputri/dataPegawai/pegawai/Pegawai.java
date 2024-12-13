package dianiputri.dataPegawai.pegawai;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record Pegawai(
        @Id Integer id,
        @NotEmpty String name,
        @Email String email,
        @NotNull LocalDate tgl_lahir,
        @NotEmpty String telp,
        @NotEmpty String posisi,
        @NotEmpty String role,
        @NotEmpty String grup,
        @NotEmpty String department,
        @NotEmpty String lokasi_kerja
) {
}



