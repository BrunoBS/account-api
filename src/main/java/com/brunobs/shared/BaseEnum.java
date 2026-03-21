package com.brunobs.shared;

import jakarta.annotation.Nonnull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.List;

/**
 * Base interface for all Enums in the system.
 * Provides reflection-based utilities for validation and i18n error messaging.
 */
public interface BaseEnum<T extends Enum<T>> {

    String COMMON_LABEL_OR = "common.label.or";

    /**
     * Retorna a lista de strings válidas para este Enum.
     * Pode ser sobrescrito para retornar códigos ou labels customizados.
     */
    @Nonnull
    default List<String> getOptions() {
        Class<?> enumClass = this.getClass();

        // Handle proxy classes (common in Hibernate/Spring)
        if (!enumClass.isEnum() && enumClass.getSuperclass() != null && enumClass.getSuperclass().isEnum()) {
            enumClass = enumClass.getSuperclass();
        }

        Object[] constants = enumClass.getEnumConstants();
        if (constants == null) return List.of();

        return Arrays.stream(constants)
                .map(e -> ((Enum<?>) e).name())
                .toList();
    }

    /**
     * Busca uma constante do Enum pelo valor string (case-insensitive).
     */
    static <E extends Enum<E>> E from(Class<E> enumClass, String value) {
        if (value == null || value.isBlank()) return null;

        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gera uma mensagem padronizada listando todas as opções válidas com suporte a i18n.
     * Exemplo: "OPCAO1, OPCAO2 ou OPCAO3"
     */
    static <E extends Enum<E> & BaseEnum<E>> String getOptionsValid(Class<E> enumClass, MessageSource messageSource) {
        E[] constants = enumClass.getEnumConstants();
        if (constants == null || constants.length == 0) return "";

        // Respeita o getOptions() da primeira constante (caso haja override)
        List<String> options = constants[0].getOptions();

        if (options.isEmpty()) return "";
        if (options.size() == 1) return options.get(0);

        // Busca o separador localizado ("ou" / "or") do messages_en.properties
        String separator = messageSource.getMessage(COMMON_LABEL_OR, null, LocaleContextHolder.getLocale());

        int lastIdx = options.size() - 1;
        return String.join(", ", options.subList(0, lastIdx))
                + " " + separator + " " + options.get(lastIdx);
    }
}
