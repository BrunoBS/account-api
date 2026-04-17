package com.brunobs.config.context;

import java.util.regex.Pattern;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

public class GroupParser {

    private static final Pattern GROUP_PATTERN =
        Pattern.compile("^PM5-(?:(ENG|AUD|NEG)_)?(DEV|TST|ADM)_([A-Za-z0-9_-]+)$");

    public static Set<ParsedGroup> parse(Set<String> groups) {
        Set<ParsedGroup> parsed = new HashSet<>();
        for (String group : groups) {
            Matcher matcher = GROUP_PATTERN.matcher(group);
            if (!matcher.matches()) {
                continue;
            }
            String profile = matcher.group(1);
            String env = matcher.group(2);
            String authorizer = matcher.group(3);

            if (profile == null) {
                profile = "ENG";
            }

            parsed.add(new ParsedGroup(profile, env, authorizer));
        }

        return parsed;
    }
}