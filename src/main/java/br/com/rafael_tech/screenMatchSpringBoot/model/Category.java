package br.com.rafael_tech.screenMatchSpringBoot.model;

public enum Category {
    ACTION("Ação"),
    COMEDY("Comédia"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    CRIME("Crime"),
    TERROR("Terror"),
    SUSPENSE("Suspense");

    private String categoryOmdb;

    Category(String categoryOmdb){
        this.categoryOmdb = categoryOmdb;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOmdb.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("no category found for given string: "  + text);
    }
}
