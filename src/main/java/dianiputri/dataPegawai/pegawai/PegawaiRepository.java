package dianiputri.dataPegawai.pegawai;

import org.springframework.data.repository.ListCrudRepository;
import java.util.List;
public interface PegawaiRepository extends ListCrudRepository<Pegawai, Integer> {
    List<Pegawai> findAllByName(String name);
}
