package com.habr.dddoptimisticlocks.utils;

import com.habr.dddoptimisticlocks.ddd.AggregateMember;
import com.habr.dddoptimisticlocks.ddd.AggregateRoot;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OptimisticLockingUtils {

    /**
     * Resolves the class of the aggregate root from the given aggregate member. Example:
     * <pre>
     *     {@code
     *     class Order implements AggregateRoot<Integer> {
     *         //...
     *     }
     *     class Milestone implements AggregateMember<Integer, Order> {
     *         //...
     *     }
     *
     *     Milestone milestone = new Milestone();
     *     var shipmentClass = OptimisticLockingUtils.resolveClass(milestone); // returns Order.class
     *     }
     * </pre>
     *
     * @param aggregateMember The aggregate member object instance
     * @param <ROOT_PK>       The type of the aggregate root ID
     * @param <T>             The type of the aggregate root
     * @return The class of the aggregate root
     */
    @SuppressWarnings("unchecked")
    public static <ROOT_PK extends Serializable, T extends AggregateRoot<ROOT_PK>> Class<T> resolveClass(
        AggregateMember<?, ?> aggregateMember) {

        var genericInterfaces = aggregateMember.getClass().getGenericInterfaces();

        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType parameterizedType
                && parameterizedType.getRawType().equals(AggregateMember.class)) {
                var actualTypeArguments = parameterizedType.getActualTypeArguments();

                for (Type actualTypeArgument : actualTypeArguments) {
                    if (actualTypeArgument instanceof Class<?> actualClass) {
                        if (AggregateRoot.class.isAssignableFrom(actualClass)) {
                            return (Class<T>) actualClass;
                        }
                    }
                }
            }
        }

        throw new RuntimeException("Cannot resolve class");
    }

}
