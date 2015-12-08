package com.vladmihalcea.book.hpjp.hibernate.identifier.batch.concurrent.providers;

import com.vladmihalcea.book.hpjp.util.EntityProvider;

/**
 * <code>PostEntityProvider</code> -
 *
 * @author Vlad Mihalcea
 */
public abstract class PostEntityProvider<T> implements EntityProvider {

    private final Class<T> clazz;

    protected PostEntityProvider(Class<T> clazz) {
        this.clazz = clazz;
    }

    public abstract T newPost();

    @Override
    public Class<?>[] entities() {
        return new Class<?>[] {
            clazz
        };
    }
}
