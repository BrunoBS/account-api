package com.brunobs.core.catalog.type.language;
import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for LanguageType catalog.
 * Leverages BaseTypeMapper to handle common field conversions including 'label'.
 */
@Component
public class LanguageTypeMapper
        extends BaseTypeMapper<LanguageTypeDTO, LanguageType, Long> {

    public LanguageTypeMapper() {
        super(LanguageType.class);
    }

    @Override
    public LanguageTypeDTO toDTO(LanguageType entity) {
        if (entity == null) return null;

        return new LanguageTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder()
        );
    }
}
