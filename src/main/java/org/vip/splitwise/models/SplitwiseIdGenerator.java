package org.vip.splitwise.models;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;
import java.util.stream.Stream;

public class SplitwiseIdGenerator implements IdentifierGenerator {
    private String prefix;

    @Override
    public String generate(SharedSessionContractImplementor session, Object object) {
        String query = String.format("select %s from %s",
                session.getEntityPersister(object.getClass().getName(), object).getIdentifierPropertyName(),
                object.getClass().getSimpleName());
        Stream<String> ids = session.createQuery(query, String.class).stream();
        long maxId = ids.map(id -> id.replace(prefix, ""))
                .mapToLong(Long::parseLong)
                .max().orElse(0L);
        return prefix + (maxId + 1);
    }

    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) {
        this.prefix = parameters.getProperty("prefix");
    }
}
