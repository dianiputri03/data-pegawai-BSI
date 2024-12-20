package dianiputri.dataPegawai.pegawai;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/pegawais")


public class PegawaiController {

    private final PegawaiRepository pegawaiRepository;
    private final JdbcClientPegawaiRepository jdbcClientpegawaiRepository;

    @Autowired

    public PegawaiController(PegawaiRepository PegawaiRepository, JdbcClientPegawaiRepository jdbcClientpegawaiRepository) {
        this.pegawaiRepository = PegawaiRepository;
        this.jdbcClientpegawaiRepository = jdbcClientpegawaiRepository;

    }

    //find all
    @GetMapping("")
    List<Pegawai> findAll() {
        return jdbcClientpegawaiRepository.findAll();
    }

    //find by id
    @GetMapping("{id}")
    Pegawai findById(@PathVariable Integer id) {

        Optional<Pegawai> pegawai = pegawaiRepository.findById(id);
        if (pegawai.isEmpty()) {
            throw new PegawaiNotFoundException();
        }
        return pegawai.get();

    }

    //post
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody Pegawai pegawai) {
        jdbcClientpegawaiRepository.create(pegawai);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pegawai berhasil dibuat"); // Status 201 Created
    }

    //put
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Pegawai pegawai, @PathVariable Integer id) {
        Optional<Pegawai> existingPegawai = pegawaiRepository.findById(id);
        if (existingPegawai.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pegawai dengan ID " + id + " tidak ditemukan");
        }
        jdbcClientpegawaiRepository.update(pegawai, id);
        return ResponseEntity.status(HttpStatus.OK).body("Pegawai dengan ID " + id + " berhasil diperbarui");
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Pegawai> pegawai = pegawaiRepository.findById(id);
        if (pegawai.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pegawai dengan ID " + id + " tidak ditemukan");
        }
        pegawaiRepository.delete(pegawai.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pegawai dengan ID " + id + " berhasil dihapus");
    }

    //filter
    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String posisi,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String grup,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String lokasi_kerja) {
        List<Pegawai> result = jdbcClientpegawaiRepository.filterPegawai(posisi, role, grup, department, lokasi_kerja);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tidak ada pegawai yang sesuai dengan filter.");
        }

        return ResponseEntity.ok(result);
    }

    }


