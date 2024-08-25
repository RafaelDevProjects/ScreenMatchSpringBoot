package br.com.rafael_tech.screenMatchSpringBoot.model;

public enum Category {
    ACTION("Action"),
    COMEDY("Comedy"),
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
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
