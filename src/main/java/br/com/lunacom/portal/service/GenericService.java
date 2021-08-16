package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.GenericEntity;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class GenericService<T extends GenericEntity<T>> {
    private final GenericRepository<T> repository;
    private final DataUtil dataUtil;

    protected GenericService(GenericRepository<T> repository, DataUtil dataUtil) {
        this.repository = repository;
        this.dataUtil = dataUtil;
    }

    public T salvar(T item) {
        return repository.save(item);
    }

//    public Page<T> pesquisarComPaginacao(MovimentoCompraRequest request, Pageable pageable) {
//        final MovimentoCompraSpecification specification = MovimentoCompraSpecification
//                .builder()
//                .dataUtil(dataUtil)
//                .request(request)
//                .build();
//        return repository.findAll(specification, pageable);
//    }

    public void removerTodos(List<Integer> list) {
        repository.deleteAllById(list);
    }

    public Page<T> getPage(Pageable pageable){
        return repository.findAll(pageable);
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
    public T create(T newDomain){
        return repository.save(newDomain);
    }

    @Transactional
    public void delete(Integer id){
        //check if object with this id exists
        get(id);
        repository.deleteById(id);
    }

}
