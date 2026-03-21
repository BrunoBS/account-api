package com.brunobs.core.catalog.feature.scope;

import com.brunobs.core.catalog.feature.type.FeatureTypeEnum;
import com.brunobs.shared.BaseEnum;

import java.util.List;


/**
 * Enum defining the mapping between Feature Scopes and their allowed Feature Types.
 * Acts as a business rule engine for the Feature Portal.
 */
public enum FeatureScopeTypeEnum implements BaseEnum<FeatureScopeTypeEnum> {

    ACCOUNT {
        @Override
        public List<FeatureTypeEnum> getAllowedFeatureTypes() {
            return List.of(
                    FeatureTypeEnum.SHARED_APPLICATIONS,
                    FeatureTypeEnum.BACKEND_APPLICATIONS,
                    FeatureTypeEnum.MFE_APPLICATIONS,
                    FeatureTypeEnum.PROMOTION_ENGINE,
                    FeatureTypeEnum.CONFIGURATION
            );
        }
    },

    PROMOTION_ENGINE {
        @Override
        public List<FeatureTypeEnum> getAllowedFeatureTypes() {
            return List.of(
                    FeatureTypeEnum.EMERGENCY_PACKAGE,
                    FeatureTypeEnum.CREATE_PACKAGE,
                    FeatureTypeEnum.MANAGE_PACKAGE
            );
        }
    },

    SHARED_APPLICATION {
        @Override
        public List<FeatureTypeEnum> getAllowedFeatureTypes() {
            return List.of(
                    FeatureTypeEnum.KEY_MANAGEMENT,
                    FeatureTypeEnum.RULE_ENGINE,
                    FeatureTypeEnum.FLEXIBLE_MENU,
                    FeatureTypeEnum.FLEXIBLE_ROUTE,
                    FeatureTypeEnum.ALLOW_LIST
            );
        }
    },


    MFE_APPLICATION {
        @Override
        public List<FeatureTypeEnum> getAllowedFeatureTypes() {
            return List.of(
                    FeatureTypeEnum.MFE_MANAGEMENT,
                    FeatureTypeEnum.MFE_CATALOG
            );
        }
    },

    BACKEND_APPLICATION {
        @Override
        public List<FeatureTypeEnum> getAllowedFeatureTypes() {
            return List.of(
                    FeatureTypeEnum.KEY_MANAGEMENT,
                    FeatureTypeEnum.RULE_ENGINE,
                    FeatureTypeEnum.FLEXIBLE_MENU,
                    FeatureTypeEnum.FLEXIBLE_ROUTE,
                    FeatureTypeEnum.ALLOW_LIST
            );
        }
    };


    public abstract List<FeatureTypeEnum> getAllowedFeatureTypes();


    public String getFormattedOptionsList() {
        List<String> names = getAllowedFeatureTypes().stream()
                .map(Enum::name)
                .toList();

        if (names.isEmpty()) return "";
        if (names.size() == 1) return names.get(0);

        return String.join(", ", names.subList(0, names.size() - 1))
                + " or " + names.get(names.size() - 1);
    }
}
