package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Aporte;
import br.com.lunacom.portal.repository.AporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AporteService {

    private final AporteRepository repository;

    public Optional<LocalDate> pesquisarUltimaDataCompra() {
        return repository.findFirstByOrderByDataAporteDesc()
                .map(Aporte::getDataAporte);
    }

    @Transactional
    public void removerUltimosAportes(Optional<LocalDate> dataUltimoAporte) {
        dataUltimoAporte.ifPresent(repository::deleteByDataAporteGreaterThanEqual);
    }

    public Aporte salvar(Aporte i) {
        return repository.save(i);
    }
}
