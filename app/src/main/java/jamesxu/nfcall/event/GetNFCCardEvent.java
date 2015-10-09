package jamesxu.nfcall.event;


public class GetNFCCardEvent {
    private String card;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public GetNFCCardEvent(String card) {
        this.card = card;
    }
}