package br.com.lunacom.portal.service;

import br.com.lunacom.portal.converter.MovimentoCompraCsvRequestConverter;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.dto.MovimentoCompraCsvRequest;
import br.com.lunacom.portal.repository.MovimentoCompraRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovimentoCompraService {

    private final MovimentoCompraRepository repository;
    private final MovimentoCompraCsvRequestConverter converter;

    public MovimentoCompra salvar(MovimentoCompra movimentoCompra) {
        return repository.save(movimentoCompra);
    }

    public void salvarCsv(String request) {
        final Reader reader = new StringReader(request);

        CsvToBean<MovimentoCompraCsvRequest> csvToBean = new CsvToBeanBuilder(reader)
                .withType(MovimentoCompraCsvRequest.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        final List<MovimentoCompraCsvRequest> parse = csvToBean.parse();
        final List<MovimentoCompra> movimentoCompraList = converter.encode(parse);
        repository.saveAll(movimentoCompraList);
    }
}
