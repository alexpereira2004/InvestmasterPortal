package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.domain.request.MonitorRegraRequest;
import br.com.lunacom.portal.service.AtivoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Component
public class MonitorRegraRequestConverter extends GenericConverter<MonitorRegraRequest, MonitorRegra> {

    AtivoService ativoService;

    @Override
    public MonitorRegra encode(MonitorRegraRequest input) {
        final Ativo ativo = ativoService
                .pesquisarPorCodigo(input.getAtivoCodigo())
                .orElseThrow(() -> new EntityNotFoundException("Ativo informado não existe"));
        return MonitorRegra.builder()
                .id(input.getId())
                .ativo(ativo)
                .prioridade(input.getPrioridade())
                .status(input.getStatus().getCodigo())
                .build();
    }

    @Override
    public MonitorRegraRequest decode(MonitorRegra input) {
        return null;
    }
}
