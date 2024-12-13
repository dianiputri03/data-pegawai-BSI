package dianiputri.dataPegawai.pegawai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.state;


@Repository
public class JdbcClientPegawaiRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcClientPegawaiRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientPegawaiRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    //find all
    public List<Pegawai> findAll() {
        return jdbcClient.sql("SELECT * FROM pegawai")
                .query(Pegawai.class)
                .list();
    }

    //find by id
    public Optional<Pegawai> findById(Integer id) {
        return jdbcClient.sql("SELECT id, name, email, tgl_lahir, telp,  posisi, role, grup, department, lokasi_kerja FROM pegawai WHERE id = :id")
                .param("id", id)
                .query(Pegawai.class)
                .optional();

    }

    //create
    public void create(Pegawai pegawai) {
        var updated = jdbcClient.sql("INSERT INTO Pegawai (id,  name, email, tgl_lahir, telp, posisi, role, grup, department, lokasi_kerja) VALUES (?,?,?,?,?,?,?,?,?,?)")
                .params(List.of(pegawai.id(), pegawai.name(), pegawai.email(), pegawai.tgl_lahir(), pegawai.telp(), pegawai.posisi(), pegawai.role(), pegawai.grup(), pegawai.department(), pegawai.lokasi_kerja()))
                .update();
        Assert.state(updated == 1, "Failed to create pegawai" + pegawai.name());
    }

    //update
    public void update(Pegawai pegawai, Integer id) {
        var updated = jdbcClient.sql("update Pegawai set name = ?, email = ?, tgl_lahir = ?, telp = ?,  posisi = ? , role = ?, grup = ?, department = ?, lokasi_kerja = ? WHERE id = ?")
                .params(List.of(pegawai.name(), pegawai.email(), pegawai.tgl_lahir(), pegawai.telp(),  pegawai.posisi(), pegawai.role(), pegawai.grup(), pegawai.department(), pegawai.lokasi_kerja(), id))
                .update();
        Assert.state(updated == 1, "Failed to create pegawai" + pegawai.name());
    }

    //delete
    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM pegawai WHERE id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete pegawai");
    }

    public int count(){return jdbcClient.sql("select * from pegawai").query().listOfRows().size();}

    public void saveAll(List<Pegawai> pegawais) {
        pegawais.stream().forEach(this::create);
    }

    //filter
    public List<Pegawai> filterPegawai(String posisi, String role, String grup, String department, String lokasiKerja) {
        StringBuilder sql = new StringBuilder("SELECT * FROM pegawai WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (posisi != null && !posisi.isEmpty()) {
            sql.append(" AND posisi = ?");
            params.add(posisi);
        }

        if (role != null && !role.isEmpty()) {
            sql.append(" AND role = ?");
            params.add(role);
        }

        if (grup != null && !grup.isEmpty()) {
            sql.append(" AND grup = ?");
            params.add(grup);
        }

        if (department != null && !department.isEmpty()) {
            sql.append(" AND department = ?");
            params.add(department);
        }

        if (lokasiKerja != null && !lokasiKerja.isEmpty()) {
            sql.append(" AND lokasi_kerja = ?");
            params.add(lokasiKerja);
        }

        System.out.println("Executing query: " + sql.toString());
        System.out.println("With parameters: " + params);

        return jdbcClient.sql(sql.toString())
                .params(params)
                .query(Pegawai.class)
                .list();
    }
    }

