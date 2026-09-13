/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class ConditionEvaluationReportMessage {
    private StringBuilder message;

    public ConditionEvaluationReportMessage(ConditionEvaluationReport report) {
        this(report, "CONDITIONS EVALUATION REPORT");
    }

    public ConditionEvaluationReportMessage(ConditionEvaluationReport report, String title) {
        this.message = this.getLogMessage(report, title);
    }

    private StringBuilder getLogMessage(ConditionEvaluationReport report, String title) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("%n%n%n", new Object[0]));
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < title.length(); ++i) {
            separator.append("=");
        }
        message.append(String.format("%s%n", separator));
        message.append(String.format("%s%n", title));
        message.append(String.format("%s%n%n%n", separator));
        Map<String, ConditionEvaluationReport.ConditionAndOutcomes> shortOutcomes = this.orderByName(report.getConditionAndOutcomesBySource());
        this.logPositiveMatches(message, shortOutcomes);
        this.logNegativeMatches(message, shortOutcomes);
        this.logExclusions(report, message);
        this.logUnconditionalClasses(report, message);
        message.append(String.format("%n%n", new Object[0]));
        return message;
    }

    private void logPositiveMatches(StringBuilder message, Map<String, ConditionEvaluationReport.ConditionAndOutcomes> shortOutcomes) {
        message.append(String.format("Positive matches:%n", new Object[0]));
        message.append(String.format("-----------------%n", new Object[0]));
        List<Map.Entry> matched = shortOutcomes.entrySet().stream().filter(entry -> ((ConditionEvaluationReport.ConditionAndOutcomes)entry.getValue()).isFullMatch()).collect(Collectors.toList());
        if (matched.isEmpty()) {
            message.append(String.format("%n    None%n", new Object[0]));
        } else {
            matched.forEach(entry -> this.addMatchLogMessage(message, (String)entry.getKey(), (ConditionEvaluationReport.ConditionAndOutcomes)entry.getValue()));
        }
        message.append(String.format("%n%n", new Object[0]));
    }

    private void logNegativeMatches(StringBuilder message, Map<String, ConditionEvaluationReport.ConditionAndOutcomes> shortOutcomes) {
        message.append(String.format("Negative matches:%n", new Object[0]));
        message.append(String.format("-----------------%n", new Object[0]));
        List<Map.Entry> nonMatched = shortOutcomes.entrySet().stream().filter(entry -> !((ConditionEvaluationReport.ConditionAndOutcomes)entry.getValue()).isFullMatch()).collect(Collectors.toList());
        if (nonMatched.isEmpty()) {
            message.append(String.format("%n    None%n", new Object[0]));
        } else {
            nonMatched.forEach(entry -> this.addNonMatchLogMessage(message, (String)entry.getKey(), (ConditionEvaluationReport.ConditionAndOutcomes)entry.getValue()));
        }
        message.append(String.format("%n%n", new Object[0]));
    }

    private void logExclusions(ConditionEvaluationReport report, StringBuilder message) {
        message.append(String.format("Exclusions:%n", new Object[0]));
        message.append(String.format("-----------%n", new Object[0]));
        if (report.getExclusions().isEmpty()) {
            message.append(String.format("%n    None%n", new Object[0]));
        } else {
            for (String exclusion : report.getExclusions()) {
                message.append(String.format("%n    %s%n", exclusion));
            }
        }
        message.append(String.format("%n%n", new Object[0]));
    }

    private void logUnconditionalClasses(ConditionEvaluationReport report, StringBuilder message) {
        message.append(String.format("Unconditional classes:%n", new Object[0]));
        message.append(String.format("----------------------%n", new Object[0]));
        if (report.getUnconditionalClasses().isEmpty()) {
            message.append(String.format("%n    None%n", new Object[0]));
        } else {
            for (String unconditionalClass : report.getUnconditionalClasses()) {
                message.append(String.format("%n    %s%n", unconditionalClass));
            }
        }
    }

    private Map<String, ConditionEvaluationReport.ConditionAndOutcomes> orderByName(Map<String, ConditionEvaluationReport.ConditionAndOutcomes> outcomes) {
        MultiValueMap<String, String> map = this.mapToFullyQualifiedNames(outcomes.keySet());
        ArrayList shortNames = new ArrayList(map.keySet());
        Collections.sort(shortNames);
        LinkedHashMap<String, ConditionEvaluationReport.ConditionAndOutcomes> result = new LinkedHashMap<String, ConditionEvaluationReport.ConditionAndOutcomes>();
        for (String shortName : shortNames) {
            List fullyQualifiedNames = (List)map.get((Object)shortName);
            if (fullyQualifiedNames.size() > 1) {
                fullyQualifiedNames.forEach(fullyQualifiedName -> {
                    ConditionEvaluationReport.ConditionAndOutcomes cfr_ignored_0 = (ConditionEvaluationReport.ConditionAndOutcomes)result.put((String)fullyQualifiedName, (ConditionEvaluationReport.ConditionAndOutcomes)outcomes.get(fullyQualifiedName));
                });
                continue;
            }
            result.put(shortName, outcomes.get(fullyQualifiedNames.get(0)));
        }
        return result;
    }

    private MultiValueMap<String, String> mapToFullyQualifiedNames(Set<String> keySet) {
        LinkedMultiValueMap map = new LinkedMultiValueMap();
        keySet.forEach(fullyQualifiedName -> map.add((Object)ClassUtils.getShortName((String)fullyQualifiedName), fullyQualifiedName));
        return map;
    }

    private void addMatchLogMessage(StringBuilder message, String source, ConditionEvaluationReport.ConditionAndOutcomes matches) {
        message.append(String.format("%n   %s matched:%n", source));
        for (ConditionEvaluationReport.ConditionAndOutcome match : matches) {
            this.logConditionAndOutcome(message, "      ", match);
        }
    }

    private void addNonMatchLogMessage(StringBuilder message, String source, ConditionEvaluationReport.ConditionAndOutcomes conditionAndOutcomes) {
        message.append(String.format("%n   %s:%n", source));
        ArrayList<ConditionEvaluationReport.ConditionAndOutcome> matches = new ArrayList<ConditionEvaluationReport.ConditionAndOutcome>();
        ArrayList<ConditionEvaluationReport.ConditionAndOutcome> nonMatches = new ArrayList<ConditionEvaluationReport.ConditionAndOutcome>();
        for (ConditionEvaluationReport.ConditionAndOutcome conditionAndOutcome : conditionAndOutcomes) {
            if (conditionAndOutcome.getOutcome().isMatch()) {
                matches.add(conditionAndOutcome);
                continue;
            }
            nonMatches.add(conditionAndOutcome);
        }
        message.append(String.format("      Did not match:%n", new Object[0]));
        for (ConditionEvaluationReport.ConditionAndOutcome nonMatch : nonMatches) {
            this.logConditionAndOutcome(message, "         ", nonMatch);
        }
        if (!matches.isEmpty()) {
            message.append(String.format("      Matched:%n", new Object[0]));
            for (ConditionEvaluationReport.ConditionAndOutcome match : matches) {
                this.logConditionAndOutcome(message, "         ", match);
            }
        }
    }

    private void logConditionAndOutcome(StringBuilder message, String indent, ConditionEvaluationReport.ConditionAndOutcome conditionAndOutcome) {
        message.append(String.format("%s- ", indent));
        String outcomeMessage = conditionAndOutcome.getOutcome().getMessage();
        if (StringUtils.hasLength((String)outcomeMessage)) {
            message.append(outcomeMessage);
        } else {
            message.append(conditionAndOutcome.getOutcome().isMatch() ? "matched" : "did not match");
        }
        message.append(" (");
        message.append(ClassUtils.getShortName(conditionAndOutcome.getCondition().getClass()));
        message.append(String.format(")%n", new Object[0]));
    }

    public String toString() {
        return this.message.toString();
    }
}

