package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.GenericEntity;
import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class GenericService<
        T extends GenericEntity<T>,
        R extends GenericRequest,
        S extends GenericSpecification> {

    private final GenericRepository<T> repository;
    private final DataUtil dataUtil;
    private final GenericSpecification specification;

    protected GenericService(GenericRepository<T> repository, DataUtil dataUtil, GenericSpecification specification) {
        this.repository = repository;
        this.dataUtil = dataUtil;
        this.specification = specification;
    }

    public T get(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Transactional
    public T update(T updated){
        T dbDomain = get(updated.getId());

        return repository.save(dbDomain);
    }

    @Transactional
    public T save(T newDomain){
        return repository.save(newDomain);
    }

    @Transactional
    public Page<T> pesquisarComPaginacao(R request, Pageable pageable) {
        specification.setDataUtil(this.dataUtil);
        specification.setRequest(request);
        return repository.findAll((Specification<T>) specification, pageable);
    }

    @Transactional
    public void deleteAll(List<Integer> list){
        repository.deleteAllById(list);
    }

}
