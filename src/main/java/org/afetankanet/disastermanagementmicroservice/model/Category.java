package org.afetankanet.disastermanagementmicroservice.model;

public enum Category {
    GIYSI("Giysi"),
    YIYECEK("Yiyecek"),
    BARINAK("Barınak"),
    EGITIM("Eğitim"),
    SAGLIK("Sağlık"),
    TEMIZLIK("Temizlik Malzemeleri"),
    OYUNCAK("Oyuncak"),
    KIRTASIYE("Kırtasiye"),
    EV_ESYASI("Ev Eşyaları");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
