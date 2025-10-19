package br.com.lunacom.portal.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class MonitorRegraRequest extends GenericRequest {
    private Integer id;
    private String status;
}
