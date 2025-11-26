package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Integer> {

    Optional<Carteira> findByAtivoCodigo(String nome);

    @Modifying
    @Query("DELETE FROM Carteira c WHERE c.ativo.codigo IN :codigos")
    void deleteByAtivoCodigoIn(@Param("codigos") List<String> codigosAtivo);
}
