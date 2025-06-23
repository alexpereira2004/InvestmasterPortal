package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Projecao;
import br.com.lunacom.portal.domain.ResultadoDividendo;
import br.com.lunacom.portal.domain.enumeration.AcaoTipo;
import br.com.lunacom.portal.repository.ProjecaoRepository;
import br.com.lunacom.portal.repository.ResultadoDividendoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProjecaoService {
    final private ProjecaoRepository repository;
    final private ResultadoDividendoRepository resultadoDividendoRepository;

    public List<Integer> buscarTodosAnosComProjecao() {
        return repository.searchAllDistinctByAnos();
    }

    public List<Projecao> atualizarIndices() {
        final List<ResultadoDividendo> all = resultadoDividendoRepository.findAll();
        List<Projecao> projecoes = new ArrayList<>();
        all.forEach(e -> {
            final Projecao projecaoAcao = Projecao.builder()
                    .ano(e.getAno())
                    .mes(e.getMes())
                    .tipo(AcaoTipo.ACAO.getCodigo())
                    .valorAlcancado(e.getAcoes())
                    .totalizador(false)
                    .build();
            if (Objects.nonNull(e.getAcaoId())) {
                projecaoAcao.setId(e.getAcaoId());
                projecaoAcao.setDataAtualizacao(LocalDateTime.now());
            } else {
                projecaoAcao.setDataCriacao(LocalDateTime.now());
            }

            projecoes.add(projecaoAcao);

            final Projecao projecaoFii = Projecao.builder()
                    .ano(e.getAno())
                    .mes(e.getMes())
                    .tipo(AcaoTipo.FII.getCodigo())
                    .valorAlcancado(e.getFundos())
                    .totalizador(false)
                    .build();
            if (Objects.nonNull(e.getFundosId())) {
                projecaoFii.setId(e.getFundosId());
                projecaoFii.setDataAtualizacao(LocalDateTime.now());
            } else {
                projecaoFii.setDataCriacao(LocalDateTime.now());
            }
            projecoes.add(projecaoFii);

            final Projecao projecaoBdr = Projecao.builder()
                    .ano(e.getAno())
                    .mes(e.getMes())
                    .tipo(AcaoTipo.BDR.getCodigo())
                    .valorAlcancado(e.getOutros())
                    .totalizador(false)
                    .build();
            if (Objects.nonNull(e.getBdrId())) {
                projecaoBdr.setId(e.getBdrId());
                projecaoBdr.setDataAtualizacao(LocalDateTime.now());
            } else {
                projecaoBdr.setDataCriacao(LocalDateTime.now());
            }
            projecoes.add(projecaoBdr);

//            projecoes.add(
//                    Projecao.builder()
//                            .ano(Integer.parseInt(e.getAno().substring(0, 4)))
//                            .mes(Integer.parseInt(e.getAno().substring(5, 7)))
//                            .tipo(AcaoTipo.BDR.getCodigo())
//                            .valorAlcancado(e.getOutros())
//                            .totalizador(false)
//                            .build());
//
//            projecoes.add(
//                    Projecao.builder()
//                            .ano(Integer.parseInt(e.getAno().substring(0, 4)))
//                            .mes(Integer.parseInt(e.getAno().substring(5, 7)))
//                            .tipo(AcaoTipo.TOD.getCodigo())
//                            .valorAlcancado(e.getTotal())
//                            .totalizador(true)
//                            .build());

        });
        return repository.saveAll(projecoes);
    }
}
