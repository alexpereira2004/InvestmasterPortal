package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.repository.MovimentoCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovimentoCompraService {

    private final MovimentoCompraRepository repository;

    public MovimentoCompra salvar(MovimentoCompra movimentoCompra) {
        return repository.save(movimentoCompra);
    }
}
