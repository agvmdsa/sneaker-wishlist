package com.agvms.sneakerwishlist.scheduler;

import com.agvms.sneakerwishlist.usecase.wishlist.command.CheckWantedItemPricesCommand;
import com.agvms.sneakerwishlist.usecase.wishlist.command.CheckWantedItemPricesUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceCheckScheduler {

    private final CheckWantedItemPricesUseCase checkWantedItemPricesUseCase;

    public PriceCheckScheduler(CheckWantedItemPricesUseCase checkWantedItemPricesUseCase) {
        this.checkWantedItemPricesUseCase = checkWantedItemPricesUseCase;
    }

    @Scheduled(cron = "${app.price-check.cron:0 0 3 * * MON}", zone = "America/Sao_Paulo")
    public void run() {
        checkWantedItemPricesUseCase.execute(new CheckWantedItemPricesCommand());
    }
}
