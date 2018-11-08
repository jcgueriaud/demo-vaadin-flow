package lu.lusis.demo.ui.dataprovider;

import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.function.SerializableBiFunction;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.shared.Registration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Nice example of a lazy spring data provider:
 * See his thread:  https://vaadin.com/forum/thread/16031323
 *
 * @param <T>
 * @param <ID>
 */
public class ExampleFilterDataProvider<T, ID extends Serializable> implements ConfigurableFilterDataProvider<T, T, T> {
    private final JpaRepository<T, ID> repository;
    private final ExampleMatcher matcher;
    private final List<QuerySortOrder> defaultSort;
    private final ConfigurableFilterDataProvider<T, T, T> delegate;


    /**
     * Default constructor that:
     * - Sort by default id ASC
     * - remove id to Example matcher
     * - filter "contains" to all String attributes
     *
     * Used for demo purpose
     *
     * @param repository
     */
    public ExampleFilterDataProvider(JpaRepository<T, ID> repository) {
        this.repository = repository;
        this.matcher =  ExampleMatcher.matching().withIgnorePaths("id","countryCode")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues();
        defaultSort = new ArrayList<>();
        defaultSort.add(new QuerySortOrder("id", SortDirection.ASCENDING));

        delegate = buildDataProvider();
    }


    public ExampleFilterDataProvider(JpaRepository<T, ID> repository,
                                     ExampleMatcher matcher,
                                     List<QuerySortOrder> defaultSort) {
        this.repository = repository;
        this.matcher = matcher;
        this.defaultSort = defaultSort;

        delegate = buildDataProvider();
    }

    private ConfigurableFilterDataProvider<T, T, T> buildDataProvider() {
        CallbackDataProvider<T, T> dataProvider = DataProvider.fromFilteringCallbacks(
                q -> q.getFilter()
                        .map(document -> repository.findAll(buildExample(document), ChunkRequest.of(q, defaultSort)).getContent())
                        .orElseGet(() -> repository.findAll(ChunkRequest.of(q, defaultSort)).getContent())
                        .stream(),
                q -> Math.toIntExact(q
                        .getFilter()
                        .map(document -> repository.count(buildExample(document)))
                        .orElseGet(repository::count)));
        return dataProvider.withConfigurableFilter((q, c) -> c);
    }

    private Example<T> buildExample(T probe) {
        return Example.of(probe, matcher);
    }

    @Override
    public void setFilter(T filter) {
        delegate.setFilter(filter);
    }

    @Override
    public boolean isInMemory() {
        return delegate.isInMemory();
    }

    @Override
    public int size(Query<T, T> query) {
        return delegate.size(query);
    }

    @Override
    public Stream<T> fetch(Query<T, T> query) {
        return delegate.fetch(query);
    }

    @Override
    public void refreshItem(T item) {
        delegate.refreshItem(item);
    }

    @Override
    public void refreshAll() {
        delegate.refreshAll();
    }

    @Override
    public Object getId(T item) {
        return delegate.getId(item);
    }

    @Override
    public Registration addDataProviderListener(DataProviderListener<T> listener) {
        return delegate.addDataProviderListener(listener);
    }

    @Override
    public <C> DataProvider<T, C> withConvertedFilter(SerializableFunction<C, T> filterConverter) {
        return delegate.withConvertedFilter(filterConverter);
    }

    @Override
    public <Q, C> ConfigurableFilterDataProvider<T, Q, C> withConfigurableFilter(SerializableBiFunction<Q, C, T> filterCombiner) {
        return delegate.withConfigurableFilter(filterCombiner);
    }

    @Override
    public ConfigurableFilterDataProvider<T, Void, T> withConfigurableFilter() {
        return delegate.withConfigurableFilter();
    }

    private static class ChunkRequest implements Pageable {
        public static <T> ChunkRequest of(Query<T, T> q, List<QuerySortOrder> defaultSort) {
            return new ChunkRequest(q.getOffset(), q.getLimit(), mapSort(q.getSortOrders(), defaultSort));
        }

        private static Sort mapSort(List<QuerySortOrder> sortOrders, List<QuerySortOrder> defaultSort) {
            if (sortOrders == null || sortOrders.isEmpty()) {
                return new Sort(mapSortCriteria(defaultSort));
            } else {
                return new Sort(mapSortCriteria(sortOrders));
            }
        }

        private static Sort.Order[] mapSortCriteria(List<QuerySortOrder> sortOrders) {
            return sortOrders.stream()
                    .map(s -> new Sort.Order(s.getDirection() == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, s.getSorted()))
                    .toArray(Sort.Order[]::new);
        }

        private final Sort sort;
        private int limit = 0;
        private int offset = 0;

        private ChunkRequest(int offset, int limit, Sort sort) {
            this.sort = sort;
            this.offset = offset;
            this.limit = limit;
        }

        @Override
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return limit;
        }

        @Override
        public long getOffset() {
            return offset;
        }

        @Override
        public Sort getSort() {
            return sort;
        }

        @Override
        public Pageable next() {
            return null;
        }

        @Override
        public Pageable previousOrFirst() {
            return this;
        }

        @Override
        public Pageable first() {
            return this;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }
    }
}
