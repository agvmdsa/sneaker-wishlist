package com.agvms.sneakerwishlist.usecase;

public interface QueryHandler<Q extends Query<R>, R> {

    R execute(Q query);
}
