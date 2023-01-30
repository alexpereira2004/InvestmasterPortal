package br.com.lunacom.portal.service;

import br.com.lunacom.portal.converter.MovimentoVendaCsvRequestConverter;
import br.com.lunacom.portal.domain.MovimentoVenda;
import br.com.lunacom.portal.domain.request.MovimentoVendaCsvRequest;
import br.com.lunacom.portal.domain.request.MovimentoVendaRequest;
import br.com.lunacom.portal.repository.MovimentoVendaRepository;
import br.com.lunacom.portal.resource.v1.specification.MovimentoVendaSpecification;
import br.com.lunacom.portal.util.DataUtil;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovimentoVendaService {

    private final DataUtil dataUtil;
    private final MovimentoVendaRepository repository;
    private final MovimentoVendaCsvRequestConverter converter;

    public MovimentoVenda salvar(MovimentoVenda movimentoVenda) {
        return repository.save(movimentoVenda);
    }

    public void salvarCsv(String request) {
        final Reader reader = new StringReader(request);

        CsvToBean<MovimentoVendaCsvRequest> csvToBean = new CsvToBeanBuilder(reader)
                .withType(MovimentoVendaCsvRequest.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        final List<MovimentoVendaCsvRequest> parse = csvToBean.parse();
        final List<MovimentoVenda> movimentoVendaList = converter.encode(parse);
        repository.saveAll(movimentoVendaList);
    }

    public Page<MovimentoVenda> pesquisarComPaginacao(MovimentoVendaRequest request, Pageable pageable) {
        final MovimentoVendaSpecification specification = MovimentoVendaSpecification
                .builder()
                .dataUtil(dataUtil)
                .request(request)
                .build();
        return repository.findAll(specification, pageable);
    }

    public void removerTodos(List<Integer> list) {
        list.stream().forEach(e -> repository.deleteById(e));
    }
}
