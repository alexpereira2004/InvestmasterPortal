package br.com.lunacom.portal.service;

import br.com.lunacom.portal.repository.ProjecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjecaoService {
    final private ProjecaoRepository repository;
}
