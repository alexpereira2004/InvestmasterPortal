package br.com.lunacom.portal.service;

import br.com.lunacom.portal.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PessoaService {
    final private PessoaRepository repository;
}
