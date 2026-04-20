package com.brunobs.auth.context;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GroupParser {

    private static final Pattern GROUP_PATTERN =
            Pattern.compile("^PM5-(?:(ENG|NEG)-)?(DEV|TST|ADM)_(.+)$");

    public static Set<ParsedGroup> parse(Set<String> groups) {

        if (groups == null || groups.isEmpty()) {
            return Collections.emptySet();
        }

        return groups.stream()
                .map(GROUP_PATTERN::matcher)
                .filter(Matcher::matches)
                .map(GroupParser::buildParsedGroup)
                .collect(Collectors.toSet());
    }

    private static ParsedGroup buildParsedGroup(Matcher matcher) {

        String rawProfile = matcher.group(1);
        String env = matcher.group(2);
        String suffix = matcher.group(3);

        String profile = rawProfile == null ? "ENG" : rawProfile;

        String authorizer = suffix.substring(suffix.lastIndexOf('_') + 1);

        return new ParsedGroup(
                matcher.group(0),
                profile,
                env,
                authorizer
        );
    }
}