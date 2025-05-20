package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Carteira;
import br.com.lunacom.portal.domain.Tag;
import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import br.com.lunacom.portal.repository.AtivoRepository;
import br.com.lunacom.portal.repository.CarteiraRepository;
import br.com.lunacom.portal.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarteiraService {

    public static final String MSG_ATIVO_NAO_ENCONTRADO = "Ativo não encontrado então esse registro de carteira não poderá ser salvo";
    private final CarteiraRepository carteiraRepository;
    private final TagRepository tagRepository;
    private final AtivoRepository ativoRepository;

    public void salvarLista(List<CarteiraDto> carteiraDtos) {
        carteiraDtos.forEach(this::salvar);
    }

    private Carteira salvar(CarteiraDto item) {
        final Carteira carteiraExistente = carteiraRepository
                .findByAtivoCodigo(item.getCodigoAtivo())
                .orElse(new Carteira());

        final Carteira carteiraAtualizada = Carteira.builder()
                            .id(carteiraExistente.getId())
                            .ativo(this.getAtivo(item, carteiraExistente))
                            .precoPago(item.getPrecoPago())
                            .precoAtual(item.getPrecoAtual())
                            .valorizacao(item.getValorizacao())
                            .tags(getTags(item, carteiraExistente))
                            .quantidade(item.getQuantidade())
                            .totalInvestido(item.getTotalInvestido())
                            .totalAtualizado(item.getAtualizacao())
                            .tags(carteiraExistente.getTags())
                            .dataCompra(item.getDataCompra())
                            .build();
        return carteiraRepository.save(carteiraAtualizada);

    }

    private Set<Tag> getTags(CarteiraDto item, Carteira carteiraExistente) {
        final Tag novaTag = tagRepository.findByNomeAndTipo(item.getEstrategia(), "CA")
                .orElse(saltarNovaTag(item.getEstrategia()));
        carteiraExistente.getTags().add(novaTag);
        return carteiraExistente.getTags();
    }

    private Tag saltarNovaTag(String estrategia) {
        //@TODO Salvar nova tag
        return null;
    }

    private Ativo getAtivo(CarteiraDto item, Carteira carteiraExistente) {
        if (Objects.isNull(carteiraExistente.getAtivo())) {
            return ativoRepository
                    .findByCodigo(item.getCodigoAtivo())
                    .orElseThrow(() -> new RuntimeException(MSG_ATIVO_NAO_ENCONTRADO));
        }
        return carteiraExistente.getAtivo();
    }
}
