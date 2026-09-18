package com.agvms.sneakerwishlist.usecase;

public interface CommandHandler<C extends Command, R> {

    R execute(C command);
}
