package com.ayanami.businesslogiclayer.game.model;

public enum SHIP {
    BASKET("/com/ayanami/images/shopping_basket_skin.png", "/com/ayanami/images/shopping_basket_live.png"),
    BASKET1("/com/ayanami/images/basket-icon_34490.png", "/com/ayanami/images/basket-life.png");
    String urlShip;
    String urlLife;

    private SHIP(String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrl() {
        return this.urlShip;
    }

    public String getUrlLife() {
        return urlLife;
    }

}